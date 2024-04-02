import java.io.*;
import java.util.*;
public class EqualSumSubarrays {
    public static void main(String[] args) throws Exception {
        InputOutput a = new InputOutput();
        long time = System.currentTimeMillis();
        int n = a.nextInt();
        long[] numbers = new long[n];
        a.read();
        for(int i = 0; i < n; i++) {
            numbers[i] = a.nextLong();
        }
        ArrayList<Subarray> subarrays = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            long sumHere = 0;
            for(int j = i; j < n; j++) {
                sumHere+=numbers[j];
                subarrays.add(new Subarray(i,j,sumHere));
            }
        }
        Collections.sort(subarrays, (x,y)->{
           return (x.sum-y.sum>0? 1:-1);
        });
        //System.out.println(subarrays);
        long[] answer = new long[n];
        Arrays.fill(answer, 1000000000000000000L);
        for(int i = 0; i < n; i++) {
            ArrayList<Long> included = new ArrayList<>();
            ArrayList<Long> notIncluded = new ArrayList<>();
            for(Subarray s: subarrays) {
                if(s.left<=i&&s.right>=i) {
                    included.add(s.sum);
                } else {
                    notIncluded.add(s.sum);
                }
            }
            //System.out.println(i+" "+included+" "+notIncluded);
            int iPointer = 0, niPointer = 0;
            if(notIncluded.size()==1) {
                answer[i]=Math.min(Math.abs(included.get(0)-notIncluded.get(0)), Math.abs(included.get(1)-notIncluded.get(0)));
                continue;
            }
            if(included.get(included.size()-1)<notIncluded.get(0)) {
                answer[i]=notIncluded.get(0)-included.get(included.size()-1);
            } else if(notIncluded.get(notIncluded.size()-1)<included.get(0)) {
                answer[i]=included.get(0)-notIncluded.get(notIncluded.size()-1);
            } else if(notIncluded.get(notIncluded.size()-1)<included.get(included.size()-1)) {
                while(notIncluded.get(niPointer)<included.get(0)) {
                    niPointer++;
                }
                if(niPointer>0) {
                    niPointer--;
                }
                answer[i]=Math.abs(included.get(0)-notIncluded.get(niPointer));
                for(;niPointer<notIncluded.size(); niPointer++) {
                    while(included.get(iPointer)<notIncluded.get(niPointer)) {
                        iPointer++;
                    }
                    if(iPointer>0) {
                        iPointer--;
                    }
                    answer[i]=Math.min(answer[i], Math.min(Math.abs(included.get(iPointer)-notIncluded.get(niPointer)), Math.abs(included.get(iPointer+1)-notIncluded.get(niPointer))));
                }
            } else {
                while(included.get(iPointer)<notIncluded.get(0)) {
                    iPointer++;
                }
                if(iPointer>0) {
                    iPointer--;
                }
                answer[i]=Math.abs(notIncluded.get(0)-included.get(iPointer));
                for(;iPointer<included.size(); iPointer++) {
                    while(notIncluded.get(niPointer)<included.get(iPointer)) {
                        niPointer++;
                    }
                    if(niPointer>0) {
                        niPointer--;
                    }
                    answer[i]=Math.min(answer[i], Math.min(Math.abs(included.get(iPointer)-notIncluded.get(niPointer)), Math.abs(included.get(iPointer)-notIncluded.get(niPointer+1))));
                }
            }
        }
        for(long ans: answer) a.append(ans);
        //a.append(System.currentTimeMillis()-time);
        a.printAns();
    }
    static class Subarray {
        int left,right;
        long sum;
        public Subarray(int left, int right, long sum) {
            this.left=left;
            this.right=right;
            this.sum=sum;
        }
        public String toString() {
            return left+" "+right+" "+sum;
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

        public int nextInt() {
            return Integer.parseInt(st.nextToken());
        }

        public long nextLong() {
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
