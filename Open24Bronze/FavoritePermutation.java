import java.io.*;
import java.util.*;
public class FavoritePermutation {
    static final String ENDL = "\n";
    public static void main(String[] args) throws Exception {
        InputOutput io = new InputOutput();
        int t = io.nextInt();
        while(t-->0) {
            int n = io.nextInt();
            int[] numbers = new int[n-1];
            int cnt1 = 0;
            TreeSet<Integer> didntShow = new TreeSet<>();
            for(int i = 1; i <= n; i++) didntShow.add(i);
            for(int i = 1; i < n; i++) {
                //System.out.println(i);
                numbers[i-1]=io.nextInt();
                if(numbers[i-1]==1) cnt1++;
                didntShow.remove(numbers[i-1]);
            }
            //System.out.println("line 17");
            //System.out.println(n+" "+didntShow);
            if(numbers[n-2]!=1) {
                io.append(-1);
                continue;
            }

            if(didntShow.size()>2) {
                io.append(-1);
                continue;
            }
            if(didntShow.size()==2&&cnt1==1) {
                io.append(-1);
                continue;
            }
            int[] orig = new int[n];
            orig[0]=didntShow.first();
            orig[n-1]=didntShow.last();
            if(didntShow.size()==1) {
                orig[0]=1;
            }

            int pointer0 = 1;
            int pointer1 = n-2;
            for(int i = 0; i < n-2; i++) {
                if(orig[pointer0-1]>orig[pointer1+1]) {
                    orig[pointer0]=numbers[i];
                    pointer0++;
                } else {
                    orig[pointer1]=numbers[i];
                    pointer1--;
                }
            }
            io.ans.append(orig[0]);
            for(int i = 1; i < n; i++) {
                io.ans.append(" "+orig[i]);
            }
            io.ans.append(ENDL);
        }
        io.printAns();
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
