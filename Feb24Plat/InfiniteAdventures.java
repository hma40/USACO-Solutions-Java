import java.io.*;
import java.util.*;
public class InfiniteAdventures {
    static final String ENDL = "\n";
    static int[][] travel;
    static int[][] firstUp;
    static long[][] movesFirstUp;
    static int[] level;
    static boolean[][] vis;
    static int[][][] jump;
    static long[][][] movesJumped;
    /*
5 1
2 4 8 2 2
4 3
4 1 1 1
3 5 1 5 1 5 2 4
3 4
2 5
2 9 4
     */
    public static void main(String[] args) throws Exception {
        InputOutput io = new InputOutput();
        int n = io.nextInt();
        int q = io.nextInt();
        HashMap<Integer, Integer> map = new HashMap<>();
        for(int i = 0; i <= 20; i++) {
            map.put(1<<i, i);
        }
        ArrayList<Integer>[] lvl = new ArrayList[20];
        for(int i = 0; i < 20; i++) {
            lvl[i] = new ArrayList<>();
        }
        level = new int[n];
        for(int i = 0; i < n; i++) {
            level[i]=io.nextInt();
            lvl[map.get(level[i])].add(i);
        }
        travel = new int[n][];
        for(int i = 0; i < n; i++) {
            travel[i] = new int[level[i]];
            for(int j = 0; j < level[i]; j++) {
                travel[i][j]=io.nextInt()-1;
            }
        }
        firstUp = new int[n][];
        movesFirstUp = new long[n][];
        vis = new boolean[n][];
        for(int i = 0; i < n; i++) {
            vis[i] = new boolean[level[i]];
            firstUp[i] = new int[level[i]];
            movesFirstUp[i] = new long[level[i]];
            Arrays.fill(firstUp[i], -2);
        }
        jump = new int[n][62][];
        movesJumped = new long[n][62][];
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < 62; j++) {
                jump[i][j] = new int[level[i]];
                movesJumped[i][j] = new long[level[i]];
            }
        }
        for(ArrayList<Integer> a: lvl) {
            for(int x: a) {
                for(int j = 0; j < level[x]; j++) {
                    if(!vis[x][j]) {
                        calcFirstUp(x, j);
                    }
                }
            }
        }
        for(ArrayList<Integer> a: lvl) {
            for(int x: a) {
                for(int j = 0; j < level[x]; j++) {
                    int tempMod = (j+1)%level[x];
                    int tempLoc = travel[x][j];
                    long moves = 1;
                    if(level[tempLoc]==level[x]) {
                        jump[x][0][j]=tempLoc;
                        movesJumped[x][0][j]=1;
                    } else if(level[tempLoc]>level[x]) {
                        jump[x][0][j]=-1;
                        movesJumped[x][0][j]=-1;
                    } else {
                        boolean done = false;
                        while(level[tempLoc]<level[x]) {
                            if(firstUp[tempLoc][tempMod%level[tempLoc]]==-1) {
                                jump[x][0][j]=-1;
                                movesJumped[x][0][j]=-1;
                                done=true;
                                break;
                            } else {
                                int temptemp = tempLoc;
                                tempLoc = firstUp[temptemp][tempMod%level[tempLoc]];
                                moves+=movesFirstUp[temptemp][tempMod%level[temptemp]];
                                tempMod=(int)((tempMod+movesFirstUp[temptemp][tempMod%level[temptemp]])%level[x]);
                            }
                        }
                        if(!done) {
                            if(level[tempLoc]>level[x]) {
                                jump[x][0][j]=-1;
                                movesJumped[x][0][j]=-1;
                            } else {
                                jump[x][0][j]=tempLoc;
                                movesJumped[x][0][j]=moves;
                            }
                        }
                    }
                }
            }
        }
        for(int leaps = 1; leaps < 62; leaps++) {
            for(int i = 0; i < n; i++) {
                for(int j = 0; j < level[i]; j++) {
                    if(jump[i][leaps-1][j]==-1) {
                        jump[i][leaps][j]=-1;
                        movesJumped[i][leaps][j]=-1;
                    } else {
                        if(movesJumped[i][leaps-1][j]>1000000000000000000L) {
//                            if(i==4&&j==0) {
//                                System.out.println("we reached line 113");
//                            }
                            movesJumped[i][leaps][j]=-1;
                            jump[i][leaps][j]=-1;
                            continue;
                        }
                        jump[i][leaps][j]=jump[jump[i][leaps-1][j]][leaps-1][(int)((j+movesJumped[i][leaps-1][j])%level[i])];
                        if(jump[i][leaps][j]==-1) {
                            movesJumped[i][leaps][j]=-1;
                        } else {
                            movesJumped[i][leaps][j]=movesJumped[jump[i][leaps-1][j]][leaps-1][(int)((j+movesJumped[i][leaps-1][j])%level[i])]+movesJumped[i][leaps-1][j];
                        }
                    }
                }
//                if(leaps==50||leaps==51) {
//                    System.out.println(i+" "+jump[4][0][0]+" "+movesJumped[4][0][0]);
//                }
            }
            //System.out.println(movesJumped[4][0][0]+" "+jump[4][0][0]+" "+leaps);
        }

        for(int i = 0; i < q; i++) {
            //System.out.println(i);
            int location = io.nextInt()-1;
            long time = io.nextLong();
            long timeLeft = io.nextLong();
            while(timeLeft>0) {
                int mod = (int)(time%level[location]);
                if(firstUp[location][mod]==-1||movesFirstUp[location][mod]>timeLeft) {
                    int leaps = 0;
                    while(movesJumped[location][leaps][mod]<=timeLeft&&jump[location][leaps][mod]!=-1) {
//                        System.out.println(leaps+" "+movesJumped[location][leaps][mod]);
                        leaps++;
                    }
//                    System.out.println(movesJumped[location][leaps][mod]+" "+jump[location][leaps][mod]);
//                    System.out.println(movesJumped[location][leaps][mod]<=timeLeft);
//                    System.out.println(jump[location][leaps][mod]!=-1);
                    leaps--;
                    if(leaps==-1) {
                        location=travel[location][mod];
                        time++;
                        timeLeft--;
//                        System.out.println("line 142");
                    } else {

                        int newLoc = jump[location][leaps][mod];
                        timeLeft-=movesJumped[location][leaps][mod];
                        time+=movesJumped[location][leaps][mod];
//                        System.out.println("line 148"+" "+leaps+" "+movesJumped[location][leaps][mod]+" "+jump[location][leaps][mod]);
//                        System.out.println("4,0: "+jump[4][0][0]+" "+movesJumped[4][0][0]);
                        location=newLoc;

                    }
                } else {
                    timeLeft-=movesFirstUp[location][mod];
                    time+=movesFirstUp[location][mod];
                    location=firstUp[location][mod];
//                    System.out.println("line 154");
                }
            }
            io.append(location+1);
        }

        io.printAns();
    }
    static void calcFirstUp(int x, int mod) {
        if(vis[x][mod]) {
            firstUp[x][mod]=-1;
            movesFirstUp[x][mod]=-1;
            return;
        }
        int tempMod = (mod+1)%level[x];
        int tempLoc = travel[x][mod];
        long moves = 1;
        vis[x][mod]=true;
        if(level[tempLoc]>level[x]) {
            firstUp[x][mod]=tempLoc;
            movesFirstUp[x][mod]=1;
            return;
        } else if(level[tempLoc]<level[x]) {
            while(level[tempLoc]<level[x]) {
                if(firstUp[tempLoc][tempMod%level[tempLoc]]==-1) {
                    firstUp[x][mod]=-1;
                    movesFirstUp[x][mod]=-1;
                    return;
                } else {
                    int temptemp = tempLoc;
                    tempLoc = firstUp[temptemp][tempMod%level[tempLoc]];
                    moves+=movesFirstUp[temptemp][tempMod%level[temptemp]];
                    tempMod=(int)((tempMod+movesFirstUp[temptemp][tempMod%level[temptemp]])%level[x]);
                }
            }
            if(level[tempLoc]>level[x]) {
                firstUp[x][mod]=tempLoc;
                movesFirstUp[x][mod]=moves;
                return;
            }
        }
        if(firstUp[tempLoc][tempMod]==-2) {
            calcFirstUp(tempLoc,tempMod);
        }
        if(firstUp[tempLoc][tempMod]==-1) {
            firstUp[x][mod]=-1;
            movesFirstUp[x][mod]=-1;
        } else {
            firstUp[x][mod]=firstUp[tempLoc][tempMod];
            movesFirstUp[x][mod]=moves+movesFirstUp[tempLoc][tempMod];
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
