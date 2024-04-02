
import java.io.*;
import java.util.*;
/*
1
15
2 1
3 2
4 2
5 4
6 5
7 6
8 5
9 4
10 4
11 9
12 3
13 4
14 1
15 2
10
7 8
8 13
9 15
10 15
11 9
12 15
13 15
14 1
15 14
 */
public class TreeMerging {
    public static void main(String[] args) throws Exception {
        InputOutput a = new InputOutput();
        for(int t = a.nextInt(); t > 0; t--) {
            int n = a.nextInt();
            ArrayList<Integer>[] children = new ArrayList[n];
            ArrayList<Integer>[] fChildren = new ArrayList[n];
            int[] fPar = new int[n];
            int[] depth = new int[n];
            Arrays.fill(fPar, -1);
            for(int i = 0; i < n; i++) {
                children[i] = new ArrayList<>();
                fChildren[i] = new ArrayList<>();
            }
            int[] par = new int[n];
            Arrays.fill(par, -1);
            int root = 0;
            for(int i = 0; i < n-1; i++) {
                int child = a.nextInt()-1, parent = a.nextInt()-1;
                par[child]=parent;
                children[parent].add(child);
            }
            for(int i = 0; i < n; i++) {
                if(par[i]==-1) root=i;
            }
            int m = a.nextInt();
            boolean[] extant = new boolean[n];
            for(int i = 0; i < m-1; i++) {
                int child = a.nextInt()-1, parent = a.nextInt()-1;
                extant[child]=true;
                extant[parent]=true;
                fPar[child]=parent;
                fChildren[parent].add(child);
            }
            boolean[][] canMerge = new boolean[n][n];
            getDepth(children, root, depth);
            ArrayList<Integer>[] sameDepth = new ArrayList[n];

            for(int i = 0; i < n; i++) sameDepth[i] = new ArrayList<>();
            for(int i = 0; i < n; i++) {
                sameDepth[depth[i]].add(i);
            }
            for(int i = n-1; i >= 0; i--) {
                for(int node: sameDepth[i]) {
                    for (int x : sameDepth[i]) {
                        if (extant[node]) {
                            break;
                        }
                        if (extant[x]) {
                            if (node < x) {
                                boolean works = true;
                                System.err.println(node+" "+x);
                                for (int child : children[node]) {
                                    boolean works2 = false;
                                    if(extant[child]) {
                                        if(fPar[child]==x) {
                                            works2=true;
                                        }
                                    } else {
                                        for (int fChild : fChildren[x]) {
                                            if (canMerge[child][fChild]) works2 = true;
                                        }
                                    }
                                    if (!works2) {
                                        works = false;
                                    }
                                }
                                if (works) canMerge[node][x] = true;
                            }
                        }
                    }
                }
            }
            for(int i = 0; i < n; i++) {
                for(int j = 0; j < n; j++) {
                    if(canMerge[i][j]) System.err.println(i+" "+j);
                }
            }
            int[] mergedW = new int[n];
            boolean[] exists = new boolean[n];
            Arrays.fill(mergedW, -1);
            a.append(n-m);
            for(int i = 0; i < n; i++) Collections.sort(sameDepth[i]);
            for(int i = 0; i < n; i++) {
                for(int j: sameDepth[i]) {
                    if(extant[j]) continue;
                    for(int k: children[par[j]]) {
                        if(canMerge[j][k]) {
                            a.append((1+j)+" "+(1+k));
                            for(int x: children[j]) {
                                children[k].add(x);
                                par[x]=k;
                            }
                            children[j].clear();
                            break;
                        }
                    }
                }
            }
        }
        a.printAns();
    }
    static void getDepth(ArrayList<Integer>[] children, int node, int[] depth) {
        for(int child: children[node]) {
            depth[child]=depth[node]+1;
            getDepth(children, child, depth);
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
