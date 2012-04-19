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
	private String time;
	private String title;
	private String teacher;
	private String description;

	public UECExpressImfo(final String className, final String date,
			final String time, final String title, final String teacher,
			final String description) {
		super();
		this.className = className;
		this.date = date;
		this.time = time;
		this.title = title;
		this.teacher = teacher;
		this.description = description;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(final String className) {
		this.className = className;
	}

	public String getDate() {
		return date;
	}

	public void setDate(final String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(final String time) {
		this.time = time;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(final String teacher) {
		this.teacher = teacher;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

}
