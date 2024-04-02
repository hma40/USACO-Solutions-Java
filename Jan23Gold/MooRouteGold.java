import java.io.*;
import java.util.*;
public class MooRouteGold {
    static long mod = 1000000007;
    static long[] factorial = new long[1100000];
    static long[] invFactorial = new long[1100000];
    static long binExp(long base, long exponent) {
        long[] binExponents = new long[33];
        binExponents[0]=base;
        long result = 1;
        for(int i = 1; i < 33; i++) {
            binExponents[i]=binExponents[i-1]*binExponents[i-1] % mod;
        }
        while(exponent>0) {
            int temp = (int)(Math.log(exponent)/Math.log(2));
            exponent-=1<<temp;
            result*=binExponents[temp];
            result%=mod;
        }
        return result;
    }
    static long choose(int a, int b) {
        return factorial[a] * invFactorial[b] % mod * invFactorial[a-b] % mod;
    }
    public static void main(String[] args) throws Exception {
        factorial[0]=invFactorial[0]=factorial[1]=invFactorial[1]=1;
        for(int i = 2; i < 1100000; i++) {
            factorial[i]=factorial[i-1]*i % mod;
            invFactorial[i]=binExp(factorial[i], mod-2);
        }
        InputOutput a = new InputOutput();
        int n = a.nextInt();
        long ans = 1;
        a.read();
        int first=a.nextInt()/2, last;
        for(int i = 1; i < n; i++) {
            last = a.nextInt()/2;
            if(last>first) {
                ans*=choose(last-1, first-1);
            } else {
                ans*=choose(first, last);
            }
            ans%=mod;
            first=last;
        }
        System.out.println(ans);
    }
    static class InputOutput {
        public BufferedReader bf;
        public PrintWriter out;
        public StringTokenizer st;

        public InputOutput() throws Exception {
            bf = new BufferedReader(new InputStreamReader(System.in));
            read();
        }

        public InputOutput(String fileName) throws Exception {
            bf = new BufferedReader(new FileReader(fileName + ".in"));
            out = new PrintWriter(new FileWriter(fileName + ".out"));
            read();
        }

        String next() throws Exception {
            return st.nextToken();
        }

        long nextLong() throws Exception {
            return Long.parseLong(st.nextToken());
        }

        int nextInt() throws Exception {
            return Integer.parseInt(st.nextToken());
        }

        void read() throws IOException {
            st = new StringTokenizer(bf.readLine());
        }

        void closeInput() throws IOException {
            bf.close();
        }

        void closeOutput() throws IOException {
            if (out != null) {
                out.close();
            }
        }
        void print(StringBuilder a) {
            if(out!=null) {
                out.print(a);
            } else {
                System.out.print(a);
            }
        }
        void println(Object a) {
            if (out == null) {
                System.out.println(a);
            } else {
                out.println(a);
            }
        }

        void println(int a) {
            if (out == null) {
                System.out.println(a);
            } else {
                out.println(a);
            }
        }
    }
}
