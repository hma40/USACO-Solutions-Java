import java.io.*;
import java.sql.SQLOutput;
import java.util.*;
public class CowmpetencyGold {
    static long mod = 1000000007;
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(), q = sc.nextInt(), c = sc.nextInt();

        ArrayList<Pair> pairs = new ArrayList<>();
        for(int i = 0; i < q; i++) {
            pairs.add(new Pair(sc.nextInt(), sc.nextInt()));
        }
        Collections.sort(pairs);
        for(int i = 1; i < pairs.size(); i++) {
            if(pairs.get(i).h==pairs.get(i-1).h) {
                pairs.remove(i-1);
                i--;
                q--;
            }
        }
        //System.out.println(pairs);
        //System.out.println(Arrays.toString(pairs));
        long[][] primComb = new long[q][c+1];
        long[][] combinations = new long[q][c+1];
        long[][] prefSum = new long[q][c+1];
        for(int i = 1; i <= c; i++) {
            primComb[0][i]=(BinExp(i-1,pairs.get(0).a)-BinExp(i-2, pairs.get(0).a))*BinExp(i-1, pairs.get(0).h-pairs.get(0).a-1);
            primComb[0][i]%=mod;
            primComb[0][i]+=mod;
            primComb[0][i]%=mod;
        }
        for(int i = 2; i <= c; i++) {
            combinations[0][i]=combinations[0][i-1]+primComb[0][i];
            combinations[0][i]%=mod;
            prefSum[0][i]=prefSum[0][i-1]+combinations[0][i];
            prefSum[0][i]%=mod;
        }
        for(int i = 1; i < q; i++) {
            for(int j = 2; j <= c; j++) {
                long first = BinExp(j-1,pairs.get(i).a-pairs.get(i-1).h)*combinations[i-1][j-1]%mod;
                long second = prefSum[i-1][j-2]*(BinExp(j-1,pairs.get(i).a-pairs.get(i-1).h)-BinExp(j-2,pairs.get(i).a-pairs.get(i-1).h))%mod;
                primComb[i][j]=BinExp(j-1,pairs.get(i).h-pairs.get(i).a-1)*(first+second)%mod;
                primComb[i][j]+=mod;
                primComb[i][j]%=mod;
                combinations[i][j]=combinations[i][j-1]+primComb[i][j];
                combinations[i][j]%=mod;
                prefSum[i][j]=prefSum[i][j-1]+combinations[i][j];
                prefSum[i][j]%=mod;
            }
        }
        long ans = prefSum[q-1][c];
        ans*=BinExp(c,n-pairs.get(q-1).h);
        ans%=mod;
        ans+=mod;
        ans%=mod;
        if(ast(ans)) return;
        System.out.println(ans);
    }
    static boolean ast(long value) {
        return value<0||value>=mod;
    }
    static long BinExp(long base, long exponent) {
        if(base<0) return 0;
        long ans = 1;
        while(exponent>0) {
            if(exponent%2==1) {
                ans*=base;
                ans%=mod;
            }
            base*=base;
            base%=mod;
            exponent/=2;
        }
        return ans;
    }
    static class Pair implements Comparable<Pair> {
        int a,h;
        public Pair(int a, int h) {
            this.a=a;
            this.h=h;
        }
        public int compareTo(Pair other) {
            if(h==other.h) {
                return -Integer.compare(a, other.a);
            }
            return Integer.compare(h, other.h);
        }
        public String toString() {
            return a+" "+h;
        }
    }
}