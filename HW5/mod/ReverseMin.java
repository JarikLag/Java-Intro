import java.util.*;
import java.io.*;

public class ReverseMin {
    public static void main(String[] args) {
        FastScanner reader = new FastScanner(System.in);
        List<Integer> lineMin = new ArrayList<>();
        List<Integer> columnMin = new ArrayList<>();
        List<Integer> linesLength = new ArrayList<>();

        while (reader.hasNextLine()) {
            String tempString = reader.nextLine();
            int minimum = Integer.MAX_VALUE, nowLength = 0;
            for (int i = 0; i < tempString.length(); i++) {
                int begin = i;
                while (i < tempString.length() && !Character.isWhitespace(tempString.charAt(i))) {
                    i++;
                }
                if (begin != i) {
                    try {
                        int tmp = Integer.parseInt(tempString.substring(begin, i));
                        minimum = Math.min(minimum, tmp);
                        if (columnMin.size() >= nowLength + 1) {
                            columnMin.set(nowLength, Math.min(columnMin.get(nowLength), tmp));
                        } else {
                            columnMin.add(tmp);
                        }
                        nowLength++;
                    } catch (NumberFormatException e) {
                        System.err.println("Incorrect input");
                    }
                }
            }
            linesLength.add(nowLength);
            lineMin.add(minimum);
        }
        reader.close();

        for (int i = 0; i < linesLength.size(); i++) {
            for (int j = 0; j < linesLength.get(i); j++) {
                System.out.print(Math.min(columnMin.get(j), lineMin.get(i)));
                if (j != linesLength.get(i) - 1)
                    System.out.print(" ");
            }
            System.out.print("\n");
        }
    }
}