import java.io.*;
import java.util.*;
public class BarnTree {//update class name
    static boolean[] visited;
    static StringBuilder ans = new StringBuilder();
    static long[] haybales;
    static int steps=0;
    static ArrayList<Integer>[] connections;
    static Long[] nums;
    static boolean[] visited2;
    public static void main(String[] args) throws Exception {
        InputOutput a = new InputOutput(true, "");
        int n = a.nextInt();
        a.read();
        long avg=0;
        connections = new ArrayList[n+1];
        for(int i = 1; i <= n; i++) {
            connections[i] = new ArrayList<>();
        }
        visited = new boolean[n+1];
        haybales = new long[n+1];
        visited2 = new boolean[n+1];
        for(int i = 1; i <= n; i++) {
            haybales[i]=a.nextInt();
            avg+=haybales[i];
        }
        nums = new Long[n+1];
        avg/=n;
        for(int i = 1; i <= n; i++) {
            haybales[i]-=avg;
        }
        for(int i = 0; i < n-1; i++) {
            a.read();
            int x = a.nextInt();
            int y = a.nextInt();
            connections[x].add(y);
            connections[y].add(x);
        }
        dfs(1);
        doStuff(1);
        System.out.print(steps+"\n"+ans);
    }

    static void dfs(int i) {
        visited[i]=true;
        long sum=haybales[i];
        for(int j: connections[i]) {
            if(visited[j]) continue;
            if(nums[j]==null) {
                dfs(j);
            }
            sum+=nums[j];
        }
        nums[i]=sum;
    }
    static void doStuff(int i) {
        visited2[i]=true;
        for(int j: connections[i]) {
            if(visited2[j]) {
                continue;
            }
            if(nums[j]>0) {
                doStuff(j);
                ans.append(j+" "+i+" "+nums[j]+"\n");
                steps++;
            }
            if(nums[j]==0) {
                doStuff(j);
            }
        }
        for(int j: connections[i]) {
            if(visited2[j]) {
                continue;
            }
            ans.append(i+" "+j+" "+-nums[j]+"\n");
            steps++;
            doStuff(j);
        }
    }
    static class InputOutput {
        public BufferedReader bf;
        public PrintWriter out;
        public StringTokenizer st;
        InputOutput(boolean standard, String name) throws IOException {
            if(standard) {
                bf = new BufferedReader(new InputStreamReader(System.in));
                out = null;
            } else {
                bf = new BufferedReader(new FileReader(name + ".in"));
                out = new PrintWriter(new FileWriter(name+".out"));
            }
            read();
        }
        String next() throws Exception {
            return st.nextToken();
        }
        int nextInt() throws Exception {
            return Integer.parseInt(st.nextToken());
        }
        void read() throws IOException {
            st = new StringTokenizer(bf.readLine());
        }
        void closeInput() throws IOException {
            bf.close();
        }
        void closeOutput() throws IOException {
            if(out!=null) {
                out.close();
            }
        }
        void println(String a) {
            if(out==null) {
                System.out.println(a);
            } else {
                out.println(a);
            }
        }
        void println(int a) {
            if(out==null) {
                System.out.println(a);
            } else {
                out.println(a);
            }
        }
    }
}