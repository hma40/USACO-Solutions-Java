import java.io.*;
import java.util.*;
public class Bakery {
    public static void main(String[] args) throws Exception {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        for(int t = Integer.parseInt(bf.readLine()); t > 0; t--) {
            bf.readLine();
            StringTokenizer st = new StringTokenizer(bf.readLine());
            int n = Integer.parseInt(st.nextToken());
            long cTime = Long.parseLong(st.nextToken());
            long mTime = Long.parseLong(st.nextToken());
            ArrayList<Friend> friends = new ArrayList<>();
            for(int i = 0; i < n; i++) {
                st = new StringTokenizer(bf.readLine());
                friends.add(new Friend(Long.parseLong(st.nextToken()),Long.parseLong(st.nextToken()),Long.parseLong(st.nextToken())));
            }
            long left=0;
            long right=cTime+mTime;
            //System.out.println(possible(cTime, mTime, 5, friends));
            while(left+1<right) {
                long mid = (left+right)/2;
                if(possible(cTime, mTime, mid, friends)) {
                    right=mid;
                } else {
                    left=mid;
                }
            }
            if(possible(cTime, mTime, left, friends)) {
                System.out.println(left);
            } else {
                System.out.println(right);
            }
        }
    }

    static boolean possible(long cTime, long mTime, long n, ArrayList<Friend> friends) {
        long num=cTime+mTime-n;
        long cUpper = Math.min(cTime, num-1);
        long mUpper = Math.min(mTime, num-1);
        long cLower = Math.max(1, num-mUpper);
        long mLower = Math.max(1, num-cUpper);
        //System.out.println(num+" "+cUpper+" "+cLower+" "+mUpper+" "+mLower);
        //c1+m1=num
        for(Friend f: friends) {
            //c*c1+m*m1<=w
            if(f.c>f.m) {
                long mTimes = num*f.m;
                long cmc1 = f.w-mTimes;
                long cUp = cmc1/(f.c-f.m);
                //System.out.println(mTimes+" "+cmc1+" "+cUp);
                if(cUp<cLower) {
                    //System.out.println("line 63");
                    return false;
                } else {
                    if(cUp<cUpper) {
                        cUpper = cUp;
                        mLower = num - cUp;
                    }
                    //System.out.println("after friend "+f+" the bounds are changed to "+cUpper+" "+cLower+" "+mUpper+" "+mLower);
                }
            } else if(f.c==f.m) {
                if(f.c*num>f.w) {
                    //System.out.println("line 72");
                    return false;
                }
            } else {
                long cTimes = num*f.c;
                long mcm1 = f.w-cTimes;
                long mUp = mcm1/(f.m-f.c);
                if(mUp<mLower) {
                    //System.out.println("line 80");
                    return false;
                } else {
                    if(mUp<mUpper) {
                        mUpper = mUp;
                        cLower = num - mUp;
                    }
                    //System.out.println("after friend "+f+" the bounds are changed to "+cUpper+" "+cLower+" "+mUpper+" "+mLower);
                }
            }
        }
        //System.out.println("num: "+num+" bounds: "+cLower+" "+cUpper+" "+mLower+" "+mUpper);
        return true;
    }
    static class Friend {
        long c;
        long m;
        long w;
        Friend(long cc, long mm, long ww) {
            c=cc;
            m=mm;
            w=ww;
        }
        public String toString() {
            return c+" "+m+" "+w;
        }
    }
}