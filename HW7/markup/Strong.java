package markup;

import java.util.List;

public class Strong extends AbstractMark {
	public Strong(List<MarkupElement> list) {
		super(list, "__", "__", "\\textbf{", "}");
	}
}