import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class CircularBarn {
    public static final int MAXVAL = 5000000;

    public static void main(String[] args) throws IOException {
        boolean[] isPrime = new boolean[MAXVAL + 1];
        for (int p = 1; p <= MAXVAL; p++) {
            isPrime[p] = true;
        }
        for (int p = 2; p <= MAXVAL; p++) {
            if (isPrime[p]) {
                for (int k = 2 * p; k <= MAXVAL; k += p) {
                    isPrime[k] = false;
                }
            }
        }
        int[] lastPrimes = new int[4];
        int[] steps = new int[MAXVAL + 1];
        for (int k = 1; k <= MAXVAL; k++) {
            if (k % 2 == 0) {
                steps[k] = k / 2;
            } else {
                if (isPrime[k]) {
                    lastPrimes[k % 4] = k;
                }
                steps[k] = 1 + steps[k - lastPrimes[k % 4]];
            }
        }
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder out = new StringBuilder();
        for (int t = Integer.parseInt(in.readLine()); t > 0; t--) {
            int n = Integer.parseInt(in.readLine());
            StringTokenizer tokenizer = new StringTokenizer(in.readLine());
            int[] rooms = new int[n];
            for (int j = 0; j < n; j++) {
                rooms[j] = Integer.parseInt(tokenizer.nextToken());
            }

            boolean johnWins = true;
            int minSteps = MAXVAL + 1;
            for (int j = 0; j < n; j++) {
                int stepsHere = steps[rooms[j]] / 2;
                if (stepsHere < minSteps) {
                    minSteps = stepsHere;
                    johnWins = steps[rooms[j]] % 2 == 1;
                }
            }
            out.append("Farmer ").append(johnWins ? "John" : "Nhoj").append('\n');
        }
        System.out.print(out);
    }
}