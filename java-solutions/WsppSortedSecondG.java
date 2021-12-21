import util.Scanner;

import util.IntList;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class WsppSortedSecondG {
    public static void main(final String[] args) {
        final ArrayList<String> words = new ArrayList<>();
        final Map<String, Map<Integer, IntList>> wordsByLines = new HashMap<>();
        final Map<String, Integer> wordCounts = new HashMap<>();

        Scanner in = null;
        try {
            in = new Scanner(new File(args[0]));
            try {
                int curLine = 0;
                int curPos = 0;
                while (in.hasNextWord()) {
                    final String word = in.nextWord().toLowerCase();
                    curLine += in.linesSkipped;
                    curPos++;
                    IntList line;
                    Map<Integer, IntList> wordMap = wordsByLines.get(word);
                    wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);

                    if (wordMap == null) {
                        words.add(word);
                        line = new IntList(curPos);
                        wordMap = new HashMap<>();
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
            } catch (final IOException e) {
                System.err.println("IOException happened while Scanner was scanning: " + e.getMessage());
            } finally {
                in.close();
            }
        } catch (final FileNotFoundException e) {
            System.err.println("Input file was not found: " + e.getMessage());
        } finally {
            if (in != null) {
                in.close();
            }
        }
        Collections.sort(words);
        try {
            final BufferedWriter output = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(args[1]),
                            StandardCharsets.UTF_8
                    )
            );
            try {
                for (final String word : words) {
                    final StringBuilder sb = new StringBuilder();
                    sb.append(word).append(" ");
                    sb.append(wordCounts.get(word)).append(" ");
                    for (final Map.Entry<Integer, IntList> entry : wordsByLines.get(word).entrySet()) {
                        final IntList positions = entry.getValue();
                        for (int i = 1; i < positions.length; i += 2) {
                            sb.append(positions.get(i)).append(" ");
                        }
                    }
                    sb.setLength(sb.length() - 1);
                    output.append(sb.toString()).append(System.lineSeparator());
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