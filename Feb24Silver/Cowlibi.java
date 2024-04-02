import java.io.*;
import java.util.*;
/*
2 1
-3 -4 0
3 4 15
-1 0 10
 */
public class Cowlibi {
    public static void main(String[] args) throws Exception {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        int g = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());
        ArrayList<Grazing> grazings = new ArrayList<>();
        for(int i = 0; i < g; i++) {
            st = new StringTokenizer(bf.readLine());
            grazings.add(new Grazing(Long.parseLong(st.nextToken()),Long.parseLong(st.nextToken()),Long.parseLong(st.nextToken())));
        }
        Collections.sort(grazings);
        //System.out.println(grazings);
        int ans=0;
        for(int i = 0; i < n; i++) {
            st = new StringTokenizer(bf.readLine());
            long x = Long.parseLong(st.nextToken());
            long y = Long.parseLong(st.nextToken());
            long time = Long.parseLong(st.nextToken());
            int left = 0;
            int right = grazings.size()-1;
            while(left+1<right) {
                int mid = (left+right)/2;
                if(grazings.get(mid).time>time) {
                    right=mid;
                }
                if(grazings.get(mid).time==time) {
                    left=mid;
                    right=mid;
                    break;
                }
                if(grazings.get(mid).time<time) {
                    left=mid;
                }
            }
            //System.out.println(left+" "+right);
            if(grazings.get(left).time==time) {
                if(grazings.get(left).x!=x||grazings.get(left).y!=y) {
                    //ystem.out.println(i);
                    ans++;
                }
            } else if(grazings.get(right).time==time) {
                if(grazings.get(right).x!=x||grazings.get(right).y!=y) {
                    //System.out.println(i);
                    ans++;
                }
            } else if(grazings.get(left).time>time) {
                long distSq = distanceSquared(grazings.get(left).x, grazings.get(left).y, x, y);
                long timeSq = (time-grazings.get(left).time)*(time-grazings.get(left).time);
                if(distSq>timeSq) {
                    //System.out.println(i);
                    ans++;
                }
            } else if(grazings.get(right).time<time) {
                long distSq = distanceSquared(grazings.get(right).x, grazings.get(right).y, x, y);
                long timeSq = (time-grazings.get(right).time)*(time-grazings.get(right).time);
                if(distSq>timeSq) {
                    //System.out.println(i);
                    ans++;
                }
            } else {
                long distSqL = distanceSquared(grazings.get(left).x, grazings.get(left).y, x, y);
                long timeSqL = (time-grazings.get(left).time)*(time-grazings.get(left).time);
                long distSqR = distanceSquared(grazings.get(right).x, grazings.get(right).y, x, y);
                long timeSqR = (time-grazings.get(right).time)*(time-grazings.get(right).time);
                if(distSqL>timeSqL||distSqR>timeSqR) {
                    //System.out.println(i);
                    ans++;
                }
            }
        }
        System.out.println(ans);
    }
    static long distanceSquared(long x1, long y1, long x2, long y2) {
        long dx = Math.abs(x1-x2);
        long dy = Math.abs(y1-y2);
        return dx*dx+dy*dy;
    }
    static class Grazing implements Comparable<Grazing> {
        long x;
        long y;
        long time;
        public int compareTo(Grazing other) {
            if(time>other.time) {
                return 1;
            }
            return -1;
        }
        public Grazing(long xx, long yy, long tt) {
            x=xx;
            y=yy;
            time=tt;
        }
        public String toString() {
            return "location: "+x+" "+y+" time: "+time;
        }
    }
}