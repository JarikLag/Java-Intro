import java.util.*;
import java.io.*;

public class SumHexFile {
    public static void main(String[] args) {
        int sum = 0;
        if ((args == null) || (args.length < 2)) {
            System.err.println("Error: Names of files not found");
            return;
        }
        String inputFile = args[0];
        String outputFile = args[1];

        try {
            Scanner reader = new Scanner(new File(inputFile), "utf8");
            try {
                while (reader.hasNextLine()) {
					String line = reader.nextLine();
                    for (int i = 0; i < line.length(); i++) {
                        int begin = i;
                        while (i < line.length() && !Character.isWhitespace(line.charAt(i))) {
                            i++;
                        }
                        if (i != begin) {
                            try {
                                String tempString = line.substring(begin, i).toUpperCase();
                                if (tempString.startsWith("0X")) {
                                    sum += Integer.parseUnsignedInt(tempString.substring(2, tempString.length()), 16);
                                } else {
                                    sum += Integer.parseInt(tempString);
                                }
                            } catch (NumberFormatException e) {
                                System.err.println("Error: Incorrect input");
								return;
                            }
                        }
                    }
                }
            } finally {
                reader.close();
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: Input file not found");
        }

        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream((outputFile)), "utf8"));
            try {
                writer.write(Integer.toString(sum));
            } catch (IOException e) {
                System.err.println("Error while writing");
            } finally {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.err.println("Error: Couldn't close the output file");
                }
            }
        } catch (UnsupportedEncodingException e) {
            System.err.println("Error: Unsupported encoding");
        } catch (FileNotFoundException e) {
            System.err.println("Error: Cannot write to output file");
        }
    }
}
