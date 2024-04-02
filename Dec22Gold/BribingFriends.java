import java.util.*;
import java.io.*;


public class BribingFriends {
    public static void main(String[] args) throws IOException {
        // System.out.println("START");
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        int n = Integer.parseInt(st.nextToken());
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        Friend[] friends = new Friend[n];
        for(int i = 0; i < n; i++) friends[i]=new Friend();
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(bf.readLine());
            friends[i].popularity = Integer.parseInt(st.nextToken());
            friends[i].mooney = Integer.parseInt(st.nextToken());
            friends[i].icecream = Integer.parseInt(st.nextToken());
        }
        Arrays.sort(friends);
        //System.out.println(Arrays.toString(friends));
        HashMap<Pair, Integer> maxPopularity = new HashMap<>();
        maxPopularity.put(new Pair(a, b), 0);

        for (int i = 0; i < n; i++) {
            HashMap<Pair, Integer> tempMaxPop = new HashMap<>();
            for (Map.Entry<Pair, Integer> entry : maxPopularity.entrySet()) {
                Pair p = entry.getKey();
                int value = entry.getValue();
                if (p.second > friends[i].icecream * friends[i].mooney) {
                    Pair temp = new Pair(p.first, p.second - friends[i].icecream * friends[i].mooney);
                    tempMaxPop.put(temp, Math.max(tempMaxPop.getOrDefault(temp, 0), value + friends[i].popularity));
                } else {
                    int costDecreased = p.second / friends[i].icecream;
                    Pair temp = new Pair(p.first - friends[i].mooney + costDecreased, 0);
                    if (temp.first >= 0) {
                        tempMaxPop.put(temp, Math.max(tempMaxPop.getOrDefault(temp, 0), value + friends[i].popularity));
                    }
                }
            }

            for (Map.Entry<Pair, Integer> entry : tempMaxPop.entrySet()) {
                maxPopularity.put(entry.getKey(), Math.max(maxPopularity.getOrDefault(entry.getKey(), 0), entry.getValue()));
            }
        }

        int ans = 0;
        for (int value : maxPopularity.values()) {
            ans = Math.max(ans, value);
        }

        System.out.println(ans);
        // System.out.println("END");
    }
    static class Friend implements Comparable<Friend>{
        int popularity, mooney, icecream;
        public int compareTo(Friend other) {
            return this.icecream-other.icecream;
        }
        public String toString() {
            return popularity+" "+mooney+" "+icecream;
        }
    }

    static class Pair {
        int first;
        int second;

        public Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public boolean equals(Object other) {
            if (other == null || getClass() != other.getClass())
                return false;
            Pair another = (Pair) other;
            return this.first==another.first&&this.second==another.second;
        }

        @Override
        public int hashCode() {
            return Objects.hash(first, second);
        }
    }
}