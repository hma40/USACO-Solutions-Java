import java.io.*;
import java.util.*;
/*
10
0 2 3 8 8 8 8 8 10 11
0 3 8 8 8 8 8 10 11
0 8 8 8 8 8 10 11
0 0 0 0 0 2 3
0 0 0 0 2 3
0 0 0 2 3
0 0 2 3
0 2 3
0 1
0

6
0 0 1 1 2 2
0 1 1 2 2
0 0 1 1
0 1 1
0 0
0

4
0 1 1 2
0 0 2
0 2
0
 */
public class RangeReconstruction {//update class name
    public static void main(String[] args) throws Exception {
        InputOutput a = new InputOutput(true, "");
        int n = a.nextInt();
        int[][] range = new int[n][n];
        for(int[] x: range) Arrays.fill(x, -1);

        for(int i = 0; i < n; i++) {
            a.read();
            for(int j = i; j < n; j++) {
                range[i][j]=a.nextInt();
            }
        }
        ArrayList<Duplicate> duplicates = new ArrayList<>();
        //two pointers
        int left;
        int right=0;
        ArrayList<int[]> firstTwo = new ArrayList<>();
        while(right<n-1) {
            //System.out.println(right);
            if(range[right][right+1]==0) {
                left=right;
                while(right<n-1&&range[right][right+1]==0) {
                    right++;
                }
                if(firstTwo.size()!=0) {
                    if(right==n-1) {
                        firstTwo.add(new int[]{firstTwo.get(firstTwo.size()-1)[0]});
                        firstTwo.remove(firstTwo.size()-2);
                    } else {
                        //System.out.println(right);
                        firstTwo.get(firstTwo.size() - 1)[1] = range[left - 1][right + 1];
                    }
                }
                if(right!=n-1) {

                    if(right!=n-2) {
                        firstTwo.add(new int[]{range[right][right + 1], range[right][right + 2]});
                    } else {
                        firstTwo.add(new int[]{range[right][right + 1]});
                    }
                }
                duplicates.add(new Duplicate(left, right-left));
            } else {
                if(right==n-2) {
                    firstTwo.add(new int[]{range[right][right+1]});
                } else {
                    firstTwo.add(new int[]{range[right][right+1], range[right][right+2]});
                }
            }
            right++;
        }
/*
        System.out.println(duplicates);
        for(int[] xx: firstTwo) {
            System.out.println(Arrays.toString(xx));
        }

 */
        ArrayList<Integer> list = new ArrayList<>();
        list.add(0);
        boolean plus = true;
        int i = 0;
        ArrayList<Integer> ints = new ArrayList<>();
        ints.add(0);
        while(i<firstTwo.size()) {
            if(i==0) {
                list.add(firstTwo.get(0)[0]);
                ints.add(firstTwo.get(0)[0]);
            } else {
                int n1 = list.get(i)+firstTwo.get(i)[0];
                int max = Math.max(ints.get(0), Math.max(ints.get(1), n1));
                int min = Math.min(ints.get(0), Math.min(ints.get(1), n1));
                if(max-min==firstTwo.get(i-1)[1]) {
                    list.add(n1);
                    ints.add(n1);
                } else {
                    list.add(list.get(i)-firstTwo.get(i)[0]);
                    ints.add(list.get(i)-firstTwo.get(i)[0]);
                }
                ints.remove(0);
            }
            i++;
        }
        Collections.sort(duplicates);
        for(Duplicate d: duplicates) {
            for(int j = 0; j < d.num; j++) {
                list.add(d.startAt, list.get(d.startAt));
            }
        }
        StringBuilder out = new StringBuilder();
        for(int xx: list) {
            out.append(xx+" ");
        }
        System.out.println(out.substring(0,out.length()-1));
    }
    static class Duplicate implements Comparable<Duplicate> {
        int startAt;
        int num;
        public int compareTo(Duplicate other) {
            return startAt-other.startAt;
        }
        public Duplicate(int s, int n) {
            startAt=s;
            num=n;
        }
        public String toString() {
            return "start at: "+startAt+" num: "+num;
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