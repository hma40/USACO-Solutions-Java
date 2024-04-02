import java.io.*;
import java.util.*;
public class TheWinningGene {
    static final String ENDL = "\n";
    static char[] s;
    static int[][] longestCommonPrefix;
    public static void main(String[] args) throws Exception {
        InputOutput io = new InputOutput();
        int n = io.nextInt();
        s = io.next().toCharArray();
        longestCommonPrefix = new int[n][n];
        for(int i = 0; i < n; i++) {
            if(s[i]==s[n-1]) {
                longestCommonPrefix[n-1][i]=longestCommonPrefix[i][n-1]=1;
            } else {
                longestCommonPrefix[n-1][i]=longestCommonPrefix[i][n-1]=0;
            }
        }
        for(int i = n-2; i >= 0; i--) {
            for(int j = 0; j < n-1; j++) {
                if(s[i]==s[j]) {
                    longestCommonPrefix[i][j]=longestCommonPrefix[i+1][j+1]+1;
                } else {
                    longestCommonPrefix[i][j]=0;
                }
            }
        }

        int[][] lowerBound = new int[n+1][n];
        //lowerBound[length][startpos]
        int[][] upperBound = new int[n+1][n];
        for(int pos = 0; pos < n; pos++) {
            int lb = pos-1;
            for(int len = 1; len < n-pos+1; len++) {
                while(lb>=0&&!LEQ(lb, pos, len)) lb--;
                lowerBound[len][pos]=lb;
            }
        }
        for(int pos = 0; pos < n; pos++) {
            int ub = pos+1;
            for(int len = n-pos; len >=1; len--) {
                while(ub<n-len+1&&!LESS(ub, pos, len)) {
                    ub++;
                }
                upperBound[len][pos]=ub;
            }
        }
        int[] ans = new int[n+1];
        for(int l = 1; l <= n; l++) {
            int[] geneCount = new int[n+2];
            for(int pos = 0; pos < n-l+1; pos++) {
                geneCount[l]++;
                geneCount[l+upperBound[l][pos]-lowerBound[l][pos]-1]--;
            }
            for(int i = 1; i <= n; i++) {
                geneCount[i]=geneCount[i-1]+geneCount[i];
            }
            for(int i = 1; i <= n; i++) {
                ans[geneCount[i]]++;
            }
        }
        for(int i = 1; i <= n; i++) {
            io.append(ans[i]);
        }
        io.printAns();
    }
    static boolean LESS(int pos0, int pos1, int len) {
        //return if s[pos0, pos0+len-1]<s[pos1, pos1+len]
        if(longestCommonPrefix[pos0][pos1]>=len) return false;
        if(s[pos0+longestCommonPrefix[pos0][pos1]]<s[pos1+longestCommonPrefix[pos0][pos1]]) return true;
        return false;
    }
    static boolean LEQ(int pos0, int pos1, int len) {
        //return if s[pos0, pos0+len-1]<=s[pos1, pos1+len]
        if(longestCommonPrefix[pos0][pos1]>=len) return true;
        if(s[pos0+longestCommonPrefix[pos0][pos1]]<=s[pos1+longestCommonPrefix[pos0][pos1]]) return true;
        return false;
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
        public int[] nextIntArr(int size) {
            int[] ret = new int[size];
            for(int i = 0; i < size; i++) ret[i]=nextInt();
            return ret;
        }
        public long[] nextLongArr(int size) {
            long[] ret = new long[size];
            for(int i = 0; i < size; i++) ret[i]=nextInt();
            return ret;
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
