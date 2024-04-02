import java.io.*;
import java.util.*;
public class MooRouteII {
    static int[] layover;
    public static void main(String[] args) throws Exception {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        //BufferedReader bf = new BufferedReader(new FileReader("20.in"));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        ArrayList<Flight>[] flights = new ArrayList[n+1];
        for(int i = 1; i <= n; i++) {
            flights[i]=new ArrayList<>();
        }
        layover = new int[n+1];
        for(int i = 0; i < m; i++) {
            st = new StringTokenizer(bf.readLine());
            int c = Integer.parseInt(st.nextToken());
            flights[c].add(new Flight(Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken())));
        }
        st = new StringTokenizer(bf.readLine());
        for(int i = 1; i <= n; i++) {
            layover[i]=Integer.parseInt(st.nextToken());
        }
        layover[1]=0;
        Deque<int[]> q = new ArrayDeque<>();
        q.offer(new int[]{1,0});
        int[] ans = new int[n+1];
        Arrays.fill(ans, -1);
        for(ArrayList<Flight> fl: flights) {
            if(fl==null) continue;
            Collections.sort(fl);
        }
        int[] visited = new int[n+1];
        //System.out.println(Arrays.toString(flights));
        while(!q.isEmpty()) {
            int[] temp = q.poll();
            int city = temp[0];
            int time = temp[1];
            if(ans[city]==-1) {
                ans[city]=time;
            } else {
                ans[city]=Math.min(ans[city], time);
            }
            //System.out.println(city+" "+time);
            time+=layover[city];
            while(visited[city]<flights[city].size()) {
                Flight f = flights[city].get(visited[city]);
                if(time<=f.aTS) {
                    q.offer(new int[]{f.aCE, f.aTE});
                } else {
                    break;
                }
                visited[city]++;
            }
        }
        StringBuilder out = new StringBuilder();
        for(int i = 1; i <= n; i++) {
            out.append(ans[i]+"\n");
        }
        System.out.print(out);
    }
    static class Flight implements Comparable<Flight> {
        int aCE;
        int aTS;
        int aTE;
        public int compareTo(Flight other) {
            return other.aTS-aTS;
        }
        public String toString() {
            return " to "+aCE+" time: from "+aTS+" to "+aTE;
        }
        public Flight(int ts, int ce, int te) {
            aCE=ce;
            aTS=ts;
            aTE=te;
        }
    }
    static class InputOutput {
        public BufferedReader bf;
        public PrintWriter out;
        public StringTokenizer st;
        public InputOutput() throws Exception{
            bf = new BufferedReader(new InputStreamReader(System.in));
            read();
        }
        public InputOutput(String fileName) throws Exception {
            bf = new BufferedReader(new FileReader(fileName+".in"));
            out = new PrintWriter(new FileWriter(fileName+".out"));
            read();
        }
        String next() throws Exception {
            return st.nextToken();
        }
        long nextLong() throws Exception {
            return Long.parseLong(st.nextToken());
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