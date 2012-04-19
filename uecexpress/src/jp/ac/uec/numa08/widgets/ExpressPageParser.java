package jp.ac.uec.numa08.widgets;

import java.util.ArrayList;
import java.util.List;

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

	private final Source source;

	public ExpressPageParser(final Source source) {
		this.source = source;
	}

	public List<String> getExpressImfo() {
		List<String> titleList = new ArrayList<String>();
		// TODO TDタグを取得
		List<Element> tdList = source.getAllElements(HTMLElementName.TD);
		// TODO　4*n+1(n>=2)番目のTDタグの中身が、休講科目のタイトル
		for (int i = 0; i < tdList.size(); i++) {
			if (i >= 6 && i % 6 == 3) {
				titleList.add(tdList.get(i).getContent().toString());
			}
		}
		return titleList;
	}
}
