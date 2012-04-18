package jp.ac.uec.numa08.uecexpress;

import jp.ac.uec.numa08.asynchttpget.RequestHttpTastk;
import jp.ac.uec.numa08.widgets.ButtonClickListener;
import jp.ac.uec.numa08.widgets.GetExpressListener;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

/**
 * 一番最初のActivity
 * 
 * @author numanuma08
 * 
 */
public class UecExpressActivity extends Activity {
	/** Called when the activity is first created. */
	private static final String _UEC_EXPRESS_URL = "http://kyoumu.office.uec.ac.jp/kyuukou/kyuukou.html";
	private transient ListView expressListView;// 休講情報のListView
	private transient GetExpressListener expressListener;// 休講情報取得タスクのリスナー

	@Override
	public void onCreate(final Bundle siState) {
		super.onCreate(siState);
		setContentView(R.layout.main);
		// TODO ListView設定
		expressListView = (ListView) findViewById(R.id.express_list);
		// TODO　ボタンの設定
		final Button updateButton = (Button) findViewById(R.id.update_button);
		updateButton.setOnClickListener(new ButtonClickListener(this));
		// TODO 休講情報取得リスナーの設定
		expressListener = new GetExpressListener(this);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		// TODO　休講情報取得タスク実行
		final RequestHttpTastk task = new RequestHttpTastk(this,
				expressListener, R.string.dialog_message, R.string.dialog_title);
		task.execute(this._UEC_EXPRESS_URL);
	}

	/**
	 * 更新ボタンがクリックされた
	 */
	public void updateAction() {
		// TODO 休講情報取得タスク実行
	}

}