import java.io.*;
import java.util.*;
//Does not pass 2 test cases
public class PaintingPosts {
    static final String ENDL = "\n";
    static long[] invComp;
    public static void main(String[] args) throws Exception {
        InputOutput io = new InputOutput("15");
        int n = io.nextInt();
        int p = io.nextInt();
        ArrayList<Long> all = new ArrayList<>();
        HashMap<Long, Integer> compress = new HashMap<>();
        ArrayList<Point> allPoints = new ArrayList<>();
        for(int i = 0; i < p; i++) {
            Point temp = new Point(io.nextLong(), io.nextLong(), i);
            allPoints.add(temp);
            all.add(temp.x);
            all.add(temp.y);
        }
        ArrayList<TwoPoints> cows=  new ArrayList<>();
        for(int i = 0; i < n; i++) {
            TwoPoints temp = new TwoPoints(io.nextLong(), io.nextLong(), io.nextLong(), io.nextLong());
            all.add(temp.a.x);
            all.add(temp.b.x);
            all.add(temp.a.y);
            all.add(temp.b.y);
            cows.add(temp);
        }
        Collections.sort(all);
        int nextNum = 0;
        for(long i: all) {
            if(!compress.containsKey(i)) {
                compress.put(i, nextNum++);
            }
        }
        invComp = new long[nextNum];
        for(long i: all) {
            invComp[compress.get(i)]=i;
        }
        for(Point q: allPoints) {
            q.x=compress.get(q.x);
            q.y=compress.get(q.y);
        }
        for(TwoPoints tp: cows) {
            tp.a.x=compress.get(tp.a.x);
            tp.a.y=compress.get(tp.a.y);
            tp.b.x=compress.get(tp.b.x);
            tp.b.y=compress.get(tp.b.y);
        }
        //System.out.println(allPoints);
        //System.out.println(cows);
        TreeSet<Point>[] pX = new TreeSet[nextNum];
        TreeSet<Point>[] pY = new TreeSet[nextNum];
        for(int i = 0; i < nextNum; i++) {
            pX[i] = new TreeSet<>();
            pY[i] = new TreeSet<>();
        }
        for(Point q: allPoints) {
            pX[(int)q.x].add(q);
            pY[(int)q.y].add(q);
        }
        //look for TOP LEFT fence
        ArrayList<Point> inOrder = new ArrayList<>();
        for(int i = 0; i < nextNum; i++) {
            Point prev = null;
            for(Point j: pX[i]) {
                if(prev==null) prev=j;
                else {
                    prev.changeY=j;
                    j.changeY=prev;
                    prev=null;
                }
            }
            for(Point j: pY[i]) {
                if(prev==null) prev=j;
                else {
                    prev.changeX=j;
                    j.changeX=prev;
                    prev=null;
                }
            }
        }

        inOrder.add(allPoints.get(0));
        allPoints.get(0).index=0;
        boolean changeY = false;
        for(int i = 1; i < p; i++) {
            Point next = null;
            if(changeY) {
                next=inOrder.get(inOrder.size()-1).changeY;
            } else {
                next=(inOrder.get(inOrder.size()-1).changeX);
            }
            next.index=inOrder.size();
            changeY=!changeY;
            inOrder.add(next);
        }
        //System.out.println(inOrder);
        long[] prefSum = new long[p+1];
        for(int i = 1; i < p; i++) {
            long dist = Math.abs(invComp[(int)inOrder.get(i).x]-invComp[(int)inOrder.get(i-1).x])+Math.abs(invComp[(int)inOrder.get(i).y]-invComp[(int)inOrder.get(i-1).y]);
            prefSum[i]=prefSum[i-1]+dist;
        }
        long dist = Math.abs(invComp[(int)inOrder.get(0).x]-invComp[(int)inOrder.get(p-1).x])+Math.abs(invComp[(int)inOrder.get(0).y]-invComp[(int)inOrder.get(p-1).y]);
        prefSum[p]=dist+prefSum[p-1];
        //System.out.println(inOrder);
        //System.out.println(Arrays.toString(prefSum));
        int[] forwardPref = new int[2*p+1];
        int[] backwardPref = new int[p+1];
        for(TwoPoints tp: cows) {
            long oneDir = 0;
            Point temp0 = pX[(int)tp.a.x].floor(tp.a);
            Point temp1 = pX[(int)tp.a.x].ceiling(tp.a);
            if(temp0!=tp.a&&temp1!=tp.a) {
                if(temp0==null||temp1==null) {
                    temp0 = pY[(int)tp.a.y].floor(tp.a);
                    temp1 = pY[(int)tp.a.y].ceiling(tp.a);
                } else if(Math.abs(temp0.index-temp1.index)!=1&&Math.abs(temp0.index-temp1.index)!=p-1) {
                    temp0 = pY[(int)tp.a.y].floor(tp.a);
                    temp1 = pY[(int)tp.a.y].ceiling(tp.a);
                }
            }

            Point temp2 = pX[(int)tp.b.x].floor(tp.b);
            Point temp3 = pX[(int)tp.b.x].ceiling(tp.b);
            if(temp2!=tp.b&&temp3!=tp.b) {
                if(temp2==null||temp3==null) {
                    temp2 = pY[(int)tp.b.y].floor(tp.b);
                    temp3 = pY[(int)tp.b.y].ceiling(tp.b);
                } else if(Math.abs(temp2.index-temp3.index)!=1&&Math.abs(temp2.index-temp3.index)!=p-1) {
                    temp2 = pY[(int)tp.b.y].floor(tp.b);
                    temp3 = pY[(int)tp.b.y].ceiling(tp.b);
                }
            }
            if(temp0!=temp1) {
                if(temp1.index-temp0.index!=1) {
                    Point tmp = temp0;
                    temp0=temp1;
                    temp1=tmp;
                }
            }
            if(temp2!=temp3) {
                if(temp3.index-temp2.index!=1) {
                    Point tmp = temp2;
                    temp2=temp3;
                    temp3=tmp;
                }
            }
            if(temp1==temp3) {
                oneDir = pointDist(tp.a,tp.b);
            } else if(temp1.index<temp3.index) {
                oneDir=pointDist(tp.a, temp1)+prefSum[temp2.index]-prefSum[temp1.index]+pointDist(temp2,tp.b);
            } else {
                oneDir=pointDist(tp.b, temp3)+prefSum[temp0.index]-prefSum[temp3.index]+pointDist(temp0, tp.a);
            }
            //System.out.println(temp0.index+" "+temp1.index+" "+temp2.index+" "+temp3.index+" "+tp);
            //System.out.println(oneDir+" "+prefSum[p]);
            if(oneDir>prefSum[p]-oneDir) {
               // System.out.println("line 156");
                if(temp1.index>temp3.index) {
                    forwardPref[temp1.index]++;
                    if(temp3.index==temp2.index) {
                        forwardPref[temp3.index+p+1]--;
                    } else {
                        forwardPref[temp3.index+p]--;
                    }
                } else if(temp1.index<temp3.index){
                    forwardPref[temp3.index]++;
                    if(temp1.index==temp0.index) {
                        forwardPref[temp1.index+p+1]--;
                    } else {
                        forwardPref[temp1.index+p]--;
                    }
                }
            } else {
                //System.out.println("line 165");
                if(temp1.index>temp3.index) {
                    forwardPref[temp3.index]++;
                    if(temp0==temp1) {
                        forwardPref[temp1.index+1]--;
                    } else
                    forwardPref[temp1.index]--;
                } else if(temp3.index>temp1.index) {
                    forwardPref[temp1.index]++;
                    if(temp2==temp3) {
                        forwardPref[temp3.index+1]--;
                    } else
                    forwardPref[temp3.index]--;
                }
            }
        }
//        System.out.println(inOrder);
//        System.out.println(Arrays.toString(forwardPref));
        int[] ans = new int[p];
        int s = 0;
        for(int i = 0; i < 2*p+1; i++) {
            s+=forwardPref[i];
            ans[inOrder.get(i%p).origI]+=s;
        }
        for(int i = 0; i < p; i++) io.append(ans[i]);
        io.printAns();
    }
    static long pointDist(Point a, Point b) {
        return Math.abs(invComp[(int)a.x]-invComp[(int)b.x])+Math.abs(invComp[(int)a.y]-invComp[(int)b.y]);
    }
    static class TwoPoints {
        Point a,b;
        public TwoPoints(long w, long x, long y, long z) {
            a = new Point(w,x,-1);
            b = new Point(y,z, -1);
        }
        public String toString() {
            return a+" "+b;
        }
    }
    static class Point implements Comparable<Point>{
        long x, y;
        int index;
        int origI;
        Point changeX, changeY;
        Point(long x, long y, int origI) {
            this.x=x;
            this.y=y;
            this.origI=origI;
        }
        public String toString() {
            return "["+x+", "+y+"]";
        }
        public int compareTo(Point other) {
            if(this.x==other.x) return Long.compare(y, other.y);
            return Long.compare(x, other.x);
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
