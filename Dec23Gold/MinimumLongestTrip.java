import java.io.*;
import java.util.*;
public class MinimumLongestTrip {
    static int[] nextNode;
    static long[] firstMove;
    static int[] travSize;
    public static void main(String[] args) throws Exception {
        long begintime = System.currentTimeMillis();
        InputOutput a = new InputOutput();
        int n = a.nextInt(), m = a.nextInt();
        ArrayList<PI>[] invadj = new ArrayList[n];
        ArrayList<PI>[] adj= new ArrayList[n];
        for(int i = 0; i < n; i++) {
            invadj[i] = new ArrayList<>();
            adj[i] = new ArrayList<>();
        }
        int[] indeg = new int[n];
        int maxTrav = 0;
        for(int i = 0; i < m; i++) {
            int start = a.nextInt()-1, end = a.nextInt()-1, size = a.nextInt();
            invadj[end].add(new PI(start, size));
            indeg[start]++;
            adj[start].add(new PI(end, size));
        }
        int[] rank = new int[n];
        travSize = new int[n];
        ArrayDeque<PIL> q = new ArrayDeque<>();
        ArrayList<Integer> topo = new ArrayList<>();
        firstMove = new long[n];
        nextNode = new int[n];
        Arrays.fill(nextNode, -1);
        for(int i = 0; i < n; i++) {
            if(indeg[i]==0) q.addLast(new PIL(i, i));
        }
        while(q.size()>0) {
            PIL x = q.removeFirst();
            if(x.a!=x.b) {
                travSize[x.b]=travSize[x.a]+1;
            }
            maxTrav=Math.max(maxTrav, travSize[x.b]);
            topo.add(x.b);
            for(PI y: invadj[x.b]) {
                if(--indeg[y.a]==0) {
                    q.addLast(new PIL(x.b, y.a));
                }
            }
        }
        ArrayList<Integer>[] lexico = new ArrayList[maxTrav+1];
        for(int i = 0; i < maxTrav+1; i++) {
            lexico[i] = new ArrayList<>();
        }
        long[] ans = new long[n];
        int prev = 0;
        for(int i = 0; i < n; i++) {
            lexico[travSize[i]].add(i);
        }
        for(int i = 1; i < maxTrav+1; i++) {
            for(int x: lexico[i]) {
                ArrayList<Integer> potential = new ArrayList<>();
                long best = Long.MAX_VALUE;
                for(PI ad: adj[x]) {
                    if(travSize[ad.a]==travSize[x]-1) {
                        if (ad.b < best) {
                            potential.clear();
                            potential.add(ad.a);
                            best = ad.b;
                        } else if (ad.b == best) {
                            potential.add(ad.a);
                        }
                    }
                }
                firstMove[x]=best;
                Collections.sort(potential, Comparator.comparingInt(p -> rank[p]));
                nextNode[x]=potential.get(0);
                ans[x]=ans[nextNode[x]]+best;
            }
            Collections.sort(lexico[i], (x, y)->{
                if(firstMove[x]==firstMove[y]) {
                    return Integer.compare(rank[nextNode[x]], rank[nextNode[y]]);
                }
                return Long.compare(firstMove[x], firstMove[y]);
            });
            for(int j = 0; j < lexico[i].size(); j++) {
                rank[lexico[i].get(j)]=j;
            }
        }
        for(int i = 0; i < n; i++) {
            a.append(travSize[i]+" "+ans[i]);
        }
        a.printAns();
    }
    static class PI {
        int a;
        long b;
        public PI(int a, long b) {
            this.a=a;
            this.b=b;
        }
        @Override
        public boolean equals(Object other) {
            if(other==null||getClass()!=other.getClass()) {
                return false;
            }
            PI obj = (PI)other;
            return a==obj.a&&b==obj.b;
        }
        public int hashCode() {
            long aa = a;
            return (int)((aa*100003)^b)%1000000007;
        }
    }
    static class PIL {
        int a;
        int b;
        public PIL(int a, int b) {
            this.a=a;
            this.b=b;
        }
        public String toString() {
            return a+" "+b;
        }
        @Override
        public boolean equals(Object other) {
            if(other==null||getClass()!=other.getClass()) {
                return false;
            }
            PIL obj = (PIL)other;
            return a==obj.a&&b==obj.b;
        }
        public int hashCode() {
            return a^b;
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
