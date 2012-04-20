package jp.ac.uec.numa08.widgets;

import java.util.List;

import jp.ac.numa08.object.UECExpressImfo;
import jp.ac.uec.numa08.uecexpress.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ExpressImfoAdapter extends ArrayAdapter<UECExpressImfo> {
	// private static final String TAG =
	// ExpressImfoAdapter.class.getSimpleName();
	private transient final LayoutInflater inflater;

	public ExpressImfoAdapter(final Context context,
			final List<UECExpressImfo> objects) {
		super(context, 0, objects);
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(final int position, final View convertView,
			final ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		View mConvertView = convertView;
		if (convertView == null) {
			mConvertView = inflater.inflate(R.layout.express_list_row, parent,
					false);
			final TextView title = (TextView) mConvertView
					.findViewById(R.id.title);
			final TextView date = (TextView) mConvertView
					.findViewById(R.id.date);
			final TextView teacher = (TextView) mConvertView
					.findViewById(R.id.teacher);
			holder = new ViewHolder(title, date, teacher);
			mConvertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final UECExpressImfo imfo = getItem(position);
		holder.title.setText(imfo.getTitle());
		holder.date.setText(imfo.getDate());
		holder.teacher.setText(imfo.getTeacher() + "先生");
		return mConvertView;
	}

	private class ViewHolder {
		public transient TextView title;
		public transient TextView date;
		public transient TextView teacher;

		public ViewHolder(final TextView title, final TextView date,
				final TextView teacher) {
			super();
			this.title = title;
			this.date = date;
			this.teacher = teacher;
		}
	}
}
