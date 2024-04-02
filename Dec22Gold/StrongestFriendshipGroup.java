import java.io.*;
import java.util.*;
public class StrongestFriendshipGroup {
    public static void main(String[] args) throws Exception {
        InputOutput a = new InputOutput();
        int n = a.nextInt(), m = a.nextInt();
        HashSet<Integer>[] friend = new HashSet[n];
        for(int i = 0; i < n; i++) friend[i] = new HashSet<>();
        for(int i = 0; i < m; i++) {
            int x = a.nextInt()-1, y = a.nextInt()-1;
            friend[x].add(y);
            friend[y].add(x);
        }
        ArrayList<Integer> removeOrder = new ArrayList<>();
        TreeSet<Pair> set = new TreeSet<>();
        int[] neighbors = new int[n];
        boolean[] removed = new boolean[n];
        for(int i = 0; i < n; i++) {
            neighbors[i]=friend[i].size();
            set.add(new Pair(friend[i].size(), i));
        }
        ArrayList<Integer> degrees = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            Pair first = set.first();
            set.remove(set.first());
            removed[first.index]=true;
            removeOrder.add(first.index);
            degrees.add(first.numEdges);
            for(int j: friend[first.index]) {
                if(removed[j]) continue;
                set.remove(new Pair(neighbors[j], j));
                neighbors[j]--;
                set.add(new Pair(neighbors[j], j));
            }
            //System.out.println(i+" "+set);
        }
        boolean[] active = new boolean[n];
        DisjointSets ds = new DisjointSets(n);
        //System.out.println(degrees);
        long ans = 0;
        //System.out.println(removeOrder);
        for(int i = n-1; i >= 0; i--) {
            active[removeOrder.get(i)]=true;
            for(int j: friend[removeOrder.get(i)]) {
                if(active[j]) ds.connect(removeOrder.get(i), j);
            }
            ans = Math.max(ans, (long)degrees.get(i)*ds.maxSize);
        }
        a.append(ans);
        a.printAns();
    }
    static class DisjointSets {
        int[] parents, sizes;
        int maxSize = 1;
        public DisjointSets (int size) {
            parents = new int[size];
            sizes = new int[size];
            Arrays.fill(parents,-1);
            Arrays.fill(sizes, 1);
        }
        public int find(int x) {
            if(parents[x]==-1) {
                return x;
            }
            parents[x]=find(parents[x]);
            return parents[x];
        }
        public boolean connect(int x, int y) {
            int rootX = find(x), rootY = find(y);
            if(rootX==rootY) return false;
            if(sizes[rootX]<sizes[rootY]) {
                parents[rootX]=rootY;
                sizes[rootY]+=sizes[rootX];
                maxSize=Math.max(maxSize,  sizes[rootY]);
            } else {
                parents[rootY]=rootX;
                sizes[rootX]+=sizes[rootY];
                maxSize=Math.max(maxSize, sizes[rootX]);
            }
            return true;
        }
    }
    static class Pair implements Comparable<Pair>{
        int numEdges,index;
        public Pair(int numEdges, int index) {
            this.numEdges=numEdges;
            this.index=index;
        }
        public int compareTo(Pair other) {
            if(numEdges==other.numEdges) return index-other.index;
            return numEdges-other.numEdges;
        }
        public String toString() {
            return numEdges+" "+index;
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
