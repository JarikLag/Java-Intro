import java.util.*;
import java.io.*;

public class Reverse {
	public static void main(String[] args) throws IOException {
		//File input = new File("C:\\Users\\Yaroslav Dorokhov\\Desktop\\JavaHomework\\#2\\input.txt");
		Scanner in = new Scanner(System.in);
		Deque<Deque<Integer>> answerDeque = new ArrayDeque<Deque<Integer>>();
		while (in.hasNextLine()) {
			String tempString = in.nextLine();
			Deque<Integer> lineDeque = new ArrayDeque<Integer>();
			for (int i = 0; i < tempString.length(); i++) {
				int begin = i;
				while (i < tempString.length() && !Character.isWhitespace(tempString.charAt(i))) {
					i++;
				}
				if (begin != i) {
					lineDeque.addLast(Integer.parseInt(tempString.substring(begin, i)));
				}
			}
			answerDeque.addLast(lineDeque);
		}
		while (answerDeque.size() > 0) {
			Deque<Integer> tempOut = answerDeque.pollLast();
			while (tempOut.size() > 0) {
					System.out.print(tempOut.pollLast() + " ");
			}
			System.out.println("");
		}
	}
}