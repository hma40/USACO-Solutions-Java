import java.io.*;
import java.util.*;
public class HaybaleDistribution {
    public static void main(String[] args) throws Exception {
        InputOutput a = new InputOutput();
        int n = a.nextInt();
        long[] haybales = new long[n];
        for(int i = 0; i < n; i++) haybales[i] = a.nextLong();
        Arrays.sort(haybales);
        long[] acoef = new long[n];
        long[] bcoef = new long[n];
        long sufsum = 0;
        for(int i = 0; i < n; i++) sufsum+=haybales[i];
        long prefSum = 0;
        for(int i = 0; i < n; i++) {
            acoef[i]=(i)*haybales[i]-prefSum;
            sufsum-=haybales[i];
            bcoef[i]=sufsum-haybales[i]*(n-i-1);
            prefSum+=haybales[i];
        }
        for(int q = a.nextInt(); q>0; q--) {
            long x = a.nextLong();
            long y = a.nextLong();
            int mid = (int)((y*n)/(x+y));
            long ansHere = x*acoef[mid]+y*bcoef[mid];
            if(mid>0) {
                ansHere=Math.min(ansHere, x*acoef[mid-1]+y*bcoef[mid-1]);
            }
            if(mid<n-1) {
                ansHere=Math.min(ansHere, x*acoef[mid+1]+y*bcoef[mid+1]);
            }
            a.append(ansHere);
        }
        a.printAns();
    }
    static class InputOutput {
        InputStream stream;
        byte[] buf = new byte[1<<16];
        int curChar;
        int numChars;
        PrintWriter pw;
        StringBuilder ans = new StringBuilder();
        public InputOutput() throws Exception {
            pw = new PrintWriter(System.out);
            stream = System.in;
        }
        public InputOutput(String name) throws Exception {
            pw = new PrintWriter(new FileWriter(name+".out"));
            stream = new FileInputStream(name+".in");
        }
        private int nextByte() {
            if (numChars == -1) { throw new InputMismatchException(); }
            if (curChar >= numChars) {
                curChar = 0;
                try {
                    numChars = stream.read(buf);
                } catch (IOException e) { throw new InputMismatchException(); }
                if (numChars == -1) {
                    return -1;  // end of file
                }
            }
            return buf[curChar++];
        }
        public String next() {
            int c;
            do { c = nextByte(); } while (c <= ' ');

            StringBuilder res = new StringBuilder();
            do {
                res.appendCodePoint(c);
                c = nextByte();
            } while (c > ' ');
            return res.toString();
        }
        public int nextInt() {  // nextLong() would be implemented similarly
            int c;
            do { c = nextByte(); } while (c <= ' ');

            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = nextByte();
            }

            int res = 0;
            do {
                if (c < '0' || c > '9') { throw new InputMismatchException(); }
                res = 10 * res + c - '0';
                c = nextByte();
            } while (c > ' ');
            return res * sgn;
        }
        public long nextLong() {  // nextLong() would be implemented similarly
            int c;
            do { c = nextByte(); } while (c <= ' ');

            long sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = nextByte();
            }

            long res = 0;
            do {
                if (c < '0' || c > '9') { throw new InputMismatchException(); }
                res = 10 * res + c - '0';
                c = nextByte();
            } while (c > ' ');
            return res * sgn;
        }
        public double nextDouble() { return Double.parseDouble(next()); }
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
