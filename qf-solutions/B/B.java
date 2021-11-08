import java.util.Scanner;

public class B {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        in.close();

        for (int i = -710 * 25000; n > 0; n--, i += 710) {
            System.out.println(i);
        }
    }
}
