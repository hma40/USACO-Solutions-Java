import java.io.*;
import java.util.*;
public class HaybaleDistributionTernarySearch {
    public static void main(String[] args) throws Exception {
        InputOutput a = new InputOutput();
        int n = a.nextInt();
        long[] haybales = new long[n];
        for(int i = 0; i < n; i++) haybales[i] = a.nextLong();
        Arrays.sort(haybales);
        ArrayList<Long> acoef = new ArrayList<>();
        ArrayList<Long> bcoef = new ArrayList<>();
        long sufsum = 0;
        for(int i = 0; i < n; i++) sufsum+=haybales[i];
        long prefSum = 0;
        for(int i = 0; i < n; i++) {
            long ac = (i)*haybales[i]-prefSum;
            sufsum-=haybales[i];
            long bc = sufsum-haybales[i]*(n-i-1);
            prefSum+=haybales[i];
            if(acoef.size()>0) {
                if(ac==acoef.get(acoef.size()-1)&&bc==bcoef.get(bcoef.size()-1)) {
                    continue;
                }
            }
            acoef.add(ac);
            bcoef.add(bc);
        }

        for(int q = a.nextInt(); q>0; q--) {
            int left = 0;
            int right = bcoef.size()-1;
            long x = a.nextLong(), y = a.nextLong();
            while(left+2<right) {
                int leftThird = (2*left+right)/3;
                int rightThird = (left+2*right)/3;
                long fleft = acoef.get(leftThird)*x+bcoef.get(leftThird)*y;
                long fright = acoef.get(rightThird)*x+bcoef.get(rightThird)*y;
                if(fleft<fright) {
                    right=rightThird;
                } else {
                    left=leftThird;
                }
            }
//            System.out.println();
            long temp1 = Math.min(acoef.get(left)*x+bcoef.get(left)*y, acoef.get(left+1)*x+bcoef.get(left+1)*y);
            long temp2 = Math.min(acoef.get(right)*x+bcoef.get(right)*y, acoef.get(right-1)*x+bcoef.get(right-1)*y);
            a.append(Math.min(temp1,temp2));
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
