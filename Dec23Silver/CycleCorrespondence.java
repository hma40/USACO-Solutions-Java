import java.io.*;
import java.util.*;
public class CycleCorrespondence {
    public static void main(String[] args) throws Exception {
        InputOutput a = new InputOutput();
        int n = a.nextInt();
        int k = a.nextInt();
        a.read();
        int[] anna = new int[k];
        int[] bess = new int[k];
        int[] bess2 = new int[k];
        boolean[] seen = new boolean[n];
        for(int i = 0; i < k; i++) {
            anna[i] = a.nextInt()-1;
            seen[anna[i]]=true;
        }
        a.read();
        for(int i = 0; i < k; i++) {
            bess[i] = a.nextInt()-1;
            bess2[k-i-1]=bess[i];
            seen[bess[i]]=true;
        }
        int[] invAnna = new int[n];
        Arrays.fill(invAnna, -1);
        for(int i = 0; i < k; i++) {
            invAnna[anna[i]]=i;
        }
        //System.out.println(Arrays.toString(invAnna));
        int[][] count = new int[k][2];
        for(int i = 0; i < k; i++) {
            int diff = i-invAnna[bess[i]];
            if(invAnna[bess[i]]==-1) continue;
            if(diff<0) diff+=k;
            count[diff][0]++;
        }
        for(int i = 0; i < k; i++) {
            int diff = i-invAnna[bess2[i]];
            if(invAnna[bess2[i]]==-1) continue;
            if(diff<0) diff+=k;
            count[diff][1]++;
        }
        int ans = 0;
        int cnt = 0;
        for(int i = 0; i < n; i++) {
            cnt+=seen[i]? 0:1;
        }
        for(int i = 0; i < k; i++) {
            ans=Math.max(ans, Math.max(count[i][0], count[i][1]));
        }
        System.out.println(cnt+ans);
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

        public int nextInt() throws Exception {
            return Integer.parseInt(st.nextToken());
        }

        public long nextLong() throws Exception {
            return Long.parseLong(st.nextToken());
        }

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
