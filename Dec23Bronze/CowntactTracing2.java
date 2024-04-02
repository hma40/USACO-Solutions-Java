import java.io.*;
import java.util.*;
public class CowntactTracing2 {
    public static void main(String[] args) throws Exception {
        InputOutput a = new InputOutput();
        int n = a.nextInt();
        a.read();
        char[] s = a.next().toCharArray();
        ArrayList<Integer> consec = new ArrayList<>();
        int left = 0;
        int right = n-1;
        while(left<n) {
            if(s[left]=='0') {
                break;
            } else {
                left++;
            }
        }
        while(right>=0) {
            if(s[right]=='0') {
                break;
            } else {
                right--;
            }
        }
        if(left==n) {
            System.out.println(1);
            return;
        }
        int minVal = Integer.MAX_VALUE;
        int prev = 0;
        for(int i = left; i <= right; i++) {
            if(s[i]=='0') {
                if(prev!=0) {
                    consec.add(prev);
                    minVal=Math.min(minVal,prev);
                    prev=0;
                }
            } else {
                prev++;
            }
        }
        if(consec.size()==0&&left==0&&right==n-1) {
            System.out.println(0);
            return;
        }
        //System.out.println(consec+" "+minVal);
        int maxDays = Integer.MAX_VALUE;
        if(left>0) {
            maxDays=Math.min(maxDays, left-1);
        }
        if(right<n-1) {
            maxDays=Math.min(maxDays, n-right-2);
        }
        if(consec.size()!=0) {
            maxDays=Math.min(maxDays,(minVal-1)/2);
        }
        int ans = 0;
        if(left>0) {
            ans+=calc(maxDays, left);
        }
        if(right<n-1) {
            ans+=calc(maxDays,n-right-1);
        }
        for(int i: consec) {
            ans+=calc(maxDays, i);
        }
        System.out.println(ans);
    }
    static int calc(int maxDays, int x) {
        int temp = 2*maxDays+1;
        if(x%temp==0) {
            return x/temp;
        }
        return x/temp+1;
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
