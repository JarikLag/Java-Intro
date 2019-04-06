package markup;

import java.util.List;

public class Strikeout extends AbstractMark {
	public Strikeout(List<MarkupElement> list) {
		super(list, "~", "~", "\\textst{", "}");
	}
}