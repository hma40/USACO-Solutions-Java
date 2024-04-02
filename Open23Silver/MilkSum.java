import java.io.*;
import java.util.*;

public class MilkSum {
    public static void main(String[] args) throws Exception{
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(bf.readLine());
        int[] arr = new int[n];
        int[] arr2 = new int[n];
        StringTokenizer st = new StringTokenizer(bf.readLine());
        for(int i = 0; i < n; i++) {
            arr[i]=Integer.parseInt(st.nextToken());
            arr2[i]=arr[i];
        }
        int prev = Integer.MIN_VALUE;
        HashMap<Integer, Integer> map = new HashMap<>();
        Arrays.sort(arr2);
        long totalMilk = 0;
        for(int i = 0; i < n; i++) {
            if(arr2[i]!=prev) {
                prev=arr2[i];
                map.put(arr2[i], i);
            }
            totalMilk+=arr2[i]*(long)(i+1);
        }

        long sum=0;
        long[] prefSum = new long[n+1];
        for(int i = 1; i <= n; i++) {
            sum+=arr2[i-1];
            prefSum[i]=sum;
        }
        StringBuilder ans = new StringBuilder();
        for(int q = Integer.parseInt(bf.readLine()); q > 0; q--) {
            st = new StringTokenizer(bf.readLine());
            int w = Integer.parseInt(st.nextToken())-1;
            int x = Integer.parseInt(st.nextToken());
            int y = map.get(arr[w]);
            int z = Arrays.binarySearch(arr2, x);
            //System.out.println(w+" "+x+" "+y+" "+z);
            long temp = totalMilk;
            if(z<0) {
                z=-z-1;
                if(z==y) {
                    temp = temp - (long)((y+1))*arr[w] + (long)((y+1))*x;
                } else if(z<y) {
                    temp = temp - ((long)(y+1))*arr[w] + prefSum[y] - prefSum[z] + (long)((z+1))*x;
                } else {
                    temp = temp - (long)((y+1)) * arr[w] - (prefSum[z]-prefSum[y+1]) + (long)z*x;
                }
            } else {
                if (z > y) {
                    temp = temp - (long)((y + 1)) * arr[w] - (prefSum[z] - prefSum[y + 1]) + (long)z * x;
                } else if (z < y) {
                    //System.out.println("74");
                    temp = temp - (long)((y + 1)) * arr[w] + (prefSum[y] - prefSum[z]) + (long)((z + 1)) * x;
                }
            }
            ans.append(temp+"\n");
        }
        System.out.print(ans);
    }
}