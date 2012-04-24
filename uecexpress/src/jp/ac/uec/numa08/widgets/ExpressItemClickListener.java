package jp.ac.uec.numa08.widgets;

import jp.ac.numa08.object.UECExpressImfo;
import jp.ac.uec.numa08.uecexpress.UecExpressActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ExpressItemClickListener implements OnItemClickListener {

	private final transient UecExpressActivity activity;

	public ExpressItemClickListener(final UecExpressActivity activity) {
		this.activity = activity;
	}

	@Override
	public void onItemClick(final AdapterView<?> parsent, final View view,
			final int position, final long itemId) {
		// TODO Auto-generated method stub
		final ListView listView = (ListView) parsent;
		final UECExpressImfo imfo = (UECExpressImfo) listView
				.getItemAtPosition(position);
		activity.selectedExpressImfo(imfo);
	}

}
