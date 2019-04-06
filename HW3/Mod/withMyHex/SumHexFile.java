import java.util.*;
import java.io.*;

public class SumHexFile {
    public static void main(String[] args) {
        String inputFile, outputFile;
        int sum = 0;
        String alphabet = "0123456789abcdef";
        if ((args == null) || (args.length < 2)) {
            System.out.println("Error: Names of files not found");
            return;
        } else {
            inputFile = args[0];
            outputFile = args[1];
        }
        BufferedReader reader = null;
        PrintWriter writer = null;
        try {
            try {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), "utf8"));
            } catch (FileNotFoundException e) {
                System.out.println("Error: " + e.toString());
                return;
            }
            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    for (int i = 0; i < line.length(); i++) {
                        int begin = i;
                        while (i < line.length() && (!Character.isWhitespace(line.charAt(i)))) {
                            i++;
                        }
                        if (i != begin) {
                            String tempString = line.substring(begin, i).toLowerCase();
                            if (tempString.length() > 2 && (tempString.charAt(0) == '0' && tempString.charAt(1) == 'x')) {
                                int val = 0;
                                for (int j = 2; j < tempString.length(); j++) {
                                    int digit = alphabet.indexOf(tempString.charAt(j));
                                    if (digit == -1) {
                                        System.out.println("Error: incorrect input");
                                        return;
                                    }
                                    val = val * 16 + digit;
                                }
                                sum += val;
                            } else {
                                try {
                                    sum += Integer.parseInt(tempString);
                                } catch (NumberFormatException e) {
                                    System.out.println("Error: incorrect input");
                                    return;
                                }
                            }
                        }
                    }
                }
            } finally {
                reader.close();
            }
            try {
                writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(outputFile), "utf8"));
                writer.println(sum);
            } catch (FileNotFoundException e) {
                System.out.println("Error: " + e.toString());
                return;
            } finally {
                if (writer != null) {
                    writer.close();
                }
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.toString());
            return;
        }
    }
}