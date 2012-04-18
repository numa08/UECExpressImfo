package jp.ac.uec.numa08.widgets;

import jp.ac.uec.numa08.asynchttpget.RequestExecuteInterface.onRequestExecuteListener;
import jp.ac.uec.numa08.uecexpress.UecExpressActivity;
import android.util.Log;

public class GetExpressListener implements onRequestExecuteListener {
	private static final String TAG = GetExpressListener.class.getSimpleName();

	private transient final UecExpressActivity mActivity;

	public GetExpressListener(UecExpressActivity mActivity) {
		super();
		this.mActivity = mActivity;
	}

	@Override
	public void onSuccess(String content) {
		Log.d(TAG, "on success");
		// TODO Auto-generated method stub
		// TODO テータ解析
		// TODO　描画メソッド呼び出し

	}

	@Override
	public void onError(Exception exception) {
		// TODO Auto-generated method stub
		Log.e(TAG, "on error");
	}
}
