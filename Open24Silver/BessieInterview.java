import java.io.*;
import java.util.*;
/*
8 3
6 2 7 4 1 3 4 9
 */
public class BessieInterview {
    static final String ENDL = "\n";
    public static void main(String[] args) throws Exception {
        InputOutput io = new InputOutput();
        int n = io.nextInt(), k = io.nextInt();
        PriorityQueue<Farmer> pq = new PriorityQueue<>();
        long[] t = new long[n];
        for(int i = 0; i < n; i++) {
            t[i]=io.nextLong();
        }
        for(int i = 0; i < k; i++) {
            pq.add(new Farmer(i, t[i]));
        }
        int i = k;
        boolean[] ans = new boolean[n];
        ArrayList<ArrayList<Integer>> events = new ArrayList<>();
        Farmer last = null;
        while(i<n) {
            ArrayList<Integer> addBack = new ArrayList<>();
            Farmer first = pq.poll();
            addBack.add(first.index);
            while(pq.size()>0&&pq.peek().time==first.time) {
                Farmer temp = pq.poll();
                addBack.add(temp.index);
            }
            if(addBack.size()>1) {
                events.add(addBack);
            }
            for(int j: addBack) {
                pq.add(new Farmer(j, first.time+t[i]));
                i++;
                if(i==n) {
                    break;
                }
            }
            if(i==n) {
                if(pq.size()==k) {
//                    System.out.println("got here");
                    first = pq.poll();
                    while(pq.size()>0&&pq.peek().time==first.time) {
                        Farmer temp = pq.poll();
                    }
                }
                ans[first.index]=true;
                io.append(first.time);
                for(int j = events.size()-1; j>=0; j--) {
                    boolean infect = false;
                    for(int x: events.get(j)) {
                        if(ans[x]) {
                            infect=true;
                        }
                    }
                    if(infect) {
                        for(int x: events.get(j)) {
                            ans[x]=true;
                        }
                    }
                }
            }
        }
        //System.out.println(Arrays.toString(ds.par));
        for(int j = 0; j < k; j++) {
            if(ans[j]) {
                io.ans.append("1");
            } else {
                io.ans.append("0");
            }
        }
        io.printAns();
    }
    static class Farmer implements Comparable<Farmer> {
        int index;
        long time;
        public Farmer(int i, long t) {
            index=i;
            time=t;
        }
        public int compareTo(Farmer other) {
            return Long.compare(time, other.time);
        }
        public String toString() {
            return index+" "+time;
        }
    }
    static class DisjointSets {
        int n;
        int[] par;
        int[] size;
        public DisjointSets(int n) {
            this.n=n;
            this.par=new int[n];
            this.size = new int[n];
            Arrays.fill(par, -1);
            Arrays.fill(size, 1);
        }
        public int find(int x) {
            if(par[x]==-1) {
                return x;
            }
            return par[x]=find(par[x]);
        }
        public void unite(int x, int y) {
            x=find(x);
            y=find(y);
            if(x==y) return;
            if(size[x]>size[y]) {
                unite(y,x);
                return;
            }
            par[x]=y;
            size[y]+=size[x];
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
