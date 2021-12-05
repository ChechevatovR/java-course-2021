import util.Scanner;
import util.IntList;

import java.io.*;
import java.util.HashMap;
import java.util.ArrayList;


public class Wspp {
    public static void main(String[] args) {
        ArrayList<String> words = new ArrayList<String>();
        HashMap<String, util.IntList> counts = new HashMap<String, util.IntList>();

        try {
            Scanner in = new Scanner(new File(args[0]));
            try {
                int i = 0;
                while (in.hasNextWord()) {
                    i++;
                    String word = in.nextWord().toLowerCase();
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
                    util.IntList cur = counts.get(word);
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
