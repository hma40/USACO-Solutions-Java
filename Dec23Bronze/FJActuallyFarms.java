import java.io.*;
import java.util.*;
public class FJActuallyFarms {
    public static void main(String[] args) throws Exception {
        InputOutput a = new InputOutput();
        for(int t = a.nextInt(); t > 0; t--) {
            a.read();
            int n = a.nextInt();
            Plant[] plants = new Plant[n];
            for(int i = 0; i < n; i++) {
                plants[i] = new Plant();
            }
            a.read();
            for(int i = 0; i < n; i++) {
                plants[i].init = a.nextLong();
            }
            a.read();
            for(int i = 0; i < n; i++) {
                plants[i].rate = a.nextLong();
            }
            a.read();
            for(int i = 0; i < n; i++) {
                plants[i].place=a.nextInt();
            }
            Arrays.sort(plants);
            long lbound = 0, ubound = Long.MAX_VALUE;
            boolean done = false;
            for(int i = 1; i < n; i++) {
                if(plants[i-1].rate==plants[i].rate) {
                    if(plants[i-1].init<plants[i].init) {
                        continue;
                    } else {
                        a.append(-1);
                        done=true;
                        break;
                    }
                }
                long dc = plants[i].init-plants[i-1].init;
                long dx = plants[i-1].rate-plants[i].rate;
                if(dx<0) {
                    //setting lower bound
                    if(dc>0) {
                        //always true
                        continue;
                    }
                    lbound=Math.max(lbound,dc/dx+1);
                } else {
                    if(dc<0) {
                        //always false;
                        a.append(-1);
                        done=true;
                        break;
                    }
                    if(dc%dx==0) {
                        ubound=Math.min(ubound,dc/dx-1);
                    } else {
                        ubound = Math.min(ubound, dc / dx);
                    }
                }

            }
            if(!done) {
                if(lbound<=ubound) {
                    a.append(lbound);
                } else {
                    a.append(-1);
                }
            }
        }
        a.printAns();
    }
    static class Plant implements Comparable<Plant> {
        long init, rate;
        int place;
        public String toString() {
            return init+" "+rate+" "+place;
        }
        public int compareTo(Plant other) {
            return -Integer.compare(place, other.place);
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
