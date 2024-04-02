import java.io.*;
import java.util.*;
public class FindAndReplaceSilver {
    public static void main(String[] args) throws Exception {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(bf.readLine());
        for(int i = 0; i < t; i++) {
            String before = bf.readLine();
            String after = bf.readLine();
            if(before.equals(after)) {
                System.out.println(0);
                continue;
            }
            int n = before.length();
            int[] turnInto = new int[52];
            HashSet<Integer>[] turnFrom = new HashSet[52];
            Arrays.fill(turnInto, -1);
            for(int j = 0; j < 52; j++) turnFrom[j]=new HashSet<>();
            boolean possible = true;

            for(int j = 0; j < n; j++) {
                int a = charToInt(before.charAt(j));
                int b = charToInt(after.charAt(j));
                if(turnInto[a]!=-1&&turnInto[a]!=b) {
                    possible=false;
                    break;
                }
                turnInto[a]=b;
                turnFrom[b].add(a);
            }
            int ans=0;
            boolean possible2=false;
            for(HashSet<Integer> h: turnFrom) {
                if(h.isEmpty()) {
                    possible2=true;
                    break;
                }
            }
            if(!possible||!possible2) {
                System.out.println(-1);
                continue;
            }
            boolean[] visited = new boolean[52];
            for(int j = 0; j < 52; j++) {
                if(turnInto[j]!=-1&&turnInto[j]!=j) {
                    ans++;
                }
            }
            for(int j = 0; j < 52; j++) {
                if(visited[j]) {
                    continue;
                }
                boolean[] temp = new boolean[52];
                int x = j;
                boolean noLoop = false;
                while(!visited[x]) {
                    visited[x]=temp[x]=true;
                    if(turnInto[x]==-1||turnInto[x]==x) {
                        noLoop=true;
                        break;
                    }
                    x=turnInto[x];
                }
                if(!noLoop&&temp[x]) {
                    int y = x;
                    boolean noNeed = false;
                    if(turnFrom[x].size()>1) {
                        noNeed=true;
                    }
                    x=turnInto[x];
                    while(x!=y) {
                        if(turnFrom[x].size()>1) {
                            noNeed=true;
                            break;
                        }
                        x=turnInto[x];
                    }
                    if(noNeed==false) {
                        ans++;
                    }
                }
            }
            System.out.println(ans);
        }
    }
    public static int charToInt(char c) {
        if(c-'A'<26) {
            return c - 'A';
        }
        return c-'a'+26;
    }
}
