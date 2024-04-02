import java.io.*;
import java.util.*;
public class PareidoliaGold {
    public static void main(String[] args) throws Exception {
        InputOutput a = new InputOutput();
        String s = a.next();
        int n = s.length();
        int[] relevance = new int[s.length()];
        for(int i = 0; i < s.length(); i++) {
            if(s.charAt(i)=='b') {
                relevance[i]=0;
            } else if(s.charAt(i)=='e') {
                relevance[i]=1;
            } else if(s.charAt(i)=='s') {
                relevance[i]=2;
            } else if(s.charAt(i)=='i') {
                relevance[i]=3;
            } else {
                relevance[i]=-1;
            }
        }
        //we want 012231
        int[] deleteCost = new int[n];
        a.read();
        for(int i = 0; i < n; i++) {
            deleteCost[i] = a.nextInt();
        }
        int[] totalSubstrings = new int[n];
        int stage = relevance[0]==0? 1:0;
        for(int i = 1; i < n; i++) {
            totalSubstrings[i]=totalSubstrings[i-1];
            if(relevance[i]==0&&stage==0) {
                stage=1;
            }
            if(relevance[i]==1&&(stage==1||stage==5)) {
                stage++;
            }
            if(relevance[i]==2&&(stage==2||stage==3)) {
                stage++;
            }
            if(relevance[i]==3&&stage==4) {
                stage++;
            }
            if(stage==6) {
                stage=0;
                totalSubstrings[i]++;
            }
        }
        //System.out.println(Arrays.toString(totalSubstrings));
        long[][] noLagBehind = new long[6][n];
        long[][] lagBehind = new long[6][n];
        for(int i = 0; i < 6; i++) {
            Arrays.fill(noLagBehind[i], Integer.MAX_VALUE);
            Arrays.fill(lagBehind[i], Integer.MAX_VALUE);
        }
        noLagBehind[0][0]=0;
        if(relevance[0]==0) {
            noLagBehind[1][0]=0;
        }
        for(int i = 1; i < n; i++) {
            //Delete this letter
            if(totalSubstrings[i]>totalSubstrings[i-1]) {
                lagBehind[0][i]=noLagBehind[0][i-1];
                for(int j = 1; j < 6; j++) {
                    lagBehind[j][i]=noLagBehind[j][i-1]+deleteCost[i];
                }
            } else {
                lagBehind[0][i]=lagBehind[0][i-1];
                noLagBehind[0][i]=noLagBehind[0][i-1];
                for(int j = 1; j < 6; j++) {
                    lagBehind[j][i]=lagBehind[j][i-1]+deleteCost[i];
                }
                for(int j = 1; j < 6; j++) {
                    noLagBehind[j][i]=noLagBehind[j][i-1]+deleteCost[i];
                }
            }
            //don't delete this letter
            if(totalSubstrings[i]>totalSubstrings[i-1]) {
                noLagBehind[0][i]=Math.min(noLagBehind[0][i], noLagBehind[5][i-1]);
                lagBehind[0][i]=Math.min(lagBehind[0][i], lagBehind[5][i-1]);
                lagBehind[2][i]=Math.min(lagBehind[2][i], noLagBehind[1][i-1]);
            } else {
                if (relevance[i] == 0) {
                    noLagBehind[1][i] = Math.min(noLagBehind[1][i], noLagBehind[0][i - 1]);
                    lagBehind[1][i] = Math.min(lagBehind[1][i], lagBehind[0][i - 1]);
                }
                if (relevance[i] == 1) {
                    noLagBehind[0][i] = Math.min(noLagBehind[0][i], lagBehind[5][i - 1]);
                    noLagBehind[2][i] = Math.min(noLagBehind[2][i], noLagBehind[1][i - 1]);
                    lagBehind[2][i] = Math.min(lagBehind[2][i], lagBehind[1][i - 1]);
                }
                if (relevance[i] == 2) {
                    noLagBehind[4][i] = Math.min(noLagBehind[4][i], noLagBehind[3][i - 1]);
                    lagBehind[4][i] = Math.min(lagBehind[4][i], lagBehind[3][i - 1]);
                    noLagBehind[3][i] = Math.min(noLagBehind[3][i], noLagBehind[2][i - 1]);
                    lagBehind[3][i] = Math.min(lagBehind[3][i], lagBehind[2][i - 1]);
                }
                if (relevance[i] == 3) {
                    noLagBehind[5][i] = Math.min(noLagBehind[5][i], noLagBehind[4][i - 1]);
                    lagBehind[5][i] = Math.min(lagBehind[5][i], lagBehind[4][i - 1]);
                }
            }
        }
        /*
        for(int i = 0; i < 6; i++) {
            System.out.println(Arrays.toString(noLagBehind[i]));
        }
        System.out.println();
        for(int i = 0; i < 6; i++) {
            System.out.println(Arrays.toString(lagBehind[i]));
        }
         */
        a.append(totalSubstrings[n-1]);
        a.append(noLagBehind[0][n-1]);
        a.printAns();
    }
    static class InputOutput {
        BufferedReader bf;
        StringTokenizer st;
        StringBuilder ans = new StringBuilder();
        PrintWriter pw;

        public InputOutput() throws IOException {
            bf = new BufferedReader(new InputStreamReader(System.in));
            pw = new PrintWriter(System.out);
            st = new StringTokenizer(bf.readLine());
        }

        public InputOutput(String fileName) throws IOException {
            bf = new BufferedReader(new FileReader(fileName + ".in"));
            pw = new PrintWriter(new FileWriter(fileName + ".out"));
            st = new StringTokenizer(bf.readLine());
        }

        public void read() throws Exception {
            st = new StringTokenizer(bf.readLine());
        }

        public String next() throws Exception {
            return st.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(st.nextToken());
        }

        public long nextLong() {
            return Long.parseLong(st.nextToken());
        }

        public void append(int i) {
            ans.append(i + "\n");
        }

        public void append(String s) {
            ans.append(s + "\n");
        }
        public void append(long i) {
            ans.append(i + "\n");
        }
        public void printAns() {
            pw.print(ans);
            pw.close();
        }
    }
}
