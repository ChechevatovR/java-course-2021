import java.util.Arrays;
import java.util.Scanner;

public class ReverseSum2 {
    public static int[] scanLine(final Scanner in) {
        int[] line = new int[2];
        int n = 0;
        while (in.hasNextInt()) {
            if (n >= line.length) {
                line = Arrays.copyOf(line, n * 2);
            }
            line[n++] = in.nextInt();
        }
        return Arrays.copyOf(line, n); // shrink to fit
    }
    
    public static void main(final String[] args) {
        final Scanner in = new Scanner(System.in);
        int[][] numbers = new int[2][];
        int lines = 0;
        int maxWidth = 0;
        
        while (in.hasNextLine()) {
            if (lines >= numbers.length) {
                numbers = Arrays.copyOf(numbers, lines * 2);
            }
            numbers[lines] = scanLine(new Scanner(in.nextLine()));
            maxWidth = Math.max(maxWidth, numbers[lines].length);
            lines++;
        }
        
        final int[] columnSum = new int[maxWidth];
        for (int i = 0; i < lines; i++) {
            int prevSum = 0;
            for (int j = 0; j < numbers[i].length; j++) {
                prevSum = prevSum + columnSum[j] + numbers[i][j];
                System.out.print(prevSum + " ");
                columnSum[j] += numbers[i][j];
            }
            System.out.println();
        }
    }
}