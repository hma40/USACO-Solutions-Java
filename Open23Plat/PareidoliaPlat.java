import java.io.*;
import java.util.*;
public class PareidoliaPlat {
    static int n;
    static char[] word;
    public static void main(String[] args) throws Exception {
        InputOutput io = new InputOutput();
        word = io.next().toCharArray();
        n = word.length;
        SegTree st = new SegTree(n);
        //System.out.println(st);
        //System.out.println(st.query());
        io.append(st.query());
        int q = io.nextInt();
        for(int i = 0; i < q; i++) {
            st.update(io.nextInt()-1, io.next().toCharArray()[0]);
            io.append(st.query());
        }
        io.printAns();
    }
    static class SegTree {
        int n;
        Matrix[] tree;
        int[] begin, end;
        public long query() {
            return tree[1].data[6][7];
        }
        public String toString() {
            return Arrays.toString(tree);
        }
        public SegTree(int n) {
            while(n!=(n&(-n))) n++;
            this.n=n;
            tree = new Matrix[2*n];
            for(int i = 0; i < word.length; i++) {
                tree[i+n]=new Matrix(word[i], i);
            }
            for(int i = word.length+n; i < 2*n; i++) {
                tree[i] = new Matrix(-1, 1);
            }
            for(int i = n-1; i > 0; i--) {
                tree[i]=Matrix.mult(tree[2*i+1], tree[2*i]);
            }
            begin = new int[2*n];
            end = new int[2*n];
            for(int i = n; i < 2*n; i++) {
                begin[i]=end[i]=i-n;
            }
            for(int i = n-1; i > 0; i--) {
                begin[i]=begin[2*i];
                end[i]=end[2*i+1];
            }
        }
        public void update(int index, int character) {
            int i = index+n;
            tree[i] = new Matrix(character, index);
            i/=2;
            while(i>0) {
                 tree[i] = Matrix.mult(tree[2*i+1], tree[2*i]);
                 i/=2;
            }
        }
    }
    static class Matrix {
        long[][] data = new long[8][8];
        public String toString() {
            String ret = "";
            for(long[] l: data) ret+=Arrays.toString(l)+"\n";
            ret+="\n";
            return ret;
        }
        public Matrix(int type, int index) {
            for(int i = 0; i < 8; i++) {
                data[i][i]=1;
            }
            data[0][7]=1;
            if(type=='b') {
                //b
                data[0][7]=0;
                data[0][0]=0;
                data[1][0]=1;
                data[1][7]=1;
            } else if(type=='e') {
                //e
                data[1][1]=0;
                data[5][5]=0;
                data[2][1]=1;
                data[0][5]=1;
                data[6][5]=n-index;
            } else if(type=='s') {
                //s
                data[3][2]=1;
                data[4][3]=1;
                data[2][2]=0;
                data[3][3]=0;
            } else if(type=='i') {
                //i
                data[5][4]=1;
                data[4][4]=0;
            } else if(type==-1) {
                //ident
                data[0][7]=0;
            }
        }
        long[] apply(long[] x) {
            long[] ret = new long[8];
            for(int i = 0; i < 8; i++) {
                for(int j = 0; j < 8; j++) {
                    ret[i]+=x[j]*data[i][j];
                }
            }
            return ret;
        }
        public Matrix() {}
        static Matrix mult(Matrix a, Matrix b) {
            Matrix ret = new Matrix();
            for(int k = 0; k < 8; k++) {
                for(int i = 0; i < 8; i++) {
                    if(a.data[i][k]==0) continue;
                    for(int j = 0; j < 8; j++) {
                        if(b.data[k][j]==0) continue;
                        ret.data[i][j]+=a.data[i][k]*b.data[k][j];
                    }
                }
            }
            return ret;
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
