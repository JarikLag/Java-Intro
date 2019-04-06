import java.util.*;
import java.io.*;

public class WordStatIndex {
    public static void main(String[] args) {
        if ((args == null) || (args.length < 2)) {
            System.err.println("Error: Names of files not found");
            return;
        }
        String inputFile = args[0];
        String outputFile = args[1];
		
        Map<String, List<Integer>> answerMap = new LinkedHashMap<>();
        try {
            Scanner reader = new Scanner(new File(inputFile), "utf8");
            try {
                String line;
                int step = 1;
                while (reader.hasNextLine()) {
                    line = reader.nextLine();
                    for (int i = 0; i < line.length(); i++) {
                        int begin = i;
                        while (i < line.length() && (Character.isLetter(line.charAt(i)) || (Character.getType(line.charAt(i)) == Character.DASH_PUNCTUATION) || line.charAt(i) == '\'')) {
                            i++;
                        }
                        if (begin != i) {
                            List<Integer> tempList;
                            String tempString = line.substring(begin, i).toLowerCase();
                            if (answerMap.containsKey(tempString)) {
                                tempList = answerMap.get(tempString);
                                tempList.add(step);
                                answerMap.put(tempString, tempList);
                            } else {
                                tempList = new ArrayList<>();
                                tempList.add(step);
                                answerMap.put(tempString, tempList);
                            }
                            step++;
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
                for (Map.Entry<String, List<Integer>> entry : answerMap.entrySet()) {
                    List<Integer> value = entry.getValue();
                    String key = entry.getKey();
                    writer.write(key + " " + Integer.toString(value.size()));
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
            System.err.println("Error: Cannot write to output file");
        }
    }
}