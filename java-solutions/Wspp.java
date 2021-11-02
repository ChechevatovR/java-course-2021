import java.io.*;
import java.util.HashMap;
import java.util.ArrayList;

//class IntList {
//    private int[] c;
//    public int length;
//
//    IntList() {
//        c = new int[0];
//        length = 0;
//    }
//
//    IntList(int val) {
//        c = new int[1];
//        c[0] = val;
//        length = 1;
//    }
//
//    public void add(int val) {
//        if (length == c.length) {
//            c = Arrays.copyOf(c, length * 2);
//        }
//        c[length++] = val;
//    }
//
//    public int get(int pos) {
//        return c[pos];
//    }
//
//    public void shrink() {
//        c = Arrays.copyOf(c, length);
//    }
//
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < length - 1; i++) {
//            sb.append(c[i]);
//            sb.append(" ");
//        }
//        sb.append(c[length - 1]);
//        return sb.toString();
//    }
//}

public class Wspp {
    public static void main(String[] args) {
        ArrayList<String> words = new ArrayList<String>();
        HashMap<String, IntList> counts = new HashMap<String, IntList>();

        try {
            Scanner in = new Scanner(new File(args[0]));
            try {
                int i = 0;
                while (in.hasNextWord()) {
                    i++;
                    String word = in.nextWord().toLowerCase();
                    IntList wordList = counts.get(word);
                    if (wordList == null) {
                        words.add(word);
                        wordList = new IntList(i);
                        counts.put(word, wordList);
                    } else {
                        wordList.add(i);
                    }
                }
            } finally {
                in.close();
            }
        } catch (FileNotFoundException e) {
            System.err.println("Input file not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("IO Exception happened while opening or closing input file: " + e.getMessage());
        }

        try {
            BufferedWriter output = new BufferedWriter(
                new OutputStreamWriter(
                    new FileOutputStream(args[1]),
                    "utf8"
                )
            );
            try {
                for (String word : words) {
                    IntList cur = counts.get(word);
                    output.write(word);
                    output.write(" ");
                    output.write(Integer.toString(cur.length));
                    output.write(" ");
                    output.write(cur.toString());
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
