import java.io.*;
import java.util.*;
public class QuantumMoochanics {
    static final String ENDL = "\n";
    public static void main(String[] args) throws Exception {
        InputOutput io = new InputOutput();
        int t = io.nextInt();
        for(;t>0;t--) {
            int n = io.nextInt();
            long[] pos = new long[n];
            long[] speed = new long[n];
            for(int i = 0; i < n; i++) pos[i]=io.nextLong();
            for(int i = 0; i < n; i++) speed[i]=io.nextLong();
            //difference of x with speed a,b with a going to right initially
            //first observation: difference of x-a-b
            //second observation: difference of x+a+b
            //third observation: difference of x-2a-2b

            //other case:
            //x+a+b
            //x-a-b
            //x+2a+2b
            //x-2a-2b
            //x+3a+3b
            //x-3a-3b
            boolean[] exists = new boolean[n];
            int[] firstAfter = new int[n];
            int[] firstBefore = new int[n];
            for(int i = 0; i < n; i++) {
                firstAfter[i]=firstBefore[i]=i;
            }
            long[] ans = new long[n];
            Arrays.fill(exists, true);
            PriorityQueue<Interval> pq = new PriorityQueue<>();
            for(int i = 0; i < n-1; i++) {
                long diff = pos[i+1]-pos[i];
                long correctParity=0;
                if(diff%(speed[i]+speed[i+1])==0) {
                    correctParity=diff/(speed[i]+speed[i+1]);
                } else {
                    correctParity = diff/(speed[i]+speed[i+1])+1;
                }
                if(i%2==0) {
                    pq.add(new Interval(i,i+1,2*correctParity-1));
                } else {
                    pq.add(new Interval(i, i+1, 2*correctParity));
                }
            }
            while(pq.size()>0) {
                Interval first = pq.poll();
                if(!exists[first.left]||!exists[first.right]) continue;
                if(first.right==n-1) {
                    firstAfter[first.right]=-1;
                } else {
                    firstAfter[first.right]=getNext(first.right+1, firstAfter, exists);
                }
                if(first.left==0) {
                    firstBefore[first.left]=-1;
                } else {
                    firstBefore[first.left]=getPrev(first.left-1, firstBefore, exists);
                }
                firstAfter[first.left]=firstAfter[first.right];
                firstBefore[first.right]=firstBefore[first.left];
                ans[first.left]=first.meetTime;
                ans[first.right]=first.meetTime;
                exists[first.left]=false;
                exists[first.right]=false;
                int bef = firstBefore[first.left], aft = firstAfter[first.right];
                if(bef==-1||aft==-1) continue;
                long diff = pos[aft]-pos[bef];
                long correctParity=0;
                if(diff%(speed[bef]+speed[aft])==0) {
                    correctParity=diff/(speed[bef]+speed[aft]);
                } else {
                    correctParity = diff/(speed[bef]+speed[aft])+1;
                }

                if(bef%2==0) {
                    pq.add(new Interval(bef,aft,2*correctParity-1));
                } else {
                    pq.add(new Interval(bef, aft, 2*correctParity));
                }
            }
            io.ans.append(ans[0]);
            for(int i = 1; i < n; i++) {
                io.ans.append(" "+ans[i]);
            }
            io.ans.append(ENDL);
        }
        io.printAns();
    }
    static int getPrev(int index, int[] firstBefore, boolean[] exists) {
        if(firstBefore[index]==-1) return -1;
        if(exists[firstBefore[index]]) {
            return firstBefore[index];
        }
        firstBefore[index]=getPrev(firstBefore[index], firstBefore, exists);
        return firstBefore[index];
    }
    static int getNext(int index, int[] firstAfter, boolean[] exists) {
        if(firstAfter[index]==-1) return -1;
        if(exists[firstAfter[index]]) {
            return firstAfter[index];
        }
        firstAfter[index]=getNext(firstAfter[index], firstAfter, exists);
        return firstAfter[index];
    }
    static class Interval implements Comparable<Interval> {
        int left, right;
        long meetTime;
        public String toString() {
            return left+" "+right+" "+meetTime;
        }
        public Interval(int left, int right, long meetTime) {
            this.left=left;
            this.right=right;
            this.meetTime=meetTime;
        }
        public int compareTo(Interval other) {
            return Long.compare(meetTime, other.meetTime);
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
