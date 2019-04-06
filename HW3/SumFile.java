import java.util.*;
import java.io.*;

public class SumFile {
    public static void main(String[] args) {
        String inputFile, outputFile;
        try {
            inputFile = args[0];
            outputFile = args[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Error: Names of files not found");
            return;
        }
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), "utf8"));
            int sum = 0;
            while (true) {
                String line = reader.readLine();
                if (line != null) {
                    for (int i = 0; i < line.length(); i++) {
                        int begin = i;
                        while (i < line.length() && !Character.isWhitespace(line.charAt(i))) {
                            i++;
                        }
                        if (i != begin) {
                            try {
                                sum += Integer.parseInt(line.substring(begin, i));
                            } catch (NumberFormatException e) {
                                System.out.println("Error: Incorrect input " + e.getMessage());
                                reader.close();
                                return;
                            }
                        }
                    }
                } else {
                    break;
                }
            }
            reader.close();
            try {
                PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(outputFile), "utf8"));
                writer.print(sum);
                writer.close();
            } catch (FileNotFoundException e) {
                System.out.println("Error: Output file not found " + e.getMessage());
                return;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: Input file not found " + e.getMessage());
            return;
        } catch (IOException e) {
		    System.out.println("Error" + e.getMessage());
			return;
		}
    }
}