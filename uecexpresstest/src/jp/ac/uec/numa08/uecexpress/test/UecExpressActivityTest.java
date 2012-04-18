package jp.ac.uec.numa08.uecexpress.test;

import jp.ac.uec.numa08.uecexpress.R;
import jp.ac.uec.numa08.uecexpress.UecExpressActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.ListView;

public class UecExpressActivityTest extends
		ActivityInstrumentationTestCase2<UecExpressActivity> {
	// private static final String TAG = UecExpressActivityTest.class
	// .getSimpleName();
	private UecExpressActivity activity;
	private Button updateButton;
	private ListView expressListView;

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
	}

	public void testButtonClicked() {
		activity.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				updateButton.performClick();
			}
		});
		getInstrumentation().waitForIdleSync();
		final int listSize = expressListView.getCount();
		assertNotSame("express list is not zero", 0, listSize);
	}
}
