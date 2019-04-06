import java.util.*;
import java.io.*;

public class WordStatWords {
    public static void main(String[] args) {
        if ((args == null) || (args.length < 2)) {
            System.err.println("Error: Names of files not found");
            return;
        }
        String inputFile = args[0];
        String outputFile = args[1];
		
        Map<String, Integer> answerMap = new TreeMap<>();
        try {
            Scanner reader = new Scanner(new File(inputFile), "utf8");
            try {
                while (reader.hasNextLine()) {
                    String line = reader.nextLine();
                    for (int i = 0; i < line.length(); i++) {
                        int begin = i;
                        while (i < line.length() && (Character.isLetter(line.charAt(i)) || (Character.getType(line.charAt(i)) == Character.DASH_PUNCTUATION) || line.charAt(i) == '\'')) {
                            i++;
                        }
                        if (begin != i) {
                            String tempString = line.substring(begin, i).toLowerCase();
                            if (answerMap.containsKey(tempString)) {
                                answerMap.put(tempString, answerMap.get(tempString) + 1);
                            } else {
                                answerMap.put(tempString, 1);
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
                for (Map.Entry entry : answerMap.entrySet()) {
                    String tmp = entry.getKey() + " " + entry.getValue() + "\n";
                    writer.write(tmp);
                }
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
