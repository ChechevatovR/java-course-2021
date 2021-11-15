import scanner.Scanner;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

class WordComparator implements Comparator<String> {
    HashMap<String, Integer> counts;

    public WordComparator(HashMap<String, Integer> counts) {
        this.counts = counts;
    }

    @Override
    public int compare(String s1, String s2) {
        return counts.getOrDefault(s1, 0) - counts.getOrDefault(s2, 0);
    }
}

public class WordStatCount {
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
            } catch (IOException e) {
                System.err.println("IO Exception happened while reading input file: " + e.getMessage());
            } finally {
                in.close();
            }
        } catch (FileNotFoundException e) {
            System.err.println("Input file not found: " + e.getMessage());
        }

        words = Arrays.copyOf(words, wordsLen);
        Arrays.sort(words, new WordComparator(counts));
        try {
            BufferedWriter output = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(args[1]),
                        "utf8"
                )
            );
            try {
                for (String word : words) {
                    output.write(word);
                    output.write(" ");
                    output.write(counts.get(word).toString());
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
