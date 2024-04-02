import java.io.*;
import java.util.*;
public class LightsOff {
    public static void main(String[] args) throws Exception {
        InputOutput a = new InputOutput();
        int t = a.nextInt();
        int n = a.nextInt();
        ArrayList<Integer>[] possibleToggles = new ArrayList[70];
        for(int i = 0; i < 70; i++) possibleToggles[i] = new ArrayList<>();
        for(int i = 0; i < n; i++) possibleToggles[1].add(1<<i);
        for(int i = 1; i < 69; i++) {
            for(int j = 0; j < n; j++) {
                int nextSwitch = (j+i)%n;
                possibleToggles[i+1].add(possibleToggles[i].get(j)^(1<<nextSwitch));
            }
        }
        boolean[][] canTurnOn = new boolean[69][1<<n];
        canTurnOn[0][0]=true;
        for(int moves = 1; moves < 69; moves++) {
            for(int mask = 0; mask < 1<<n; mask++) {
                for(int firstMove = 0; firstMove<n; firstMove++) {
                    canTurnOn[moves][mask]=canTurnOn[moves][mask]||(canTurnOn[moves-1][mask^possibleToggles[moves].get(firstMove)]);
                }
            }
        }
        for(int i = 0; i < t; i++) {
            int lights=0,switches=0;
            String l = a.next(), s = a.next();
            for(int j = n-1; j >=0; j--) {
                if(l.charAt(j)=='1') {
                    lights+=1<<j;
                }
                if(s.charAt(j)=='1') {
                    switches+=1<<j;
                }
            }
            for(int j = 0; j < 69; j++) {
                if(canTurnOn[j][lights]) {
                    a.append(j);
                    break;
                }
                lights^=switches;
                switches*=2;
                if(switches>=(1<<n)) {
                    switches%=(1<<n);
                    switches++;
                }
            }
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
