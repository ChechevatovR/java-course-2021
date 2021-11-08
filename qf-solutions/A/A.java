import java.util.Scanner;

public class A {
    private static int divCeil(int numerator, int denominator) {
        return (numerator + denominator - 1) / denominator;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int a = in.nextInt();
        int b = in.nextInt();
        int n = in.nextInt();
        in.close();

        System.out.println(2 * divCeil(n - b, b - a) + 1);

    }
}
