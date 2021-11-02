import java.io.*;
import java.util.Arrays;
import java.util.HashMap;

public class WordStatInput {
    public static void main(String[] args) {
        String[] words = new String[2];
        HashMap<String, Integer> counts = new HashMap<String, Integer>();
        int wordsLen = 0;

        try {
            Scanner in = new Scanner(new File(args[0]));
            try {
                while (in.hasNextWord()) {
                    String word = in.nextWord().toLowerCase();
                    if (!counts.containsKey(word)) {
                        if (wordsLen >= words.length) {
                            words = Arrays.copyOf(words, wordsLen * 2);
                        }
                        words[wordsLen++] = word;
                    }
                    counts.put(word, counts.getOrDefault(word, 0) + 1);
                }
            } finally {
                in.close();
            }
        } catch (FileNotFoundException e) {
            System.err.println("Input file not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("IO Exception happened while opening or closing input file: " + e.getMessage());
        }

        words = Arrays.copyOf(words, wordsLen);
        try {
            BufferedWriter output = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(args[1]),
                            "utf8"
                    )
            );
            try {
                for (String word : words) {
                    output.write(word + " " + counts.get(word) + System.lineSeparator());
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
