import markup.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Paragraph paragraph = new Paragraph(Collections.singletonList(
                new Strong(Arrays.asList(new Text("sdq"), new Strikeout(Arrays.asList(new Emphasis(Collections.singletonList(new Text("r"))), new Text("vavc"))), new Text("zg")))));
		StringBuilder sb = new StringBuilder();
		paragraph.toMarkdown(sb);
		System.out.println(sb);
    }
}