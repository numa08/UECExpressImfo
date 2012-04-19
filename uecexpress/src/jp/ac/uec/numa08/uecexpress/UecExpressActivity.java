package jp.ac.uec.numa08.uecexpress;

import java.util.List;

import jp.ac.uec.numa08.asynchttpget.RequestHttpTask;
import jp.ac.uec.numa08.widgets.ButtonClickListener;
import jp.ac.uec.numa08.widgets.GetExpressListener;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
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
	protected void onResume() {
		// TODO Auto-generated method stub
		// TODO　休講情報取得タスク実行
		executeExpressGetTask();
		super.onResume();
	}

	private void executeExpressGetTask() {
		final RequestHttpTask httpTask = new RequestHttpTask(this,
				expressListener, R.string.dialog_message,
				R.string.dialog_title, "Shift_jis");
		httpTask.execute(UecExpressActivity._UEC_EXPRESS_URL);
	}

	/**
	 * 更新ボタンがクリックされた
	 */
	public void updateButtonAction() {
		// TODO 休講情報取得タスク実行
		executeExpressGetTask();
	}

	/**
	 * 休講情報更新時のアクション
	 * 
	 * @param titleList
	 *            休講科目のタイトル
	 * 
	 */
	public void updateAction(List<String> titleList) {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_expandable_list_item_1);
		for (String title : titleList) {
			adapter.add(title);
		}
		expressListView.setAdapter(adapter);
	}
}