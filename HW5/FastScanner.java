import java.io.*;

public class FastScanner {
    private BufferedReader reader;
    private String line;
    private boolean isLoaded;

    FastScanner(InputStream in) {
        try {
            reader = new BufferedReader(new InputStreamReader(in, "utf8"));
        } catch (UnsupportedEncodingException e) {
            System.err.println("Error: Unsupported encoding");
        }
    }

    public boolean hasNextLine() {
        if (reader == null) {
            return false;
        } else if (isLoaded) {
            return true;
        } else {
            try {
                line = reader.readLine();
                if (line != null) {
                    isLoaded = true;
                    return true;
                } else {
                    return false;
                }
            } catch (IOException e) {
				System.err.println("Error: Unknown IOException");
                return false;
            }
        }
    }

    public String nextLine()  {
        if(hasNextLine()) {
            isLoaded = false;
            return line;
        } else {
            return null;
        }
    }

    public void close() {
        try {
            reader.close();
        } catch (IOException e) {
            System.err.println("Error: Unknown IOException - couldn't close the output file");
        }
    }
}