package jp.ac.uec.numa08.asynchttpget;

import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;

import jp.ac.uec.numa08.asynchttpget.RequestExecuteInterface.onRequestExecuteListener;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.mozilla.universalchardet.UniversalDetector;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.util.Log;

public class RequestHttpTask extends AsyncTask<String, Void, String> {
	private static final String TAG = RequestHttpTask.class.getSimpleName();
	private static final int BUFFER_SIZE = 500;
	private static final int _TIME_OUT = 30 * 1000;
	private final transient Activity activity;
	private final transient onRequestExecuteListener listener;
	private final transient String dialogMessage;
	private final transient String dialogTitle;
	// private final transient String encoding;
	private transient ProgressDialog dialog;
	private transient Exception exception;

	public RequestHttpTask(final Activity activity,
			final onRequestExecuteListener listener,
			final String dialogMessage, final String dialogTitle) {
		super();
		this.activity = activity;
		this.listener = listener;
		this.dialogMessage = dialogMessage;
		this.dialogTitle = dialogTitle;
	}

	public RequestHttpTask(final Activity activity,
			final onRequestExecuteListener listener, final int dialogMessageId,
			final int dialogTitleId) {
		this(activity, listener, activity.getString(dialogMessageId), activity
				.getString(dialogTitleId));
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
		return getContentByDetectEncoding(params);
	}

	/**
	 * 文字コードを自動判別して、WEBからデータを引っ張ってくる
	 * 
	 * @param params
	 * @return
	 */
	private String getContentByDetectEncoding(final String... params) {
		String content = null;
		final HttpClient httpClient = new DefaultHttpClient();
		final HttpParams httpParams = httpClient.getParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, _TIME_OUT);
		HttpConnectionParams.setSoTimeout(httpParams, _TIME_OUT);
		HttpEntity entity = null;
		try {
			final HttpGet getMethod = new HttpGet();
			getMethod.setURI(new URI(params[0]));
			final HttpResponse resp = httpClient.execute(getMethod);
			if (resp.getStatusLine().getStatusCode() < 400) {
				entity = resp.getEntity();
				final InputStream inputStream = entity.getContent();
				final ArrayList<Byte> byteList = new ArrayList<Byte>();

				final int bufSize = RequestHttpTask.BUFFER_SIZE;
				final byte[] buf = new byte[bufSize];
				int bufferSize = 0;
				while (true) {
					final int readLine = inputStream.read(buf, 0, bufSize);
					if (readLine == -1) {
						break;
					}

					for (int i = 0; i < readLine; i++) {
						byteList.add(Byte.valueOf(buf[i]));
					}
					bufferSize += readLine;
				}

				final byte[] byteArray = new byte[bufferSize];
				for (int i = 0; i < bufferSize; i++) {
					byteArray[i] = byteList.get(i).byteValue();
				}
				// 文字コードを取得する
				final String encoding = findEncodingCharset(bufferSize,
						byteArray);

				// 得られたデータ文字列を保存
				content = new String(byteArray, encoding);
			}
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			Log.e(TAG, null, e);
			this.exception = e;
		} finally {
			try {
				entity.consumeContent();
				httpClient.getConnectionManager().shutdown();
			} catch (final Exception e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				Log.e(TAG, null, e);
				this.exception = e;
			}
		}
		return content;
	}

	/**
	 * 文字列の文字コードを見つける。初期値はUTF-8
	 * 
	 * @param bufferSize
	 * @param byteArray
	 * @return
	 */
	private String findEncodingCharset(final int bufferSize,
			final byte[] byteArray) {
		final UniversalDetector detector = new UniversalDetector(null);
		detector.handleData(byteArray, 0, bufferSize);
		detector.dataEnd();

		String encoding = detector.getDetectedCharset();
		detector.reset();
		if (encoding == null) {
			encoding = "UTF-8";
		}
		return encoding;
	}

	// private String getContentByHttpClient(final String... params) {
	// final HttpClient httpClient = new DefaultHttpClient();
	// final HttpParams param = httpClient.getParams();
	// HttpConnectionParams.setConnectionTimeout(param, _TIME_OUT);
	// HttpConnectionParams.setSoTimeout(param, _TIME_OUT);
	// final HttpUriRequest httpRequest = new HttpGet(params[0]);
	// HttpResponse httpResponse = null;
	// HttpEntity httpEntity = null;
	// String content = null;
	// try {
	// httpResponse = httpClient.execute(httpRequest);
	// httpEntity = httpResponse.getEntity();
	// content = EntityUtils.toString(httpEntity, encoding);
	// } catch (final Exception e) {
	// this.exception = e;
	// Log.e(TAG, "do in back ground", e);
	// } finally {
	// try {
	// httpEntity.consumeContent();
	// httpClient.getConnectionManager().shutdown();
	// } catch (final Exception e) {
	// this.exception = e;
	// Log.e(TAG, "do in  back ground", e);
	// }
	// }
	// return content;
	// }

	/*
	 * 処理実行後の処理。コールバックを呼び出したり。 {@inheritDoc}
	 */
	@Override
	protected void onPostExecute(final String result) {
		// TODO Auto-generated method stub
		Log.d(TAG, "on post execute");
		dialog.dismiss();
		if (exception == null) {
			listener.onSuccess(result);
		} else {
			listener.onError(exception);
		}
	}
}
