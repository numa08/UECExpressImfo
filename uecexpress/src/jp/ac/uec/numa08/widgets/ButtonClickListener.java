package jp.ac.uec.numa08.widgets;

import jp.ac.uec.numa08.uecexpress.UecExpressActivity;
import android.view.View;
import android.view.View.OnClickListener;

public class ButtonClickListener implements OnClickListener {
	// private static final String TAG =
	// ButtonClickListener.class.getSimpleName();
	private final transient UecExpressActivity mActivity;

	public ButtonClickListener(final UecExpressActivity activity) {
		this.mActivity = activity;
	}

	@Override
	public void onClick(final View view) {
		// TODO Auto-generated method stub
		mActivity.updateButtonAction();
	}
}
