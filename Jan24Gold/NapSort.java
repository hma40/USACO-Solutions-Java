import java.io.*;
import java.util.*;
public class NapSort {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine());
        for(int i = 0; i < t; i++) {
            int n = Integer.parseInt(br.readLine());
            long[] numbers = new long[n];
            StringTokenizer st = new StringTokenizer(br.readLine());
            for(int j = 0; j < n; j++) {
                numbers[j]=Long.parseLong(st.nextToken());
            }
            Arrays.sort(numbers);
            long ans = Math.min((long)(n)*(n+1)/2, numbers[n-1]);
            for(int j = 0; j < n; j++) {
                if(j!=n-1&&numbers[j+1]==numbers[j]) continue;
                long x = n-j-1;
                long z = numbers[j];
                /*
                0 always works
                past some point it will not work
                 */
                long bin0=0, bin1 = j;
                while(bin0+1<bin1) {
                    long mid = (bin0+bin1)/2;
                    if(works0(x,mid,z)) {
                        bin0=mid;
                    } else {
                        bin1=mid;
                    }
                }
                if(!works0(x,bin1,z)) {
                    bin1=bin0;
                }
                if(!works1(x,j,z)) {
                    continue;
                }
                /*
                0 never works
                past some point it always works
                 */
                long bin2=0, bin3=j;
                while(bin2+1<bin3) {
                    long mid = (bin2+bin3)/2;
                    if(works1(x,mid,z)) {
                        bin3=mid;
                    } else {
                        bin2=mid;
                    }
                }
                if(!works1(x, bin2, z)) {
                    bin2=bin3;
                }
                //System.out.println(bin0+" "+bin1+" "+bin2+" "+bin3+" "+x);
                if(bin2>=bin1) {
                    long here = Math.max(numbers[j], (bin2+x)*(bin2+x+1)/2);
                    //System.out.println(bin2+" "+here+" "+z);
                    ans=Math.min(ans, here);
                }
            }
            System.out.println(ans);
        }
    }
    static boolean works0(long x, long y, long z) {
        return (2*x+y+1)*y<=2*z;
    }
    static boolean works1(long x, long y, long z) {
        return (2*x+y)*(y+1)>2*z;
    }
}