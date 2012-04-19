package jp.ac.numa08.object;

/**
 * 休講情報保存クラス
 * 
 * @author numanuma08
 * 
 */
public class UECExpressImfo {
	// private static final String TAG = UECExpressImfo.class.getSimpleName();

	private String className;
	private String date;
	private int time;
	private String title;
	private String teacher;
	private String description;

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
