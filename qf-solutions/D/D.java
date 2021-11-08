import java.util.Scanner;

public class D {
    static final long MOD = 998_244_353;

    static int[] powers;
    static int[] twoPalindromes;
    static int[] doublePalindromes;
    static int n;
    static int k;

    private static int add(int x, int y) {
        return (int) ((x + y) % MOD);
    }

    private static int sub(int x, int y) {
        return (int) ((x - y + MOD) % MOD);
    }

    private static int mul(long x, long y) {
        return (int) ((x * y) % MOD);
    }

    private static void build() {
        powers = new int[n / 2 + 2];
        twoPalindromes = new int[n + 1];
        doublePalindromes = new int[n + 1];
        powers[0] = 1;

        for (int len = 1; len < powers.length; len++) {
            powers[len] = mul(powers[len - 1], k);
        }

        for (int len = 1; len <= n; len++) {
            twoPalindromes[len] =
                    len % 2 == 0 ?
                    mul(len / 2, add(powers[len / 2], powers[len / 2 + 1])) :
                    mul(len, powers[(len + 1) / 2]);
        }

        for (int len = 1; len <= n; len++) {
            doublePalindromes[len] = add(doublePalindromes[len], twoPalindromes[len]);
            for (int j = 2; len * j <= n; j++) {
                doublePalindromes[len * j] = sub(
                    doublePalindromes[len * j],
                    mul(j, doublePalindromes[len])
                );
            }
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        n = in.nextInt();
        k = in.nextInt();
        in.close();

        build();

        int ans = 0;
        for (int len = 1; len <= n; len++) {
            ans = add(ans, mul(doublePalindromes[len], n / len));
        }
        System.out.println(ans);
    }
}