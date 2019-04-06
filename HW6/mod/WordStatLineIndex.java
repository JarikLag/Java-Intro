import java.util.*;
import java.io.*;

public class WordStatLineIndex {
    public static void main(String[] args) {
        if ((args == null) || (args.length < 2)) {
            System.err.println("Error: Names of files not found");
            return;
        }
        String inputFile = args[0];
        String outputFile = args[1];

        Map<String, List<String>> answerMap = new TreeMap<>();
        try {
            FastScanner reader = new FastScanner(new FileInputStream(new File(inputFile)));
            try {
                int numOfLine = 1;
                while (reader.hasNextLine()) {
                    String line = reader.nextLine();
                    int numInLine = 1;
                    for (int i = 0; i < line.length(); i++) {
                        int begin = i;
                        while (i < line.length() && (Character.isLetter(line.charAt(i)) || (Character.getType(line.charAt(i)) == Character.DASH_PUNCTUATION) || line.charAt(i) == '\'')) {
                            i++;
                        }
                        if (begin != i) {
                            List<String> tempList;
                            String tempString = line.substring(begin, i).toLowerCase();
                            if (answerMap.containsKey(tempString)) {
                                tempList = answerMap.get(tempString);
                                tempList.add(Integer.toString(numOfLine) + ':' + Integer.toString(numInLine));
                                answerMap.put(tempString, tempList);
                            } else {
                                tempList = new ArrayList<>();
                                tempList.add(Integer.toString(numOfLine) + ':' + Integer.toString(numInLine));
                                answerMap.put(tempString, tempList);
                            }
                            numInLine++;
                        }
                    }
                    numOfLine++;
                }
            } catch (IOException e) {
                System.err.println("Error while reading");
                return;
            } finally {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.err.println("Error: cannot close input file");
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: Input file not found");
            return;
        } catch (UnsupportedEncodingException e) {
            System.err.println("Error : Unsupported encoding");
            return;
        }

        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream((outputFile)), "utf8"));
            try {
                for (Map.Entry<String, List<String>> entry : answerMap.entrySet()) {
                    List<String> value = entry.getValue();
                    String key = entry.getKey();
                    writer.write(key + " " + value.size());
                    for (int i = 0; i < value.size(); i++) {
                        writer.write(" " + value.get(i));
                    }
                    writer.newLine();
                }
            } catch (IOException e) {
                System.err.println("Error: Unknown IOException");
            } finally {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.err.println("Error: Unknown IOException - couldn't close the output file");
                }
            }
        } catch (UnsupportedEncodingException e) {
            System.err.println("Error: Unsupported encoding");
        } catch (FileNotFoundException e) {
            System.err.println("Error: Output file not found");
        }
    }
}