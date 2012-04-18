package jp.ac.uec.numa08.asynchttpget;

/**
 * HTTPリクエストが完了した時のコールバック
 * 
 * @author numanuma08
 * 
 */
public interface RequestExecuteInterface {
	interface onRequestExecuteListener {
		public void onSuccess(String content);

		public void onError(Exception exception);
	}
}
