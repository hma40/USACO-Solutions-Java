import java.io.*;
import java.util.*;
public class TargetPractice {
    public static void main(String[] args) throws Exception {
        InputOutput a = new InputOutput();
        int t = a.nextInt();
        int c = a.nextInt();
        boolean[][] hasTarget = new boolean[5][2*c+5];
        int[] invTargets = new int[2*c+5];
        int[] targets = new int[t];
        for(int i = 0; i < t; i++) {
            targets[i] = a.nextInt();
            for(int j = 0; j < 5; j++) hasTarget[j][targets[i]+c+2]=true;
            invTargets[targets[i]+c+2]=i;
        }
        char[] moves = a.next().toCharArray();
        int[][] lastHit = new int[5][t];
        int[][] pos = new int[5][c+1];
        int[][] hitSomething = new int[5][c+1];
        for(int i = 0; i < 5; i++) Arrays.fill(lastHit[i], -1);
        for(int i = 0; i < 5; i++) Arrays.fill(hitSomething[i], -1);
        for(int i = 0; i < 5; i++) pos[i][0]=i+c;
        for(int i = 0; i < c; i++) {
            for(int j = 0; j < 5; j++) {
                if (moves[i] == 'L') {
                    pos[j][i+1]=pos[j][i]-1;
                } else if(moves[i]=='R') {
                    pos[j][i+1]=pos[j][i]+1;
                } else {
                    pos[j][i+1]=pos[j][i];
                }
            }
        }
        int[] totalHits = new int[5];
        for(int i = 0; i < 5; i++) {
            for(int j = c-1; j >= 0; j--) {
                if(moves[j]=='F'&&hasTarget[0][pos[i][j]]) {
                    int index = invTargets[pos[i][j]];
                    if(lastHit[i][index]==-1) {
                        lastHit[i][index] = Math.max(lastHit[i][index], j);
                        hitSomething[i][j]=index;
                    }
                }
            }
            for(int j = 0; j < t; j++) {
                if(lastHit[i][j]!=-1) totalHits[i]++;
            }
        }
        int ans = totalHits[2];
        int here = 0;
        int total = 0;
        //switch to 2 left
        for(int i = 0; i < c; i++) {
            if(moves[i]=='R') {
                //can switch to L
               // System.out.println("line 56: " +i+" "+(total+totalHits[0]));
                here=Math.max(here, total+totalHits[0]);
            } else if(moves[i]=='F') {
                if(hasTarget[0][pos[2][i]]) {
                    hasTarget[0][pos[2][i]]=false;
                    total++;
                    if(lastHit[0][invTargets[pos[2][i]]]!=-1) {
                        lastHit[0][invTargets[pos[2][i]]]=-1;
                        totalHits[0]--;
                    }
                }
                if(hitSomething[0][i]!=-1&&lastHit[0][invTargets[pos[0][i]]]!=-1) {
                    lastHit[0][invTargets[pos[0][i]]]=-1;
                    totalHits[0]--;
                }
            }
        }

        ans=Math.max(ans, here);
        here=0;
        total=0;
        for(int i = 0; i < c; i++) {
            //System.out.println(i+" "+total+" "+totalHits[4]+" "+pos[4][i]);
            if(moves[i]=='L') {
                //can switch to L
               // System.out.println("line 80: " +i+" "+total+" "+totalHits[4]);
                here=Math.max(here, total+totalHits[4]);
            } else if(moves[i]=='F') {
                if(hasTarget[4][pos[2][i]]) {
                    hasTarget[4][pos[2][i]]=false;
                    total++;
                    if(lastHit[4][invTargets[pos[2][i]]]!=-1) {
                        lastHit[4][invTargets[pos[2][i]]]=-1;
                        totalHits[4]--;
                    }
                }
                if(hitSomething[4][i]!=-1&&lastHit[4][invTargets[pos[4][i]]]!=-1) {
                    lastHit[4][invTargets[pos[4][i]]]=-1;
                    totalHits[4]--;
                }
            }
        }

        //move to 1 left
        ans=Math.max(ans, here);
        here=0;
        total=0;
        for(int i = 0; i < c; i++) {
            //System.out.println(i+" "+total+" "+totalHits[1]);
            if(moves[i]=='R') {
                if(hasTarget[1][pos[1][i+1]]) {
                    if(lastHit[1][invTargets[pos[1][i+1]]]!=-1) {
                        //System.out.println("line 106: " +i+" "+(total+totalHits[1]));
                        here=Math.max(here, total+totalHits[1]);
                    } else {
                       // System.out.println("line 109: " +i+" "+(total+totalHits[1]+1));
                        here=Math.max(here, total+totalHits[1]+1);
                    }
                } else {
                   // System.out.println("line 113: " +i+" "+(total+totalHits[1]));
                    here=Math.max(here, total+totalHits[1]);
                }
            }
            if(moves[i]=='F') {
                if(hitSomething[1][i]!=-1&&lastHit[1][invTargets[pos[1][i]]]!=-1) {
                    lastHit[1][invTargets[pos[1][i]]]=-1;
                    totalHits[1]--;
                }
                //switch
                //System.out.println("line 123: " +i+" "+total+" "+totalHits[1]);
                here=Math.max(here, total+totalHits[1]);
                ///switch
                if(hasTarget[1][pos[2][i]]) {
                    hasTarget[1][pos[2][i]]=false;
                    total++;
                    if(lastHit[1][invTargets[pos[2][i]]]!=-1) {
                        lastHit[1][invTargets[pos[2][i]]]=-1;
                        totalHits[1]--;
                    }
                }
            }
        }
        ans=Math.max(ans, here);
        here=0;
        total=0;
        for(int i = 0; i < c; i++) {
            if(moves[i]=='L') {
                if(hasTarget[3][pos[3][i+1]]) {
                    if(lastHit[3][invTargets[pos[3][i+1]]]!=-1) {
                        //System.out.println("line 143: " +i+" "+(total+totalHits[3]));
                        here=Math.max(here, total+totalHits[3]);
                    } else {
                        //System.out.println("line 146: " +i+" "+(total+totalHits[3]+1));
                        here=Math.max(here, total+totalHits[3]+1);
                    }
                } else {
                    //System.out.println("line 150: " +i+" "+(total+totalHits[3]));
                    here=Math.max(here, total+totalHits[3]);
                }
            }
            if(moves[i]=='F') {
                if(hitSomething[3][i]!=-1&&lastHit[3][invTargets[pos[3][i]]]!=-1) {
                    lastHit[3][invTargets[pos[3][i]]]=-1;
                    totalHits[3]--;
                }
                //switch
                //System.out.println("line 160: " +i+" "+total+" "+totalHits[3]);
                here=Math.max(here, total+totalHits[3]);
                ///switch
                if(hasTarget[3][pos[2][i]]) {
                    hasTarget[3][pos[2][i]]=false;
                    total++;
                    if(lastHit[3][invTargets[pos[2][i]]]!=-1) {
                        lastHit[3][invTargets[pos[2][i]]]=-1;
                        totalHits[3]--;
                    }
                }
            }
        }
        ans=Math.max(ans, here);
        System.out.println(ans);
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
