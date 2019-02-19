import java.util.*;
import java.io.*;

public class seqAlignment {
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
            System.out.println("Input Method finished!!!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pnt;
    }

    /**/
    void alignment(String xx, String yy) {
        int x_len = xx.length();
        int y_len = yy.length();
        int[][] dp = new int[x_len+1][n+1];
        int i, j;
        /*Initialize the path array*/
        for (i = 0; i <= x_len; i++)
            dp[i][0] = i * gap_pnt;
        for (i = 0; i <= y_len; i++)
            dp[0][i] = i * gap_pnt;

        System.out.println("Initialize the dp matrix finished!!!");

        /*Calculate the dp matrix*/
        for (j = 1; j <= x_len; j++) {
            for (i = 1; i <= y_len; i++) {
                char cx = xx.charAt(j - 1);
                char cy = yy.charAt(i - 1);
                int case1 = dp[j - 1][i - 1] + pnt[cx - 'a'][cy - 'a'];
                int case2 = dp[j - 1][i] + gap_pnt;
                int case3 = dp[j][i - 1] + gap_pnt;
                dp[j][i] =  Math.min(case1, Math.min(case2, case3));
            }
            System.out.println("finish the  " + j + "  row!!!");
        }
        System.out.println("Finish dp calculate!!!");


        int[] res = new int[x_len+1];
/**********************************************************************************/
        for(i = 0; i <= x_len; i++) {
            for (j = 0; j <= y_len; j++) {
                System.out.print(dp[i][j] + "  ");
            }
            System.out.println();
        }
        System.out.println("Start while");
/*****************************************************************************/
        i = x_len;
        j = y_len;
        /*Track back to find the shortest path*/
        while (!(i == 0 || j == 0)) {
            char cx = xx.charAt(i - 1);
            char cy = yy.charAt(j - 1);
             if (dp[i][j] == dp[i -1][j - 1] + pnt[cx - 'a'][cy - 'a'] && dp[i - 1][j - 1] <= dp[i - 1][j] && dp[i - 1][j - 1] <= dp[i][j - 1]) {
                res[i] = j;
                num_match++;
                i--;
                j--;
            } else if (dp[i][j] == dp[i -1][j] + gap_pnt) {
                i--;
            } else if (dp[i][j] == dp[i][j - 1] + gap_pnt) {
                j--;
            }
//            System.out.println("i = " + i + " ,  j = " + j);
        }
         cost = dp[x_len][y_len];
        for(i = 0; i <= x_len; i++) {
            for (j = 0; j <= y_len; j++) {
                MatchPair pair = new MatchPair(i, res[i]);
                matcheList.add(pair);
            }
            System.out.println();
        }
    }

    /*Space Efficient Alignment*/
    int[][] spaceEA(String xx, String yy) {
        int x_len = xx.length();
        int y_len = yy.length();
        System.out.println("yy_length forward " + y_len + " String yy :" + yy);
        int[][] dpB = new int[x_len + 1][2];
        /*Initialize the first line*/
        for (int i = 0; i <= x_len; i++) {
            dpB[i][0] = i * gap_pnt;
        }
        for (int j = 1; j <= y_len; j++) {
            dpB[0][1] = j * gap_pnt;
            for (int i = 1; i <= x_len; i++) {
                char cx = xx.charAt(i - 1);
                char cy = yy.charAt(j - 1);
                int case1 = dpB[i - 1][0] + pnt[cx - 'a'][cy - 'a'];
                int case2 = dpB[i - 1][1] + gap_pnt;
                int case3 = dpB[i][0] + gap_pnt;
                dpB[i][1] = Math.min(case1, Math.min(case2, case3));
            }
            for (int k = 0; k <= x_len; k++) {
                dpB[k][0] = dpB[k][1];
            }
        }
        return dpB;
    }
    /*Backward Space Efficient Alignment*/
    int[][] backSEA(String xx, String yy) {
        int x_len = xx.length();
        int y_len = yy.length();
        System.out.println("yy_length " + y_len + "String : " + yy);
        int[][] dpB = new int[x_len + 1][2];
        /*Initialize the first line*/
        for (int i = x_len; i >= 0; i--) {
            dpB[i][0] = (x_len - i) * gap_pnt;
        }
        for (int i = 0; i <= x_len; i++)
            System.out.println(dpB[i][0]);

        for (int j = y_len - 1; j >= 0; j--) {
            dpB[y_len][1] = (y_len - j) * gap_pnt;
            for (int i = x_len - 1; i >= 0; i--) {
                char cx = xx.charAt(i);
                char cy = yy.charAt(j);
                int case1 = dpB[i + 1][0] + pnt[cx - 'a'][cy - 'a'];
                int case2 = dpB[i + 1][1] + gap_pnt;
                int case3 = dpB[i][0] + gap_pnt;
                dpB[i][1] = Math.min(case1, Math.min(case2, case3));
            }
            for (int k = 0; k <= x_len; k++) {
                dpB[k][0] = dpB[k][1];
            }
        }
        return dpB;
    }

    void divideConquer(String xx, String yy) {
        int xlen = xx.length();
        int ylen = yy.length();

         if ( xlen < 2 || ylen < 2) {
            alignment(xx, yy);
         }
         int[][] forward = spaceEA(xx, yy.substring(0, ylen / 2 + 1));
         int[][] back = backSEA(xx, yy.substring(ylen / 2, ylen));

         int q = 0;
         int min = Integer.MAX_VALUE;
         System.out.println("Forward");
        for (int i = 0; i <= xlen; i++) {
            System.out.print(forward[i][0] + " ");
        }
        System.out.println();
        System.out.println("Backward");
        for (int i = 0; i <= xlen; i++) {
            System.out.print(back[i][0] + " ");
        }


         for (int i = 0; i < xlen; i++) {
             if (forward[i][0] + back[i][0] < min){
                 min = forward[i][0] + back[i][0];
                 q = i + 1;
             }
         }
         System.out.println("\n q = " + q + " " + xx.charAt(q));


         divideConquer(xx.substring(1,q), yy.substring(1, ylen / 2));
         divideConquer(xx.substring(q + 1, ylen / 2), yy.substring(ylen + 1, ylen));
    }


    public seqAlignment() {
        pnt = input();
        matcheList = new ArrayList<MatchPair>();
//        alignment(X, Y);
        divideConquer(X, Y);

        try{
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out));
            writer.println(cost);
            writer.println(num_match);
            String format = "%d %d\n";
            for (int i = 0; i <= matcheList.size(); i++) {
                writer.printf(format, matcheList.get(i).first, matcheList.get(i).second);

            }
            writer.close();

         } catch (Exception e) {
             e.printStackTrace();
         }

    }

    public static void main(String[] args) {
    /*Standard input to get the data*/
        new seqAlignment();
    }
}
