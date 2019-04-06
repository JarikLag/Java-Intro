package markup;

import java.util.List;

public class Paragraph implements MarkupElement {
	private List<MarkupElement> list;
	
	public Paragraph(List<MarkupElement> list) {
	    this.list = list;
	}
	
	@Override
	public void toMarkdown (StringBuilder sb) {
		for (MarkupElement tmp : list) {
			tmp.toMarkdown(sb);
		}
	}
	
	@Override
	public void toTex (StringBuilder sb) {
		for (MarkupElement tmp : list) {
			tmp.toTex(sb);
		}
	}
}