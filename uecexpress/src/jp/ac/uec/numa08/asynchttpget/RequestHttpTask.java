package jp.ac.uec.numa08.asynchttpget;

import jp.ac.uec.numa08.asynchttpget.RequestExecuteInterface.onRequestExecuteListener;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.util.Log;

public class RequestHttpTask extends AsyncTask<String, Void, String> {
	private static final String TAG = RequestHttpTask.class.getSimpleName();
	private static final int _TIME_OUT = 30 * 1000;
	private final transient Activity activity;
	private final transient onRequestExecuteListener listener;
	private final transient String dialogMessage;
	private final transient String dialogTitle;
	private final transient String encoding;
	private transient ProgressDialog dialog;
	private transient Exception exception;

	/**
	 * コンストラクタ。ダイアログのメッセージは勝手に決める
	 * 
	 * @param activity
	 * @param listener
	 */
	public RequestHttpTask(final Activity activity,
			final onRequestExecuteListener listener) {
		super();
		this.activity = activity;
		this.listener = listener;
		this.dialogMessage = "Please wait...";
		this.dialogTitle = "Connectting...";
		this.encoding = "UTF-8";
	}

	/**
	 * コンストラクタ。ダイアログのメッセージをString型変数で決める
	 * 
	 * @param activity
	 * @param listener
	 * @param dialogMessage
	 * @param dialogTitle
	 */
	public RequestHttpTask(final Activity activity,
			final onRequestExecuteListener listener,
			final String dialogMessage, final String dialogTitle) {
		super();
		this.activity = activity;
		this.listener = listener;
		this.dialogMessage = dialogMessage;
		this.dialogTitle = dialogTitle;
		this.encoding = "UTF-8";
	}

	/**
	 * コンストラクタ。ダイアログのメッセージをR.string.idから決める
	 * 
	 * @param activity
	 * @param listener
	 * @param dialogMessage
	 * @param dialogTitle
	 */
	public RequestHttpTask(final Activity activity,
			final onRequestExecuteListener listener, final int dialogMessage,
			final int dialogTitle) {
		super();
		this.activity = activity;
		this.listener = listener;
		this.dialogMessage = activity.getString(dialogMessage);
		this.dialogTitle = activity.getString(dialogTitle);
		this.encoding = "UTF-8";
	}

	/**
	 * コンテンツの文字コードを指定する
	 * 
	 * @param activity
	 * @param listener
	 * @param dialogMessage
	 * @param dialogTitle
	 * @param encoding
	 * @param dialog
	 * @param exception
	 */
	public RequestHttpTask(final Activity activity,
			final onRequestExecuteListener listener,
			final String dialogMessage, final String dialogTitle,
			final String encoding) {
		super();
		this.activity = activity;
		this.listener = listener;
		this.dialogMessage = dialogMessage;
		this.dialogTitle = dialogTitle;
		this.encoding = encoding;
	}

	/**
	 * コンテンツの文字コードを指定する
	 * 
	 * @param activity
	 * @param listener
	 * @param dialogMessage
	 * @param dialogTitle
	 * @param encoding
	 * @param dialog
	 * @param exception
	 */
	public RequestHttpTask(final Activity activity,
			final onRequestExecuteListener listener, final int dialogMessage,
			final int dialogTitle, final String encoding) {
		super();
		this.activity = activity;
		this.listener = listener;
		this.dialogMessage = activity.getString(dialogMessage);
		this.dialogTitle = activity.getString(dialogTitle);
		this.encoding = encoding;
	}

	/*
	 * キャンセル時の処理。ダイアログを消す {@inheritDoc}
	 */
	@Override
	protected void onCancelled() {
		// TODO Auto-generated method stub
		Log.d(TAG, "on cancelled");
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
			// dialog = null;
		}
	}

	/*
	 * 実行前の処理。ダイアログの設定と表示 {@inheritDoc}
	 */
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		Log.d(TAG, "on pre execute");

		this.dialog = new ProgressDialog(activity);
		this.dialog.setTitle(dialogTitle);
		this.dialog.setMessage(dialogMessage);
		this.dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		this.dialog.setCancelable(true);

		this.dialog.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(final DialogInterface dialog) {
				// TODO Auto-generated method stub
				RequestHttpTask.this.cancel(true);
			}
		});
		this.dialog.show();
	}

	/*
	 * 処理の実行。指定したURLからコンテンツをDLし、String型で返す {@inheritDoc}
	 */
	@Override
	protected String doInBackground(final String... params) {
		// TODO Auto-generated method stub
		Log.d(TAG, "do in back ground");

		synchronized (activity) {
			final HttpClient httpClient = new DefaultHttpClient();
			final HttpParams param = httpClient.getParams();
			HttpConnectionParams.setConnectionTimeout(param, _TIME_OUT);
			HttpConnectionParams.setSoTimeout(param, _TIME_OUT);
			final HttpUriRequest httpRequest = new HttpGet(params[0]);
			HttpResponse httpResponse = null;
			HttpEntity httpEntity = null;
			String content = null;
			try {
				httpResponse = httpClient.execute(httpRequest);
				httpEntity = httpResponse.getEntity();
				content = EntityUtils.toString(httpEntity, encoding);
			} catch (final Exception e) {
				this.exception = e;
				Log.e(TAG, "do in back ground", e);
			} finally {
				try {
					httpEntity.consumeContent();
					httpClient.getConnectionManager().shutdown();
				} catch (final Exception e) {
					this.exception = e;
					Log.e(TAG, "do in  back ground", e);
				}
			}
			return content;
		}
	}

	/*
	 * 処理実行後の処理。コールバックを呼び出したり。 {@inheritDoc}
	 */
	@Override
	protected void onPostExecute(final String result) {
		// TODO Auto-generated method stub
		Log.d(TAG, "on post execute");
		if (exception == null) {
			listener.onSuccess(result);
		} else {
			listener.onError(exception);
		}
		dialog.dismiss();
	}
}
