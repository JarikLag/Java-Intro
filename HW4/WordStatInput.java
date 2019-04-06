import java.util.*;
import java.io.*;

public class WordStatInput {
    public static void main(String[] args) throws IOException {
        String inputFile, outputFile;
        if ((args == null) || (args.length < 2)) {
            System.out.println("Error: Names of files not found");
            return;
        } else {
            inputFile = args[0];
            outputFile = args[1];
        }
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), "utf8"));
            Map<String, Integer> answerMap = new LinkedHashMap<>();
            while (true) {
                String line = reader.readLine();
                if (line != null) {
                    for (int i = 0; i < line.length(); i++) {
                        int begin = i;
                        while (i < line.length() && (Character.isLetter(line.charAt(i)) || (Character.getType(line.charAt(i)) == Character.DASH_PUNCTUATION) || line.charAt(i) == '\'')) {
                            i++;
                        }
                        if (begin != i) {
                            String tempString = line.substring(begin, i).toLowerCase();
                            if (answerMap.containsKey(tempString)) {
                                int tempValue = answerMap.get(tempString);
                                answerMap.put(tempString, tempValue + 1);
                            } else {
                                answerMap.put(tempString, 1);
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
                for (Map.Entry entry : answerMap.entrySet()) {
                    writer.println(entry.getKey() + " " + entry.getValue());
                }
                writer.close();
            } catch (FileNotFoundException e) {
                System.out.println("Error: Output file not found: " + e.getMessage());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: Input file not found: " + e.getMessage());
        }
    }
}