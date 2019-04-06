package markup;

public interface MarkupElement {
	public void toMarkdown (StringBuilder sb);
	public void toTex (StringBuilder sb);
}