import java.io.*;
import java.util.*;
public class Mountains {
    public static void main(String[] args) throws Exception {
        InputOutput a = new InputOutput();
        int n = a.nextInt();
        TreeSet<Integer>[] monotonicSets = new TreeSet[n];
        long[] heights = new long[n];
        for(int i = 0; i < n; i++) {
            heights[i] = a.nextLong();
            monotonicSets[i] = new TreeSet<>();
        }
        for(int i = 0; i < n-1; i++) {
            monotonicSets[i].add(i+1);
            int oldFracDenom = 1;
            long oldFracNum = heights[1+i]-heights[i];
            for(int j = i+2; j < n; j++) {
                int denom = j-i;
                long num = heights[j]-heights[i];
                if(num*oldFracDenom>=denom*oldFracNum) {
                    monotonicSets[i].add(j);
                    oldFracDenom=denom;
                    oldFracNum=num;
                }
            }
        }
        int answer = 0;
        for(int i = 0; i < n; i++) answer+=monotonicSets[i].size();
        int q = a.nextInt();
        for(int i = 0; i < q; i++) {
            int x = a.nextInt()-1;
            long y = a.nextLong();
            heights[x]+=y;
            for(int j = 0; j < x; j++) {
                if(!monotonicSets[j].contains(x)) {
                    int lower = monotonicSets[j].lower(x);
                    int oDen = lower - j;
                    long oNum = heights[lower] - heights[j];
                    int nDen = x-j;
                    long nNum = heights[x]-heights[j];
                    if(nNum*oDen>=oNum*nDen) {
                        monotonicSets[j].add(x);
                        answer++;
                    }
                }
                if(monotonicSets[j].contains(x)) {
                    int oldFracDenom = x-j;
                    long oldFracNum = heights[x]-heights[j];
                    ArrayList<Integer> remove = new ArrayList<>();
                    for(int check: monotonicSets[j].tailSet(x+1)) {
                        int nDen = check-j;
                        long nNum = heights[check]-heights[j];
//                        System.out.println(oldFracDenom+" "+oldFracNum+" "+nDen+" "+nNum+" "+j);
                        if(nNum*oldFracDenom>=oldFracNum*nDen) {
                            break;
                        }
                        remove.add(check);
                        answer--;
                    }
                    monotonicSets[j].removeAll(remove);
                }
            }
            if(x!=n-1) {
                answer -= monotonicSets[x].size();
                monotonicSets[x] = new TreeSet<>();
                monotonicSets[x].add(x + 1);
                answer++;
                int oldFracDenom = 1;
                long oldFracNum = heights[1 + x] - heights[x];
                for (int j = x + 2; j < n; j++) {
                    int denom = j - x;
                    long num = heights[j] - heights[x];
                    if (num * oldFracDenom >= denom * oldFracNum) {
                        monotonicSets[x].add(j);
                        answer++;
                        oldFracDenom = denom;
                        oldFracNum = num;
                    }
                }
            }
            a.append(answer);
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
