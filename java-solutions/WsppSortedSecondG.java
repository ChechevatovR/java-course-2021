import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

class IntList {
    private int[] c;
    public int length;

    IntList() {
        c = new int[1];
        length = 0;
    }

    IntList(int val) {
        c = new int[1];
        c[0] = val;
        length = 1;
    }

    public void add(int val) {
        if (length >= c.length) {
            c = Arrays.copyOf(c, length * 2);
        }
        c[length++] = val;
    }

    public int get(int pos) {
        return c[pos];
    }

    public void shrink() {
        c = Arrays.copyOf(c, length);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < length - 1; i += 2) {
            sb.append(c[i]);
            sb.append(" ");
        }
        if (length % 2 == 0) {
            sb.append(c[length - 1]);
        }
        if (sb.length() > 0 && sb.charAt(sb.length() - 1) == ' ') {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
}

public class WsppSortedSecondG {
    public static void main(String[] args) {
        ArrayList<String> words = new ArrayList<String>();
        Map<String, Map<Integer, IntList>> wordsByLines = new HashMap<String, Map<Integer, IntList>>();
        Map<String, Integer> wordCounts = new HashMap<String, Integer>();

        Scanner in = null;
        try {
            in = new Scanner(new File(args[0]));
            try {
                int curLine = 0;
                int curPos = 0;
                while (in.hasNextWord()) {
                    String word = in.nextWord().toLowerCase();
                    curLine += in.linesSkipped;
                    curPos++;
                    IntList line;
                    Map<Integer, IntList> wordMap = wordsByLines.get(word);
                    wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);

                    if (wordMap == null) {
                        words.add(word);
                        line = new IntList(curPos);
                        wordMap = new TreeMap<Integer, IntList>();
                        wordMap.put(curLine, line);
                        wordsByLines.put(word, wordMap);
                    } else {
                        line = wordMap.get(curLine);
                        if (line == null) {
                            line = new IntList(curPos);
                            wordMap.put(curLine, line);
                        } else {
                            line.add(curPos);
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println("IOException happened while Scanner was scanning: " + e.getMessage());
            } finally {
                in.close();
            }
        } catch (FileNotFoundException e) {
            System.err.println("Input file was not found: " + e.getMessage());
        } finally {
            if (in != null) {
                in.close();
            }
        }
        Collections.sort(words);
        try {
            BufferedWriter output = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(args[1]),
                            StandardCharsets.UTF_8
                    )
            );
            try {
                for (String word : words) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(word).append(" ").append(wordCounts.get(word)).append(" ");
                    for (Map.Entry<Integer, IntList> entry : wordsByLines.get(word).entrySet()) {
                        String s = entry.getValue().toString();
                        if (!s.isEmpty()) {
                            sb.append(s).append(" ");
                        }
                    }
                    if (sb.length() > 0 && sb.charAt(sb.length() - 1) == ' ') {
                        sb.deleteCharAt(sb.length() - 1);
                    }
                    output.write(sb.toString());
                    output.write(System.lineSeparator());
                }
            } catch (IOException e) {
                System.err.println("IO Exception happened while writing output file: " + e.getMessage());
            } finally {
                output.close();
            }
        } catch (IOException e) {
            System.err.println("IO Exception happened while opening or closing output file: " + e.getMessage());
        }
    }
}