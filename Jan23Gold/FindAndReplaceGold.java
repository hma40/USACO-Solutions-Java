import java.io.*;
import java.util.*;
public class FindAndReplaceGold {
    static long MAX_LENGTH = 1000000000000000005L;
    static InputOutput a;
    static {
        try {
            a = new InputOutput();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws Exception {

        long l = a.nextLong()-1, r = a.nextLong()-1, n = a.nextLong();
        ArrayList<Operation> operations = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            int from = a.next().charAt(0)-'a';
            Operation temp = new Operation(from);
            String to = a.next();
            for(int j = 0; j < to.length(); j++) {
                temp.to.add(to.charAt(j)-'a');
            }
            operations.add(temp);
        }
        Collections.reverse(operations);
       // System.out.println(operations);
        Tree[] trees = new Tree[26];
        for(int i = 0; i < 26; i++) trees[i] = new Tree(i);
//        System.out.println(Arrays.toString(trees));
        for(Operation o: operations) {
            if(o.to.size()==1) {
                trees[o.from]=trees[o.to.get(0)];
            } else {
                ArrayList<Tree> newTree = new ArrayList<>();
                long len = 0;
                for(int i = 0; i < o.to.size(); i++) {
                    newTree.add(trees[o.to.get(i)]);
                    len+=trees[o.to.get(i)].length;
                    if(len>=MAX_LENGTH) {
                        len=MAX_LENGTH;
                        break;
                    }
                }
                trees[o.from]=new Tree(newTree, len);
            }
        }
        trees[0].getSub(l, r);
        a.append('\n');
        a.printAns();
    }
    static class Tree {
        Integer c = null;
        ArrayList<Tree> children;
        long length=1;
        public Tree(Integer c) {
            this.c=c;
        }
        void getSub(long from, long to) {
            //from, to both inclusive
            from=Math.max(from, 0);
            to=Math.min(to, length-1);
            long startLen = 0;
            if(c!=null) {
                a.append((char)('a'+c));
            } else {
                for(Tree t: children) {
                    if(startLen>to) {
                        break;
                    }
                    long finLen = startLen+t.length-1;
                    if(finLen>=from) {
                       t.getSub(from-startLen, to-startLen);
                    }
                    startLen=finLen+1;
                }
            }
        }
        public String toString() {
            if(c!=null) {
                return (char)(c+'a')+" 1";
            }
            StringBuilder ret = new StringBuilder();
            for(Tree t: children) ret.append(t.getPureString());
            return ret.toString()+" "+length+" "+children.size();
        }
        public String getPureString() {
            if(c!=null) {
                return (char)(c+'a')+"";
            }
            StringBuilder ret = new StringBuilder();
            for(Tree t: children) ret.append(t.getPureString());
            return ret.toString();
        }
        public Tree(ArrayList<Tree> children, long length) {
            this.length=length;
            this.children=children;
        }
    }
    static class Operation {
        int from;
        ArrayList<Integer> to = new ArrayList<>();
        public Operation(int from) {
            this.from=from;
        }
        public String toString() {
            return from+" "+to;
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
        public void append(char i) {
            ans.append(i);
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
