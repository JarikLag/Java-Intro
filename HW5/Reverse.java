import java.util.*;
import java.io.*;

public class Reverse {
    public static void main(String[] args) {
        FastScanner reader = new FastScanner(System.in);
        List<List<Integer>> answerArray = new ArrayList<>();
        while (reader.hasNextLine()) {
			String tempString = reader.nextLine();
            List<Integer> lineArray = new ArrayList<>();
            for (int i = 0; i < tempString.length(); i++) {
				int begin = i;
				while (i < tempString.length() && !Character.isWhitespace(tempString.charAt(i))) {
					i++;
				}
				if (begin != i) {
					lineArray.add(Integer.parseInt(tempString.substring(begin, i)));
				}
			}
            answerArray.add(lineArray);
        }
        for (int i = answerArray.size() - 1; i >= 0; i--) {
            for (int j = answerArray.get(i).size() - 1; j >= 0; j--) {
                System.out.print(answerArray.get(i).get(j));
                if (j != 0) {
                    System.out.print(" ");
                }
            }
            System.out.println("");
        }
    }
}
