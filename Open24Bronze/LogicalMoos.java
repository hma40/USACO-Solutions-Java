import java.io.*;
import java.util.*;
public class LogicalMoos {
    static final String ENDL = "\n";
    public static void main(String[] args) throws Exception {
        InputOutput io = new InputOutput();
        int n = io.nextInt();
        int q = io.nextInt();
        boolean[] start = new boolean[(n+1)/2];
        ArrayList<Integer> islands = new ArrayList<>();
        islands.add(0);
        int[] islandNum = new int[(n+1)/2];
        int curIsl = 0;
        for(int i = 0; i < n; i++) {
            String s = io.next();
            if(i%2==0) {
                islandNum[i/2]=curIsl;
                start[i/2] = s.equals("true");
            } else {
                if(s.equals("or")) {
                    curIsl++;
                    islands.add((i+1)/2);
                }
            }
        }
        //System.out.println(islands);
        //System.out.println(Arrays.toString(start));
        //System.out.println(Arrays.toString(islandNum));
        int[] truePref = new int[islands.size()];
        int[] trueSuff = new int[islands.size()];
        Arrays.fill(truePref, -1);
        Arrays.fill(trueSuff, -1);
        for(int i = 0; i < (n+1)/2; i++) {
            if(truePref[islandNum[i]]==-1) {
                if(!start[i]) {
                    truePref[islandNum[i]]=i;
                }
            }
        }
        for(int i = n/2; i >= 0; i--) {
            if(trueSuff[islandNum[i]]==-1) {
                if(!start[i]) {
                    trueSuff[islandNum[i]]=i;
                }
            }
        }
        int first = -1, last = -1;
        for(int i = 0; i < islands.size(); i++) {
            if(truePref[i]==-1) {
                if(first==-1) first=i;
                last=i;
            }
        }
        //System.out.println(first+" "+last);
        for(int i = 0; i < q; i++) {
            int l = io.nextInt()/2;
            int r = io.nextInt()/2;
            boolean target = io.next().equals("true");
            if(first!=-1) {
                boolean fail = false;
                if(islandNum[r]<last) {
                    fail=true;
                }
                if(islandNum[l]>first) {
                    fail=true;
                }
                //System.out.println(r+" "+islandNum[r]+" "+last);
                //System.out.println(l+" "+islandNum[l]+" "+first);
                if(fail) {
                    //System.out.println("line 68");
                    if (target) {
                        io.ans.append("Y");
                    } else {
                        io.ans.append("N");
                    }
                    continue;
                }
            }
            if(!target) {
                //System.out.println("line 78");
                io.ans.append("Y");
                continue;
            }
            //20, 29
            //System.out.println(l+" "+islandNum[l]+" "+truePref[islandNum[l]]);
            //System.out.println(r+" "+islandNum[r]+" "+trueSuff[islandNum[r]]);
            if((l<=truePref[islandNum[l]]||truePref[islandNum[l]]==-1)&&(r>=trueSuff[islandNum[r]]||trueSuff[islandNum[r]]==-1)) {
                //System.out.println("line 83");
                io.ans.append("Y");
            } else {
                //System.out.println("line 86");
                io.ans.append("N");
            }
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
