import java.io.*;
import java.util.*;
public class CandyCaneFeast {
    public static void main(String[] args) throws Exception {
        InputOutput a = new InputOutput();
        int n = a.nextInt();
        int m = a.nextInt();
        long[] init = new long[n];
        a.read();
        for(int i = 0; i < n; i++) {
            init[i] = a.nextInt();
        }
        a.read();
        for(int i = 0; i < m; i++) {
            long candy = a.nextLong();
            long low = 0;
            for(int j = 0; j < n; j++) {
                if(init[j]>low) {
                    if(init[j]>= candy) {
                        init[j]+= candy -low;
                        break;
                    } else {
                        long temp = low;
                        low=init[j];
                        init[j]+=init[j]-temp;
                    }
                }
            }
        }
        for(int i = 0; i < n; i++) a.append(init[i]);
        a.printAns();
    }
    static class InputOutput {
        BufferedReader bf;
        StringTokenizer st;
        StringBuilder ans = new StringBuilder();
        PrintWriter pw;

        public InputOutput() throws IOException {
            bf = new BufferedReader(new InputStreamReader(System.in));
            pw = new PrintWriter(System.out);
            st = new StringTokenizer(bf.readLine());
        }

        public InputOutput(String fileName) throws IOException {
            bf = new BufferedReader(new FileReader(fileName + ".in"));
            pw = new PrintWriter(new FileWriter(fileName + ".out"));
            st = new StringTokenizer(bf.readLine());
        }

        public void read() throws Exception {
            st = new StringTokenizer(bf.readLine());
        }

        public String next() throws Exception {
            return st.nextToken();
        }

        public int nextInt() throws Exception {
            return Integer.parseInt(st.nextToken());
        }

        public long nextLong() throws Exception {
            return Long.parseLong(st.nextToken());
        }

        public void append(int i) {
            ans.append(i + "\n");
        }
        public void append(long i) {
            ans.append(i + "\n");
        }
        public void append(String s) {
            ans.append(s + "\n");
        }
        public void printAns() {
            pw.print(ans);
            pw.close();
        }
    }
}
