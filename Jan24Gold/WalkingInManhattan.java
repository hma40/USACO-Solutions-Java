import java.io.*;
import java.util.*;
public class WalkingInManhattan {
    static InputOutput a;
    static int[][] hEvenSparseTable, hOddSparseTable, vOddSparseTable, vEvenSparseTable;
    static ArrayList<Long> hOdd = new ArrayList<>(), hEven = new ArrayList<>(), vOdd = new ArrayList<>(), vEven = new ArrayList<>();
    static TreeSet<Long> horiz = new TreeSet<>(), vert = new TreeSet<>();
    public static void main(String[] args) throws Exception {
        a = new InputOutput();
        int n = a.nextInt();
        int q = a.nextInt();
        for(int i = 0; i < n; i++) {
            char dir = a.next().toCharArray()[0];
            Long coord = a.nextLong();
            if(dir=='V') {
                if(coord%2==0) {
                    vEven.add(coord);
                } else {
                    vOdd.add(coord);
                }
                vert.add(coord);
            } else {
                if(coord%2==0) {
                    hEven.add(coord);
                } else {
                    hOdd.add(coord);
                }
                horiz.add(coord);
            }
        }
        hOdd.add((1L<<50)-1);
        hEven.add((1L<<50)-2);
        vOdd.add((1L<<50)-1);
        vEven.add((1L<<50)-2);
        horiz.add((1L)<<50);
        vert.add((1L)<<50);
        Collections.sort(hOdd);
        Collections.sort(hEven);
        Collections.sort(vOdd);
        Collections.sort(vEven);
        hOddSparseTable = new int[20][hOdd.size()];
        hEvenSparseTable = new int[20][hEven.size()];
        vOddSparseTable = new int[20][vOdd.size()];
        vEvenSparseTable = new int[20][vEven.size()];
        for(int[] x: hOddSparseTable) Arrays.fill(x, -1);
        for(int[] x: hEvenSparseTable) Arrays.fill(x, -1);
        for(int[] x: vOddSparseTable) Arrays.fill(x, -1);
        for(int[] x: vEvenSparseTable) Arrays.fill(x, -1);
        for(int i = 0; i < hOdd.size(); i++) {
            int bs = Collections.binarySearch(hEven, hOdd.get(i));
            bs=-bs-1;
            if(bs==hEven.size()) bs=-1;
            hOddSparseTable[0][i]=bs;
        }
        for(int i = 0; i < hEven.size(); i++) {
            int bs = Collections.binarySearch(hOdd, hEven.get(i));
            bs=-bs-1;
            if(bs==hOdd.size()) bs=-1;
            hEvenSparseTable[0][i]=bs;
        }
        for(int i = 0; i < vOdd.size(); i++) {
            int bs = Collections.binarySearch(vEven, vOdd.get(i));
            bs=-bs-1;
            if(bs==vEven.size()) bs=-1;
            vOddSparseTable[0][i]=bs;
        }
        for(int i = 0; i < vEven.size(); i++) {
            int bs = Collections.binarySearch(vOdd, vEven.get(i));
            bs=-bs-1;
            if(bs==vOdd.size()) bs=-1;
            vEvenSparseTable[0][i]=bs;
        }
        for(int j = 0; j < vOdd.size(); j++) {
            if(vOddSparseTable[0][j]==-1) {
                vOddSparseTable[1][j]=-1;
            } else {
                vOddSparseTable[1][j]=vEvenSparseTable[0][vOddSparseTable[0][j]];
            }
        }
        for(int j = 0; j < hOdd.size(); j++) {
            if(hOddSparseTable[0][j]==-1) {
                hOddSparseTable[1][j]=-1;
            } else {
                hOddSparseTable[1][j]=hEvenSparseTable[0][hOddSparseTable[0][j]];
            }
        }
        for(int j = 0; j < vEven.size(); j++) {
            if(vEvenSparseTable[0][j]==-1) {
                vEvenSparseTable[1][j]=-1;
            } else {
                vEvenSparseTable[1][j]=vOddSparseTable[0][vEvenSparseTable[0][j]];
            }
        }
        for(int j = 0; j < hEven.size(); j++) {
            if(hEvenSparseTable[0][j]==-1) {
                hEvenSparseTable[1][j]=-1;
            } else {
                hEvenSparseTable[1][j]=hOddSparseTable[0][hEvenSparseTable[0][j]];
            }
        }
        for(int i = 2; i < 20; i++) {
            for(int j = 0; j < vOdd.size(); j++) {
                if(vOddSparseTable[i-1][j]==-1) {
                    vOddSparseTable[i][j]=-1;
                } else {
                    vOddSparseTable[i][j]=vOddSparseTable[i-1][vOddSparseTable[i-1][j]];
                }
            }
            for(int j = 0; j < hOdd.size(); j++) {
                if(hOddSparseTable[i-1][j]==-1) {
                    hOddSparseTable[i][j]=-1;
                } else {
                    hOddSparseTable[i][j]=hOddSparseTable[i-1][hOddSparseTable[i-1][j]];
                }
            }
            for(int j = 0; j < vEven.size(); j++) {
                if(vEvenSparseTable[i-1][j]==-1) {
                    vEvenSparseTable[i][j]=-1;
                } else {
                    vEvenSparseTable[i][j]=vEvenSparseTable[i-1][vEvenSparseTable[i-1][j]];
                }
            }
            for(int j = 0; j < hEven.size(); j++) {
                if(hEvenSparseTable[i-1][j]==-1) {
                    hEvenSparseTable[i][j]=-1;
                } else {
                    hEvenSparseTable[i][j]=hEvenSparseTable[i-1][hEvenSparseTable[i-1][j]];
                }
            }
        }
//        System.out.println("vEven: "+vEven);
//        System.out.println("vOdd: "+vOdd);
//        System.out.println("hEven: "+hEven);
//        System.out.println("hOdd: "+hOdd);
//        for(int i = 0; i < 20; i++) {
//            System.out.println(Arrays.toString(vOddSparseTable[i]));
//        }
//        System.out.println(Arrays.toString(hOddSparseTable[2]));
//        System.out.println(Arrays.toString(hEvenSparseTable[2]));
//        System.out.println(Arrays.toString(vOddSparseTable[2]));
//        System.out.println(Arrays.toString(vEvenSparseTable[2]));
//        System.out.println(horiz+" "+vert);
        for(int i = 0; i < q; i++) {
            Coord cow = new Coord(a.nextLong(), a.nextLong(), 0);
            Coord fin = getLoc(cow, a.nextLong());
            a.append(fin.x+" "+fin.y);
        }
        a.printAns();
    }
    static Coord getLoc(Coord start, long finalTime) {
        //System.out.println(start+" "+finalTime);
        if(horiz.contains(start.y)) {
            if(vert.contains(start.x)) {
                //System.out.println("scenario 1");
                if(start.x%2==0) {
                    start.xIndex=Collections.binarySearch(vEven, start.x);
                } else {
                    start.xIndex=Collections.binarySearch(vOdd, start.x);
                }
                if(start.y%2==0) {
                    start.yIndex=Collections.binarySearch(hEven, start.y);
                } else {
                    start.yIndex=Collections.binarySearch(hOdd, start.y);
                }
                return getLoc2(start, finalTime);
            } else {
                //System.out.println("scenario 2");
                long nextIntersect = vert.higher(start.x);
                if(nextIntersect-start.x>=finalTime) {
                    long actual = start.x+finalTime;
                    return new Coord(actual, start.y, 0);
                }
                start.t=nextIntersect-start.x;
                start.x=nextIntersect;
                if(start.x%2==0) {
                    start.xIndex=Collections.binarySearch(vEven, start.x);
                } else {
                    start.xIndex=Collections.binarySearch(vOdd, start.x);
                }
                if(start.y%2==0) {
                    start.yIndex=Collections.binarySearch(hEven, start.y);
                } else {
                    start.yIndex=Collections.binarySearch(hOdd, start.y);
                }
                //System.out.println(start+" "+finalTime);
                return getLoc2(start, finalTime);
            }
        }
        //System.out.println("scenario 3");
        long nextIntersect = horiz.higher(start.y);
        if(nextIntersect-start.y>=finalTime) {
            long actual = start.y+finalTime;
            return new Coord(start.x, actual, 0);
        }
        start.t=nextIntersect-start.y;
        start.y=nextIntersect;
        if(start.x%2==0) {
            start.xIndex=Collections.binarySearch(vEven, start.x);
        } else {
            start.xIndex=Collections.binarySearch(vOdd, start.x);
        }
        if(start.y%2==0) {
            start.yIndex=Collections.binarySearch(hEven, start.y);
        } else {
            start.yIndex=Collections.binarySearch(hOdd, start.y);
        }
        //System.out.println(start+" "+finalTime);
        return getLoc2(start, finalTime);
    }

/*
53 1
H 23
V 24
H 14
V 20
H 10
V 31
V 1
H 33
H 41
V 48
H 25
V 49
V 47
V 18
V 35
H 34
V 26
V 33
V 22
H 29
H 39
H 11
H 31
V 37
H 5
V 28
V 29
H 24
H 50
V 7
H 46
V 40
H 22
V 16
V 39
H 48
V 36
V 2
H 0
H 38
V 17
V 27
V 8
V 44
V 45
H 42
H 32
H 45
H 7
V 38
V 34
H 20
H 1
39 45 28
 */
    static Coord getLoc2(Coord start, long finalTime) {
        //System.out.println(start);
        //System.out.println(start+" "+finalTime);
        if(start.x%2==0) {
            if(start.y%2==0) {
                int newxIndex = vEvenSparseTable[2][start.xIndex];
                int newyIndex = hEvenSparseTable[2][start.yIndex];
                if(newxIndex==-1||newyIndex==-1) return getLoc3(start, finalTime);
                if(vEven.get(newxIndex)-start.x+hEven.get(newyIndex)-start.y>=finalTime-start.t) {
                    return getLoc3(start, finalTime);
                }
            } else {
                int newxIndex = vEvenSparseTable[2][start.xIndex];
                int newyIndex = hOddSparseTable[2][start.yIndex];
                if(newxIndex==-1||newyIndex==-1) return getLoc3(start, finalTime);
                if(vEven.get(newxIndex)-start.x+hOdd.get(newyIndex)-start.y>=finalTime-start.t) {
                    return getLoc3(start, finalTime);
                }
            }
        } else {
            if(start.y%2==0) {
                int newxIndex = vOddSparseTable[2][start.xIndex];
                int newyIndex = hEvenSparseTable[2][start.yIndex];
                if(newxIndex==-1||newyIndex==-1) return getLoc3(start, finalTime);
                if(vOdd.get(newxIndex)-start.x+hEven.get(newyIndex)-start.y>=finalTime-start.t) {
                    return getLoc3(start, finalTime);
                }
            } else {
                int newxIndex = vOddSparseTable[2][start.xIndex];
                int newyIndex = hOddSparseTable[2][start.yIndex];
                if(newxIndex==-1||newyIndex==-1) return getLoc3(start, finalTime);
                if(vOdd.get(newxIndex)-start.x+hOdd.get(newyIndex)-start.y>=finalTime-start.t) {
                    return getLoc3(start, finalTime);
                }
            }
        }
        int i = 2;
        for(i = 2; i < 20; i++) {
            if(start.x%2==0) {
                if(start.y%2==0) {
                    int newxIndex = vEvenSparseTable[i][start.xIndex];
                    int newyIndex = hEvenSparseTable[i][start.yIndex];
                    if(newxIndex==-1||newyIndex==-1) break;
                    if(vEven.get(newxIndex)-start.x+hEven.get(newyIndex)-start.y>=finalTime-start.t) {
                        break;
                    }
                } else {
                    int newxIndex = vEvenSparseTable[i][start.xIndex];
                    int newyIndex = hOddSparseTable[i][start.yIndex];
                    if(newxIndex==-1||newyIndex==-1) break;
                    if(vEven.get(newxIndex)-start.x+hOdd.get(newyIndex)-start.y>=finalTime-start.t) {
                        break;
                    }
                }
            } else {
                if(start.y%2==0) {
                    int newxIndex = vOddSparseTable[i][start.xIndex];
                    int newyIndex = hEvenSparseTable[i][start.yIndex];
                    if(newxIndex==-1||newyIndex==-1) break;
                    if(vOdd.get(newxIndex)-start.x+hEven.get(newyIndex)-start.y>=finalTime-start.t) {
                        break;
                    }
                } else {
                    int newxIndex = vOddSparseTable[i][start.xIndex];
                    int newyIndex = hOddSparseTable[i][start.yIndex];
                    if(newxIndex==-1||newyIndex==-1) break;
                    if(vOdd.get(newxIndex)-start.x+hOdd.get(newyIndex)-start.y>=finalTime-start.t) {
                        break;
                    }
                }
            }
        }
        i--;
        //System.out.println(i);
        if(start.x%2==0) {
            if(start.y%2==0) {
                int newxIndex = vEvenSparseTable[i][start.xIndex];
                int newyIndex = hEvenSparseTable[i][start.yIndex];
                long newX = vEven.get(newxIndex);
                long newY = hEven.get(newyIndex);
                start.t+=newX+newY-start.x-start.y;
                start.x=newX;
                start.y=newY;
                start.xIndex=newxIndex;
                start.yIndex=newyIndex;
                return getLoc2(start, finalTime);
            } else {
                int newxIndex = vEvenSparseTable[i][start.xIndex];
                int newyIndex = hOddSparseTable[i][start.yIndex];
                long newX = vEven.get(newxIndex);
                long newY = hOdd.get(newyIndex);
                start.t+=newX+newY-start.x-start.y;
                start.x=newX;
                start.y=newY;
                start.xIndex=newxIndex;
                start.yIndex=newyIndex;
                return getLoc2(start, finalTime);
            }
        } else {
            if(start.y%2==0) {
                int newxIndex = vOddSparseTable[i][start.xIndex];
                int newyIndex = hEvenSparseTable[i][start.yIndex];
                long newX = vOdd.get(newxIndex);
                long newY = hEven.get(newyIndex);
                start.t+=newX+newY-start.x-start.y;
                start.x=newX;
                start.y=newY;
                start.xIndex=newxIndex;
                start.yIndex=newyIndex;
                return getLoc2(start, finalTime);
            } else {
                int newxIndex = vOddSparseTable[i][start.xIndex];
                int newyIndex = hOddSparseTable[i][start.yIndex];
                long newX = vOdd.get(newxIndex);
                long newY = hOdd.get(newyIndex);
                start.t+=newX+newY-start.x-start.y;
                start.x=newX;
                start.y=newY;
                start.xIndex=newxIndex;
                start.yIndex=newyIndex;
                return getLoc2(start, finalTime);
            }
        }
    }
    static Coord getLoc3(Coord start, long finalTime){
        //System.out.println("LINE 321");
        //System.out.println(start+" "+finalTime);
        while(start.t<finalTime) {
            if(start.t%2==0) {
                long nextIntersect = horiz.higher(start.y);
                if(nextIntersect-start.y>=finalTime-start.t) {
                    long actual = start.y+finalTime-start.t;
                    return new Coord(start.x, actual, 0);
                }
                start.t+=nextIntersect-start.y;
                start.y=nextIntersect;
            } else {
                long nextIntersect = vert.higher(start.x);
                if(nextIntersect-start.x>=finalTime-start.t) {
                    long actual = start.x+finalTime-start.t;
                    return new Coord(actual, start.y, 0);
                }
                start.t+=nextIntersect-start.x;
                start.x=nextIntersect;
            }
        }
        return start;
    }
    static class Coord {
        long x,y,t;
        int xIndex, yIndex;
        public Coord(long x, long y, long t) {
            this.x=x;
            this.y=y;
            this.t=t;
        }
        public String toString() {
            return x+" "+y+" "+t+" "+xIndex+" "+yIndex;
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
