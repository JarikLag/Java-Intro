package md2html;

import java.util.*;
import java.io.*;

public class Md2Html {
    private static final Map<String, String> tags = new HashMap<>();

    static {
        tags.put("*", "em");
        tags.put("_", "em");
        tags.put("**", "strong");
        tags.put("__", "strong");
        tags.put("--", "s");
        tags.put("`", "code");
        tags.put("++", "u");
        tags.put("~", "mark");
    }

    private static final Map<Character, String> htmlEscape = new HashMap<>();

    static {
        htmlEscape.put('<', "&lt;");
        htmlEscape.put('>', "&gt;");
        htmlEscape.put('&', "&amp;");
    }

    private static boolean isEndOfLine(int a, int b) {
        String c = String.valueOf((char)a) + (char)b;
        return (c.equals(System.lineSeparator()) || Character.toString((char)b).equals(System.lineSeparator()));
    }

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]), "utf8"))) {
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), "utf8"))) {
                int ch;
                int prevCh = -1;
                int prevChCnt = 0;
                int newLineCnt = 0;
                int headerLevel = 0;
                boolean textStarted = false;
                boolean newBlock = true;
                boolean hasSpaceBefore = false;
                boolean escaped = false;
                boolean doubleSeparator = false;
                Set<String> open = new HashSet<>();
                while ((ch = reader.read()) != -1) {
                    if (doubleSeparator) {
                        doubleSeparator = false;
                        continue;
                    }
                    if (System.lineSeparator().length() == 2 && ch == '\r') {
                        doubleSeparator = true;
                    }
                    if (!Character.isWhitespace((char)ch)) {
                        hasSpaceBefore = false;
                    }
                    if (Character.isWhitespace((char)prevCh)) {
                        hasSpaceBefore = true;
                    }
                    if (isEndOfLine(prevCh, ch) || doubleSeparator) {
                        newLineCnt++;
                    } else {
                        if (newLineCnt == 1 && textStarted) {
                            writer.write(System.lineSeparator());
                        } else if (newLineCnt >= 2) {
                            newBlock = true;
                            open.clear();
                            if (textStarted) {
                                if (headerLevel > 0) {
                                    writer.write("</h" + headerLevel + ">" + System.lineSeparator());
                                    headerLevel = 0;
                                } else {
                                    writer.write("</p>" + System.lineSeparator());
                                }
                            }
                        }
                        textStarted = true;
                        newLineCnt = 0;
                        if (newBlock) {
                            if (Character.isWhitespace((char)ch) && prevCh == '#') {
                                headerLevel = prevChCnt;
                                writer.write("<h" + headerLevel + ">");
                            } else if (ch != '#') {
                                writer.write("<p>");
                                if (prevCh == '#') {
                                    for (int i = 0; i < prevChCnt; i++) {
                                        writer.write('#');
                                    }
                                }
                                newBlock = false;
                            }
                        }
                    }
                    String prevCombined = String.valueOf((char)prevCh);
                    if (prevChCnt > 1) {
                        prevCombined += (char)prevCh;
                    }
                    if (tags.containsKey(prevCombined) && prevCh != ch) {
                        if (prevChCnt > 2) {
                            for (int i = 0; i < prevChCnt; i++) {
                                writer.write(prevCh);
                            }
                        } else if (!Character.isWhitespace((char)ch) && !open.contains(prevCombined)) {
                            writer.write("<" + tags.get(prevCombined) + ">");
                            open.add(prevCombined);
                        } else if (open.contains(prevCombined) && !(hasSpaceBefore && Character.isWhitespace((char)ch))) {
                            writer.write("</" + tags.get(prevCombined) + ">");
                            open.remove(prevCombined);
                        } else {
                            for (int i = 0; i < prevChCnt; i++) {
                                writer.write(prevCh);
                            }
                        }
                    }
                    if (!isEndOfLine(prevCh, ch) && !doubleSeparator) {
                        reader.mark(1);
                        int nextCh = reader.read();
                        reader.reset();
                        if (prevCh == '\\') {
                            writer.write(ch);
                            escaped = true;
                        } else if (!tags.containsKey(String.valueOf((char)prevCh) + (char)ch) &&
                                !tags.containsKey(String.valueOf((char)ch)) &&
                                !tags.containsKey(String.valueOf((char)ch) + (char)nextCh) && ch != '\\') {
                            if (Character.isWhitespace((char)ch) && newBlock) {
                                newBlock = false;
                            } else {
                                if (htmlEscape.containsKey((char)ch)) {
                                    writer.write(htmlEscape.get((char)ch));
                                } else if (!newBlock) {
                                    if (ch != '\r' && ch != '\n')
                                        writer.write(ch);
                                }
                            }
                        }
                    }
                    if (escaped) {
                        prevCh = -1;
                        prevChCnt = 0;
                        escaped = false;
                    } else if (ch == prevCh) {
                        prevChCnt++;
                    } else {
                        prevCh = ch;
                        prevChCnt = 1;
                    }
                }
				if (headerLevel > 0) {
					writer.write("</h" + headerLevel + ">");
				} else {
					writer.write("</p>");
				}
                writer.write(System.lineSeparator());

            } catch (UnsupportedEncodingException e) {
                System.out.println("Unsupported encoding");
            } catch (FileNotFoundException e) {
                System.out.println("Cannot write to output file");
            } catch (IOException e) {
                System.out.println("Error while writing");
            }
        } catch (UnsupportedEncodingException e) {
            System.out.println("Unsupported encoding");
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find input file");
        } catch (IOException e) {
            System.out.println("Error while reading");
        }
    }
}
