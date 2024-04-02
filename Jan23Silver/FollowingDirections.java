import java.io.*;
import java.util.*;
public class FollowingDirections {
    static int[][] grid;
    static int[][] cowsPassing;
    static int n;
    static long sum=0;
    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(in.readLine());
        grid = new int[n+1][n+1];
        cowsPassing = new int[n+1][n+1];
        for(int i = 0; i < n+1; i++) {
            String line = in.readLine();
            String[] linesSeparated = line.split(" ");
            if(i==n) {
                for(int j = 0; j < n; j++) {
                    grid[i][j]=Integer.parseInt(linesSeparated[j]);
                }
            } else {
                for(int j = 0; j < n; j++) {
                    char c = linesSeparated[0].charAt(j);
                    grid[i][j]=(c=='R'?1:2);
                }
                grid[i][n]=Integer.parseInt(linesSeparated[1]);
            }
        }
        System.out.println(calculateSum());
        int Q = Integer.parseInt(in.readLine());

        for(int i = 0; i < Q; i++) {
            String[] a = in.readLine().split(" ");
            int[] pos = new int[]{Integer.parseInt(a[0]),Integer.parseInt(a[1])};
            pos[0]--;
            pos[1]--;
            System.out.println(updateSum(pos[0], pos[1]));
        }
    }
    static long updateSum(int x, int y) {
        int nx=x;
        int ny=y;
        while(nx!=n&&ny!=n) {
            if(nx!=x||ny!=y) {
                cowsPassing[nx][ny]-=cowsPassing[x][y];
            }
            if(grid[nx][ny]==1) {
                ny++;
            } else {
                nx++;
            }
        }
        sum-=grid[nx][ny]*cowsPassing[x][y];
        cowsPassing[nx][ny]-=cowsPassing[x][y];
        nx=x;
        ny=y;
        grid[nx][ny]=3-grid[nx][ny];
        while(nx!=n&&ny!=n) {
            if(nx!=x||ny!=y) {
                cowsPassing[nx][ny]+=cowsPassing[x][y];
            }
            if(grid[nx][ny]==1) {
                ny++;
            } else {
                nx++;
            }
        }
        sum+=grid[nx][ny]*cowsPassing[x][y];
        cowsPassing[nx][ny]+=cowsPassing[x][y];
        return sum;
    }
    static long calculateSum() {
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                cowsPassing[i][j]+=1;
                if(grid[i][j]==2) {
                    cowsPassing[i+1][j]+=cowsPassing[i][j];
                } else {
                    cowsPassing[i][j+1]+=cowsPassing[i][j];
                }
            }
        }
        for(int i = 0; i < n; i++) {
            sum+=cowsPassing[n][i]*grid[n][i];
            sum+=cowsPassing[i][n]*grid[i][n];
        }
        return sum;
    }
}
