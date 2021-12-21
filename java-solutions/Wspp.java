import util.Scanner;
import util.IntList;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.ArrayList;


public class Wspp {
    public static void main(final String[] args) {
        final ArrayList<String> words = new ArrayList<>();
        final HashMap<String, util.IntList> counts = new HashMap<>();

        try {
            final Scanner in = new Scanner(new File(args[0]));
            try {
                int i = 0;
                while (in.hasNextWord()) {
                    i++;
                    final String word = in.nextWord().toLowerCase();
                    util.IntList wordList = counts.get(word);
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
        } catch (final FileNotFoundException e) {
            System.err.println("Input file not found: " + e.getMessage());
        } catch (final IOException e) {
            System.err.println("IO Exception happened while opening or closing input file: " + e.getMessage());
        }

        try {
            final BufferedWriter output = new BufferedWriter(
                new OutputStreamWriter(
                    new FileOutputStream(args[1]),
                        StandardCharsets.UTF_8
                )
            );
            try {
                for (final String word : words) {
                    final util.IntList cur = counts.get(word);
                    output.write(word);
                    output.write(" ");
                    output.write(Integer.toString(cur.length));
                    output.write(" ");
                    output.write(cur.toString());
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
