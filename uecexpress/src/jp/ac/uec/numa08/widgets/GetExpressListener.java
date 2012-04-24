package jp.ac.uec.numa08.widgets;

import java.util.List;

import jp.ac.numa08.object.UECExpressImfo;
import jp.ac.uec.numa08.asynchttpget.RequestExecuteInterface.onRequestExecuteListener;
import jp.ac.uec.numa08.uecexpress.UecExpressActivity;
import net.htmlparser.jericho.Source;
import android.util.Log;

/**
 * 休講情報取得タスクのリスナー
 * 
 * @author numanuma08
 * 
 */
public class GetExpressListener implements onRequestExecuteListener {
	private static final String TAG = GetExpressListener.class.getSimpleName();

	private transient final UecExpressActivity mActivity;

	public GetExpressListener(final UecExpressActivity mActivity) {
		super();
		this.mActivity = mActivity;
	}

	@Override
	public void onSuccess(final String content) {
		Log.d(TAG, "on success");
		// TODO Auto-generated method stub
		// TODO テータ解析
		final Source html = new Source(content);
		final ExpressPageParser parser = new ExpressPageParser(html);
		final List<UECExpressImfo> imfoList = parser.getExpressImfo();
		// TODO　描画メソッド呼び出し
		mActivity.updateAction(imfoList);
	}

	@Override
	public void onError(final Exception exception) {
		// TODO Auto-generated method stub
		Log.e(TAG, "on error");
		// TODO 通信エラーダイアログの表示
	}
}
