import java.io.*;
import java.util.*;

public class Pareidolia {
    public static void main(String[] args) throws Exception{
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String s = bf.readLine();
        int[] last = new int[5];
        int n = s.length();
        ArrayList<long[]> intervals = new ArrayList<>();
        Arrays.fill(last, -1);
        for(int i = 0; i < n; i++) {
            //System.out.println(Arrays.toString(last));
            char c = s.charAt(i);
            if(c=='b') {
                last[0]=i;
            } else if(c=='e') {
                if(last[0]!=-1) {
                    last[1] = last[0];
                    last[0] = -1;
                }
                if(last[4]!=-1) {
                    intervals.add(new long[]{last[4], i});
                    last[4]=-1;
                }
            } else if(c=='s') {
                if(last[2]!=-1) {
                    last[3]=last[2];
                    last[2]=-1;
                }
                if(last[1]!=-1) {
                    last[2] = last[1];
                    last[1] = -1;
                }
            } else if(c=='i') {
                if(last[3]!=-1) {
                    last[4] = last[3];
                    last[3] = -1;
                }
            }
        }
        long ans = 0;
        int currentIndex=0;
        for(int i = 0; i < n; i++) {
            long[] temp = intervals.get(currentIndex);
            if(temp[0]<i) {
                currentIndex++;
                if(currentIndex>=intervals.size()) {
                    break;
                }
            }
            int lastt = currentIndex;
            for(int j = currentIndex; j < intervals.size(); j++) {
                if(j>=currentIndex+1&&intervals.get(j)[0]<intervals.get(lastt)[1]) {
                    continue;
                } else {
                    lastt=j;
                    ans+=n-intervals.get(j)[1];
                }
            }
        }

        System.out.println(ans);
    }
}
//besbesiebesiebesiebesiesie