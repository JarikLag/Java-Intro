package markup;

public class Text implements MarkupElement {
	private final String s;
	
	public Text(String s) {
		this.s = s;
	}
	
	@Override
	public void toMarkdown (StringBuilder sb) {
		sb.append(s);
	}
	
	@Override
	public void toTex (StringBuilder sb) {
		sb.append(s);
	}
}