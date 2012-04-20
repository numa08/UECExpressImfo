package jp.ac.uec.numa08.widgets;

import java.util.ArrayList;
import java.util.List;

import jp.ac.numa08.object.UECExpressImfo;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

/**
 * HTMLを解析して、休講情報を取得するクラス
 * 
 * @author numanuma08
 * 
 */
public class ExpressPageParser {
	// private static final String TAG =
	// ExpressPageParser.class.getSimpleName();
	private static final int CLASS_NAME = 0;
	private static final int DATE = CLASS_NAME + 1;
	private static final int TIME = DATE + 1;
	private static final int NAME = TIME + 1;
	private static final int TEACHER = NAME + 1;
	private static final int DESCRIPTION = TEACHER + 1;

	private transient final Source source;

	public ExpressPageParser(final Source source) {
		this.source = source;
	}

	// public List<String> getExpressImfo() {
	// List<String> titleList = new ArrayList<String>();
	// // TODO TDタグを取得
	// List<Element> tdList = source.getAllElements(HTMLElementName.TD);
	// // TODO　4*n+1(n>=2)番目のTDタグの中身が、休講科目のタイトル
	// for (int i = 0; i < tdList.size(); i++) {
	// if (i >= 6 && i % 6 == 3) {
	// titleList.add(tdList.get(i).getContent().toString());
	// }
	// }
	// return titleList;
	// }

	public List<UECExpressImfo> getExpressImfo() {
		final List<UECExpressImfo> expressList = new ArrayList<UECExpressImfo>();
		final List<Element> trList = source.getAllElements(HTMLElementName.TR);
		for (int i = 1; i < trList.size(); i++) {
			final Element tableRow = trList.get(i);
			final List<Element> tdList = tableRow
					.getAllElements(HTMLElementName.TD);
			final String className = tdList.get(CLASS_NAME).getContent()
					.toString();
			final String date = tdList.get(DATE).getContent().toString();
			final String time = tdList.get(TIME).getContent().toString();
			final String title = tdList.get(NAME).getContent().toString();
			final String teacher = tdList.get(TEACHER).getContent().toString();
			final String description = tdList.get(DESCRIPTION).getContent()
					.toString();
			expressList.add(new UECExpressImfo(className, date, time, title,
					teacher, description));
		}
		return expressList;
	}
}
