package markup;

import java.util.List;

public abstract class AbstractMark implements MarkupElement {
	private List<MarkupElement> list;
	protected final String markdownOpening;
	protected final String markdownClosing;
	protected final String texOpening;
	protected final String texClosing;
	
	public AbstractMark(List<MarkupElement> list, String markdownOpening, String markdownClosing, String texOpening, String texClosing) {
		this.list = list;
		this.markdownOpening = markdownOpening;
		this.markdownClosing = markdownClosing;
		this.texOpening = texOpening;
		this.texClosing = texClosing;
	}
	
	@Override
	public void toMarkdown (StringBuilder sb) {
		sb.append(markdownOpening);
		for (MarkupElement tmp : list) {
			tmp.toMarkdown(sb);
		}
		sb.append(markdownClosing);
	}
	
	@Override
	public void toTex (StringBuilder sb) {
		sb.append(texOpening);
		for (MarkupElement tmp : list) {
			tmp.toTex(sb);
		}
		sb.append(texClosing);
	}
}