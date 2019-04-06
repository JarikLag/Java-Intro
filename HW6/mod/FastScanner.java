import java.io.*;

public class FastScanner {
    private BufferedReader reader;
    private String line;
    private boolean isLoaded;

    public FastScanner(InputStream in) throws UnsupportedEncodingException {
        reader = new BufferedReader(new InputStreamReader(in, "utf8"));
    }

    public boolean hasNextLine() throws IOException {
        if (reader == null) {
            return false;
        } else if (isLoaded) {
            return true;
        } else {
            line = reader.readLine();
            if (line != null) {
                isLoaded = true;
                return true;
            } else {
                return false;
            }
        }
    }

    public String nextLine() throws IOException {
        if (hasNextLine()) {
            isLoaded = false;
            return line;
        } else {
            return null;
        }
    }

    public void close() throws IOException {
        reader.close();
    }
}