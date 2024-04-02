import java.io.*;
import java.util.*;
public class BovineAcrobatics {
    public static void main(String[] args) throws Exception {
        InputOutput a = new InputOutput();
        int n  = a.nextInt();
        long m = a.nextLong(), k = a.nextLong();
        CowGroup[] groups = new CowGroup[n];
        for(int i = 0; i < n; i++) {
            groups[i] = new CowGroup(a.nextLong(), a.nextLong());
        }
        Arrays.sort(groups);
        ArrayDeque<CowGroup> q = new ArrayDeque<>();
        q.addLast(new CowGroup(Long.MAX_VALUE, m));
        long ans = 0;
        for(CowGroup cg: groups) {
//            System.out.println(q+" "+ans);
            long remaining = cg.number;
            while(q.size()>0&&remaining>0&&cg.weight+k<=q.peekFirst().weight) {
                if(q.peekFirst().number>remaining) {
                    q.peekFirst().number-=remaining;
                    remaining=0;
                } else {
                    remaining-=q.peekFirst().number;
                    q.removeFirst();
                }
//                System.out.println(cg+" "+remaining);
            }
            long count = cg.number-remaining;
            if(count>0) {
                q.addLast(new CowGroup(cg.weight, count));
                ans+=count;
            }
        }
        System.out.println(ans);
    }
    static class CowGroup implements Comparable<CowGroup>{
        long weight;
        long number;
        public String toString() {
            return weight+" "+number;
        }
        public CowGroup(long w, long n) {
            weight=w;
            number=n;
        }
        public int compareTo(CowGroup other) {
            return -Long.compare(weight, other.weight);
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
