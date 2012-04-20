package jp.ac.uec.numa08.uecexpress.test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import jp.ac.numa08.object.UECExpressImfo;
import jp.ac.uec.numa08.asynchttpget.RequestExecuteInterface.onRequestExecuteListener;
import jp.ac.uec.numa08.asynchttpget.RequestHttpTask;
import jp.ac.uec.numa08.uecexpress.R;
import jp.ac.uec.numa08.uecexpress.UecExpressActivity;
import jp.ac.uec.numa08.widgets.ExpressPageParser;
import net.htmlparser.jericho.Source;
import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.ListView;

public class UecExpressActivityTest extends
		ActivityInstrumentationTestCase2<UecExpressActivity> {
	// private static final String TAG = UecExpressActivityTest.class
	// .getSimpleName();
	private static final String _UEC_EXPRESS_URL = "http://kyoumu.office.uec.ac.jp/kyuukou/kyuukou.html";
	private UecExpressActivity activity;
	private Button updateButton;
	private ListView expressListView;
	private CountDownLatch signal;
	private String result;

	public UecExpressActivityTest() {
		super("jp.ac.uec.numa08.uecexpress", UecExpressActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		this.activity = getActivity();
		this.updateButton = (Button) activity.findViewById(R.id.update_button);
		this.expressListView = (ListView) activity
				.findViewById(R.id.express_list);
		signal = new CountDownLatch(1);
	}

	public void testExecut() throws InterruptedException,
			UnsupportedEncodingException {
		MyTestTask task = new MyTestTask(activity, new MyTestListener(),
				"test", "test", "Shift_jis");
		task.execute(UecExpressActivityTest._UEC_EXPRESS_URL);
		signal.await();
		ExpressPageParser parser = new ExpressPageParser(new Source(result));
		List<UECExpressImfo> imfoList = parser.getExpressImfo();
		UECExpressImfo imfo = imfoList.get(0);
		String title = "英語演習";
		// assertEquals("same string", title, imfo.getTitle());
		// assertThat(title, is(imfo.getTitle()));
		assertThat(title, is(imfo.getTitle()));
	}

	private class MyTestListener implements onRequestExecuteListener {

		@Override
		public void onSuccess(String content) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onError(Exception exception) {
			// TODO Auto-generated method stub

		}

	}

	private class MyTestTask extends RequestHttpTask {

		public MyTestTask(Activity activity, onRequestExecuteListener listener,
				String dialogMessage, String dialogTitle, String encoding) {
			super(activity, listener, dialogMessage, dialogTitle, encoding);
			// TODO Auto-generated constructor stub
		}

		@Override
		public String doInBackground(String... params) {
			// TODO Auto-generated method stub
			return super.doInBackground(params);
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			UecExpressActivityTest.this.result = result;
			signal.countDown();
		}
	}
}
