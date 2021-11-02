import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ReverseHexAbc2 {
    static String toLiteral(int val) {
        StringBuilder res = new StringBuilder();
        if (val < 0) {
            res.append('-');
            val *= -1;
        }
        String in = Integer.toString(val);
        for (int i = 0; i < in.length(); i++) {
            res.append((char) (in.charAt(i) - '0' + 'a'));
        }
        return res.toString();
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ArrayList<int[]> numbers = new ArrayList<int[]>();

        try {
            int[] line = new int[2];
            int lineLength = 0;
            while (in.hasNextNumber()) {
                int val = in.nextNumber();
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
        } catch (IOException e) {
            System.err.println("IOException happened while Scanner was scanning: " + e.getMessage());
        } finally {
            in.close();
        }

        for (int i = numbers.size() - 1; i >= 0; i--) {
            int[] line = numbers.get(i);
            for (int j = line.length - 1; j >= 0; j--) {
                System.out.print(toLiteral(line[j]) + " ");
            }
            System.out.println();
        }
    }
}