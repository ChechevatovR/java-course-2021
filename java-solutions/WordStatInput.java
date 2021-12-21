import util.Scanner;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;

public class WordStatInput {
    public static void main(final String[] args) {
        String[] words = new String[2];
        final HashMap<String, Integer> counts = new HashMap<>();
        int wordsLen = 0;

        try {
            final Scanner in = new Scanner(new File(args[0]));
            try {
                while (in.hasNextWord()) {
                    final String word = in.nextWord().toLowerCase();
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
        } catch (final FileNotFoundException e) {
            System.err.println("Input file not found: " + e.getMessage());
        } catch (final IOException e) {
            System.err.println("IO Exception happened while opening or closing input file: " + e.getMessage());
        }

        words = Arrays.copyOf(words, wordsLen);
        try {
            final BufferedWriter output = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(args[1]),
                            StandardCharsets.UTF_8
                    )
            );
            try {
                for (final String word : words) {
                    output.write(word + " " + counts.get(word) + System.lineSeparator());
                }
            } catch (final IOException e) {
                System.err.println("IO Exception happened while writing output file: " + e.getMessage());
            } finally {
                output.close();
            }
        } catch (final IOException e) {
            System.err.println("IO Exception happened while opening or closing output file: " + e.getMessage());
        }
    }
}
