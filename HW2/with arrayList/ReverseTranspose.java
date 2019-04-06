import java.util.*;
import java.io.*;

public class ReverseTranspose {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		List<List<Integer>> answerArray = new ArrayList<>();
		int maxSize = 1;
		while (in.hasNextLine()) {
			String tempString = in.nextLine();
			List<Integer> lineArray = new ArrayList<Integer>();
			for (int i = 0; i < tempString.length(); i++) {
				int begin = i;
				while (i < tempString.length() && !Character.isWhitespace(tempString.charAt(i))) {
					i++;
				}
				if (begin != i) {
					lineArray.add(Integer.parseInt(tempString.substring(begin, i)));
				}
			}
			maxSize = Math.max(maxSize, lineArray.size());
			answerArray.add(lineArray);
		}	
		for (int j = 0; j < maxSize; j++) {
			for (int i = 0; i < answerArray.size(); i++) {
				if (answerArray.get(i).size() >= j + 1) {
					System.out.print(answerArray.get(i).get(j));
					if (i != answerArray.size() - 1) {
						System.out.print(" ");
					}
				}
			}
			System.out.println("");
		}
	}
}
