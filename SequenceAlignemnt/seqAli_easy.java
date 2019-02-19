import java.util.*;
import java.io.*;

class seqAli_easy {
    /*global variance*/
    int m, n, gap_pnt, cost = 0, num_match = 0;
    int[][] pnt;
    String X, Y;
    ArrayList<MatchPair> matcheList;

    /*Create the class to store the pairs*/
    public class MatchPair {
        int first;
        int second;

        public MatchPair(int fst, int sec) {
            this.first = fst;
            this.second = sec;
        }
        public MatchPair() {

        }
    }

    /*Method to get the data*/
    private int[][] input(){
//        BufferedReader reader = null;
        int lines_num = 26;
        int[][] pnt = new int[lines_num][lines_num];
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            /*First reader the first line and the next three lines*/
            String text = reader.readLine();
            String[] parts = text.split(" ");
            m = Integer.parseInt(parts[0]);
            n = Integer.parseInt(parts[1]);
            X = reader.readLine();
            Y = reader.readLine();
            text = reader.readLine();
            parts = text.split(" ");
            gap_pnt = Integer.parseInt(parts[0]);

            /*Get the penalty matrix*/
            for (int i = 0; i < lines_num; i++) {
                for (int j = 0; j < lines_num; j++){
                    text = reader.readLine();
                    String [] aList = text.split(" ");
                    pnt[i][j] = Integer.parseInt(aList[2]);
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pnt;
    }

    /**/
    int[] alignment(String xx, String yy) {
        int[][] dp = new int[m+1][n+1];
        int i, j;
        /*Initialize the path array*/
        for (i = 0; i <= m; i++)
            dp[i][0] = i * gap_pnt;
        for (i = 0; i <= n; i++)
            dp[0][i] = i * gap_pnt;
        /*Calculate the dp matrix*/
        for (j = 1; j <= m; j++) {
            for (i = 1; i <= n; i++) {
                char cx = xx.charAt(j - 1);
                char cy = yy.charAt(i - 1);
                int case1 = dp[j - 1][i - 1] + pnt[cx - 'a'][cy - 'a'];
                int case2 = dp[j - 1][i] + gap_pnt;
                int case3 = dp[j][i - 1] + gap_pnt;
                dp[j][i] =  Math.min(case1, Math.min(case2, case3));
            }
        }

        i = m;
        j = n;
        int[] res = new int[m+1];
        /*Track back to find the shortest path*/
        while (!(i == 0 || j == 0)) {
            char cx = xx.charAt(i - 1);
            char cy = yy.charAt(j - 1);
            if (dp[i][j] == dp[i -1][j - 1] + pnt[cx - 'a'][cy - 'a'] ) {
                res[i] = j;
                num_match++;
                i--;
                j--;

            } else if (dp[i][j] == dp[i -1][j] + gap_pnt) {
                i--;
            } else if (dp[i][j] == dp[i][j - 1] + gap_pnt) {
                j--;
            }

        }
        cost = dp[m][n];
        return res;
    }



    public seqAli_easy() {
        pnt = input();
        matcheList = new ArrayList<MatchPair>();
        int[] res = alignment(X, Y);
        try{
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out));
            writer.println(cost);
            writer.println(num_match);
            String format = "%d %d\n";
            for (int i = 0; i <= m; i++) {
                if (res[i] != 0) {
                    writer.printf(format, i, res[i]);
                }
            }
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        /*Standard input to get the data*/
        new seqAli_easy();

    }
}
