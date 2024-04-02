import java.io.*;
import java.util.*;
public class FertilizingPastures {
    static ArrayList<Integer>[] adj;
    static long[] growRate;
    static int[] par;
    static long[] subtreeTotal;
    static int[] depth;
    static int[] subtreeDepth;
    static long ans1=0,ans2=0;
    static int n,t;
    static int[] maxDepth;
    static int[] subtreeSize;
    static InputOutput a;

    static {
        try {
            a = new InputOutput();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static long[][] dp;
    public static void main(String[] args) throws Exception {
        n = a.nextInt();
        t = a.nextInt();
        dp = new long[n][2];
        subtreeSize = new int[n];
        Arrays.fill(subtreeSize,1);
        maxDepth = new int[n];
        subtreeTotal = new long[n];
        subtreeDepth = new int[n];
        adj = new ArrayList[n];
        growRate = new long[n];
        par = new int[n];
        par[0]=-1;
        depth = new int[n];
        for(int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }
        for(int i = 1; i < n; i++) {
            a.read();
            par[i]=a.nextInt()-1;
            adj[par[i]].add(i);
            growRate[i]=a.nextLong();
        }
        dfs(0);
        if(t==0) {
            ans1=2*(n-1);
        } else {
            ans1=2*(n-1)-maxDepth[0];
        }
        for(int i = 0; i < n; i++) {
            ans2+=growRate[i]*depth[i];
        }
        //System.out.println(ans2);
        //System.out.println(Arrays.toString(subtreeSize));
        //System.out.println(Arrays.toString(maxDepth));
        dfs2(0);
        if(t==0) ans2+=dp[0][0];
        else ans2+=dp[0][1];
        System.out.println(ans1+" "+ans2);
    }
    static void dfs(int node) {
        if(node!=0) depth[node]=depth[par[node]]+1;
        subtreeDepth[node]=depth[node];
        subtreeTotal[node]+=growRate[node];
        maxDepth[node]=depth[node];
        for(int nei: adj[node]) {
            dfs(nei);
            subtreeSize[node]+=subtreeSize[nei];
            maxDepth[node]=Math.max(maxDepth[node], maxDepth[nei]);
            subtreeDepth[node]=Math.max(subtreeDepth[node], subtreeDepth[nei]);
            subtreeTotal[node]+=subtreeTotal[nei];
        }
    }
    static void dfs2(int node) {
//        System.out.println(node);
        for(int i: adj[node]) {
            dfs2(i);
            dp[node][0]+=dp[i][0];
        }
        if(adj[node].size()>0) {
            ArrayList<SubtreeInfo> infos = new ArrayList<>();
            long sum = subtreeTotal[node] - growRate[node];
            for (int nei : adj[node]) {
                infos.add(new SubtreeInfo(subtreeTotal[nei], subtreeDepth[nei], nei, subtreeSize[nei]));
            }
            Collections.sort(infos, (a, b) -> {
                return (a.subtreeTotal * b.subtreeSize - a.subtreeSize * b.subtreeTotal > 0 ? -1 : 1);
            });
            long time = 0;
            long totalSum = 0;
            for (int i = 0; i < infos.size(); i++) {
                dp[node][0] += time * 2 * infos.get(i).subtreeTotal;
                time += infos.get(i).subtreeSize;
                totalSum += infos.get(i).subtreeTotal;
            }
            dp[node][1]=Long.MAX_VALUE;
            for(int i = 0; i < infos.size(); i++) {
                time-=infos.get(i).subtreeSize;
                totalSum-=infos.get(i).subtreeTotal;
                if(infos.get(i).subtreeDepth==subtreeDepth[node]) {
                    dp[node][1]=Math.min(dp[node][1], dp[node][0]+2*time*infos.get(i).subtreeTotal-2*totalSum*infos.get(i).subtreeSize-dp[infos.get(i).node][0]+dp[infos.get(i).node][1]);
                }
            }
        }
    }
    static class SubtreeInfo {
        long subtreeTotal;
        int subtreeDepth;
        int subtreeSize;
        int node;
        public SubtreeInfo(long subtreeTotal, int subtreeDepth, int node, int subtreeSize) {
            this.subtreeTotal=subtreeTotal;
            this.subtreeDepth=subtreeDepth;
            this.node=node;
            this.subtreeSize=subtreeSize;
        }
        public String toString() {
            return subtreeTotal+" "+subtreeDepth+" "+node+" "+subtreeSize;
        }
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
