import java.util.*;
import java.io.*;

public class ReverseSum {
    public static void main(String[] args) {
        List<Integer> lineSum = new ArrayList<>();
        List<Integer> columnSum = new ArrayList<>();
        List<List<Integer>> matrix = new ArrayList<>();
        try {
            FastScanner reader = new FastScanner(System.in);
            try {
                while (reader.hasNextLine()) {
                    String tempString = reader.nextLine();
                    int nowSum = 0, nowLength = 0;
                    List<Integer> nowList = new ArrayList<>();
                    for (int i = 0; i < tempString.length(); i++) {
                        int begin = i;
                        while (i < tempString.length() && !Character.isWhitespace(tempString.charAt(i))) {
                            i++;
                        }
                        if (begin != i) {
                            try {
                                int tmp = Integer.parseInt(tempString.substring(begin, i));
                                nowList.add(tmp);
                                nowSum += tmp;
                                if (columnSum.size() >= nowLength + 1) {
                                    columnSum.set(nowLength, columnSum.get(nowLength) + tmp);
                                } else {
                                    columnSum.add(tmp);
                                }
                                nowLength++;
                            } catch (NumberFormatException e) {
                                System.err.println("Incorrect input");
                                return;
                            }
                        }
                    }
                    matrix.add(nowList);
                    lineSum.add(nowSum);
                }
            } catch (IOException e) {
                System.err.println("Error while reading");
                return;
            } finally {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.err.println("Error: cannot close input file");
					return;
                }
            }
        } catch(UnsupportedEncodingException e) {
            System.err.println("Error : Unsupported encoding");
            return;
        }
        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.get(i).size(); j++) {
                System.out.print(columnSum.get(j) + lineSum.get(i) - matrix.get(i).get(j));
                if (j != matrix.get(i).size() - 1)
                    System.out.print(" ");
            }
            System.out.print("\n");
        }
    }
}