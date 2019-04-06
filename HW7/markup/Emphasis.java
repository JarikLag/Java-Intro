package markup;

import java.util.List;

public class Emphasis extends AbstractMark {
	public Emphasis(List<MarkupElement> list) {
		super(list, "*", "*", "\\emph{", "}");
	}
}