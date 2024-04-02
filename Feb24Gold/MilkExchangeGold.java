import java.io.*;
import java.util.*;
public class MilkExchangeGold {
    static final String ENDL = "\n";
    public static void main(String[] args) throws Exception {
        InputOutput io = new InputOutput();
        int n = io.nextInt();
        long[] capacity = new long[n];
        for(int i = 0; i < n; i++) capacity[i]=io.nextInt();
        long[] extendo = new long[3*n];
        for(int i = 0; i < 3*n; i++) {
            extendo[i]=capacity[i%n];
        }
        ArrayDeque<Integer> mono = new ArrayDeque<>();
        int[] LEbefore = new int[3*n];
        int[] LEafter = new int[3*n];
        Arrays.fill(LEbefore, -1);
        Arrays.fill(LEafter, -1);
        for(int i = 0; i < 3*n; i++) {
            while(mono.size()>0&&extendo[mono.peekLast()]>extendo[i]) {
                LEafter[mono.removeLast()]=i;
            }
            mono.add(i);
        }
        mono.clear();
        for(int i = 3*n-1; i >= 0; i--) {
            while(mono.size()>0&&extendo[mono.peekLast()]>=extendo[i]) {
                LEbefore[mono.removeLast()]=i;
            }
            mono.add(i);
        }
        long[] pSum = new long[3*n];
        for(int i = n; i < 2*n; i++) DoThis(i, pSum, LEbefore, LEafter, extendo);
        for(int i = 0; i < 2; i++) {
            for(int j = 1; j < 3*n; j++) {
                pSum[j]+=pSum[j-1];
            }
        }
        for(int i = 1; i <= n; i++) {
            io.append(pSum[i]);
        }
        io.printAns();
    }
    static void DoThis(int i, long[] pSum, int[] LEbefore, int[] LEafter, long[] extendo) {
        pSum[0]+=extendo[i];
        pSum[i-LEbefore[i]]-=extendo[i];
        if(LEafter[i]!=-1) {
            pSum[LEafter[i] - i] -= extendo[i];
            pSum[LEafter[i] - LEbefore[i]] += extendo[i];
        }
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
