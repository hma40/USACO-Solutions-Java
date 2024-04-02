import java.io.*;
import java.util.*;
public class FieldDay {//update class name
    public static void main(String[] args) throws Exception {
        InputOutput a = new InputOutput();
        int c = a.nextInt();
        int n = a.nextInt();
        int[] dist = new int[1<<c];
        int[] values = new int[n];
        Arrays.fill(dist, -1);
        ArrayDeque<int[]> q = new ArrayDeque<>();
        for(int i = 0; i < n; i++) {
            a.read();
            String s = a.next();
            int value=0;
            for(int j = 0; j < c; j++) {
                if(s.charAt(j)=='G') {
                    value += 1 << j;
                }
            }
            values[i]=value;
            q.offer(new int[]{value, 1});
            dist[value]=0;
        }
        while(!q.isEmpty()) {
            int[] x = q.poll();
            for(int i = 0; i < c; i++) {
                int newVal = x[0]^(1<<i);
                if(dist[newVal]==-1) {
                    dist[newVal]=x[1];
                    q.offer(new int[]{newVal, x[1]+1});
                }
            }
        }
        //System.out.println(Arrays.toString(dist));
        StringBuilder ans = new StringBuilder();
        for(int i = 0; i < n; i++) {
            ans.append(c-dist[values[i]^((1<<c)-1)]+"\n");
        }
       // System.out.println(Arrays.toString(dist));
        System.out.print(ans);
    }
    static class InputOutput {
        public BufferedReader bf;
        public PrintWriter out;
        public StringTokenizer st;

        public InputOutput() throws Exception {
            bf = new BufferedReader(new InputStreamReader(System.in));
            read();
        }

        public InputOutput(String fileName) throws Exception {
            bf = new BufferedReader(new FileReader(fileName + ".in"));
            out = new PrintWriter(new FileWriter(fileName + ".out"));
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
            if (out != null) {
                out.close();
            }
        }
        void print(StringBuilder a) {
            if(out!=null) {
                out.print(a);
            } else {
                System.out.print(a);
            }
        }
        void println(Object a) {
            if (out == null) {
                System.out.println(a);
            } else {
                out.println(a);
            }
        }

        void println(int a) {
            if (out == null) {
                System.out.println(a);
            } else {
                out.println(a);
            }
        }
    }
}
