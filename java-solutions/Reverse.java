import util.Scanner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Reverse {
    public static void main(final String[] args) {
        final Scanner in = new Scanner(System.in);
        final ArrayList<int[]> numbers = new ArrayList<>();

        try {
            int[] line = new int[2];
            int lineLength = 0;
            while (in.hasNextInt()) {
                final int val = in.nextInt();
                for (int i = 0; i < in.linesSkipped; i++) {
                    numbers.add(Arrays.copyOfRange(line, 0, lineLength));
                    line = new int[2];
                    lineLength = 0;
                }

                if (lineLength == line.length) {
                    line = Arrays.copyOf(line, lineLength * 2);
                }
                line[lineLength++] = val;
            }
            for (int i = 0; i < in.linesSkipped; i++) {
                numbers.add(Arrays.copyOfRange(line, 0, lineLength));
                line = new int[2];
                lineLength = 0;
            }
        } catch (final IOException e) {
            System.err.println("IOException happened while Scanner was scanning: " + e.getMessage());
        } finally {
            in.close();
        }

        for (int i = numbers.size() - 1; i >= 0; i--) {
            final int[] line = numbers.get(i);
            for (int j = line.length - 1; j >= 0; j--) {
                System.out.print(line[j] + " ");
            }
            System.out.println();
        }
    }
}