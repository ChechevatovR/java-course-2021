import util.Scanner;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

class WordComparator implements Comparator<String> {
    private final HashMap<String, Integer> counts;

    public WordComparator(final HashMap<String, Integer> counts) {
        this.counts = counts;
    }

    @Override
    public int compare(final String s1, final String s2) {
        return counts.getOrDefault(s1, 0) - counts.getOrDefault(s2, 0);
    }
}

public class WordStatCount {
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
            } catch (final IOException e) {
                System.err.println("IO Exception happened while reading input file: " + e.getMessage());
            } finally {
                in.close();
            }
        } catch (final FileNotFoundException e) {
            System.err.println("Input file not found: " + e.getMessage());
        }

        words = Arrays.copyOf(words, wordsLen);
        Arrays.sort(words, new WordComparator(counts));
        try {
            final BufferedWriter output = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(args[1]),
                        StandardCharsets.UTF_8
                )
            );
            try {
                for (final String word : words) {
                    output.write(word);
                    output.write(" ");
                    output.write(counts.get(word).toString());
                    output.write(System.lineSeparator());
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
