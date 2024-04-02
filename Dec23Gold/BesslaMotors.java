import java.io.*;
import java.util.*;
public class BesslaMotors {
    static final String ENDL = "\n";
    public static void main(String[] args) throws Exception {
        InputOutput io = new InputOutput();
        int n = io.nextInt();
        int m = io.nextInt();
        int c = io.nextInt();
        int r = io.nextInt();
        int k = io.nextInt();
        ArrayList<Path>[] adj = new ArrayList[n];
        for(int i = 0; i < n; i++) {
            adj[i]=new ArrayList<>();
        }
        TreeSet<Integer> wellConnected = new TreeSet<>();
        for(int i = 0; i < m; i++) {
            int u = io.nextInt()-1, v = io.nextInt()-1;
            long l = io.nextLong();
            adj[u].add(new Path(v, l));
            adj[v].add(new Path(u,l));
        }
        int[] timesVis = new int[n];
        PriorityQueue<Path> pq = new PriorityQueue<>();
        for(int i = 0; i < c; i++) {
            timesVis[i]=1;
            pq.add(new Path(i, 0, i));
        }
        HashSet<Integer>[] vis = new HashSet[n];
        for(int i = 0; i < n; i++) {
            vis[i] = new HashSet<>();
        }
        while(pq.size()>0) {
            Path f = pq.poll();
            if(vis[f.destination].size()==k) continue;
            if(vis[f.destination].contains(f.origin)) continue;
            vis[f.destination].add(f.origin);
            timesVis[f.destination]++;
            for(Path p: adj[f.destination]) {
                if(f.dist+p.dist>r) continue;
                pq.add(new Path(p.destination, f.dist+p.dist, f.origin));
            }
        }
//        for(int i = 0; i < c; i++) {
//            System.out.println(i+" "+dist(i, 189, adj));
//        }
//        System.out.println(vis[189]);
//        System.out.println(vis[3]);
        for(int i = c; i < n; i++) {
            if(timesVis[i]==k) {
                wellConnected.add(i);
            }
        }
        io.append(wellConnected.size());
        for(int i: wellConnected) io.append(i+1);
        io.printAns();
    }
    static ArrayList<Long> dist(int from, int to, ArrayList<Path>[] adj) {
        int n = adj.length;
        boolean[] vis = new boolean[n];
        PriorityQueue<Path> pq = new PriorityQueue<>();
        pq.add(new Path(from,0, from));
        long[] dist = new long[n];
        ArrayList<Long>[] path = new ArrayList[n];
        path[from]=new ArrayList<>();
        Arrays.fill(dist, Long.MAX_VALUE);
        dist[from]=0;
        while(pq.size()>0) {
            Path p = pq.poll();
            if(vis[p.destination]) continue;
            vis[p.destination]=true;
            path[p.destination] = new ArrayList<>(path[p.origin]);
            path[p.destination].add((long) p.destination);
            for(Path q: adj[p.destination]) {
                if(vis[q.destination]) continue;
                if(dist[q.destination]<=p.dist+q.dist) continue;
                dist[q.destination]=p.dist+q.dist;
                pq.add(new Path(q.destination, p.dist+q.dist, p.destination));
            }
        }
        path[to].add(dist[to]);
        return path[to];
    }
    static class Path implements Comparable<Path>{
        int destination;
        long dist;
        int origin;
        public Path(int destination, long dist, int origin) {
            this(destination,dist);
            this.origin=origin;
        }
        public String toString() {
            return destination+" "+dist+" "+origin;
        }
        public int compareTo(Path other) {
            return Long.compare(dist, other.dist);
        }
        public Path(int destination, long dist) {
            this.destination=destination;
            this.dist=dist;
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
