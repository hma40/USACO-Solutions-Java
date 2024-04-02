    import java.io.*;
    import java.util.*;
    public class MooRouteSilver {
        static int[] crosses;
        static StringBuilder ans;
        static int position=0;
        public static void main(String[] args) throws Exception {
            Scanner in = new Scanner(System.in);
            int n = in.nextInt();
            crosses = new int[n];
            for(int i = 0; i < n; i++) {
                crosses[i]=in.nextInt();
            }
            in.close();
            ans = new StringBuilder();
            while(crosses[0]>0) {
                runRight();
                //System.out.println(Arrays.toString(crosses)+" "+ans+" "+position);
                runLeft();
                //System.out.println(Arrays.toString(crosses)+" "+ans+" "+position);
            }
            System.out.println(ans);
        }
        static void runRight() {
            for(int i = position; i < crosses.length; i++) {
                if(crosses[i]==0) {
                    return;
                }
                crosses[i]--;
                ans.append("R");
                position++;
            }
        }
        static void runLeft() {
            for(int i = position; i > 0; i--) {
                if(crosses[i-1]==1&&allDone()) {
                    ans.append("L");
                } else if(crosses[i-1]==1) {
                    return;
                } else {
                    ans.append("L");
                }
                crosses[i-1]--;
                position--;
            }
        }
        static boolean allDone() {
            for(int i = position; i < crosses.length; i++) {
                if(crosses[position]!=0) {
                    return false;
                }
            }
            return true;
        }
    }
