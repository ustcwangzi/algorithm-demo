/**
 * <p>Title: ThrowChessPieces</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/2/19</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.others;

/**
 * <p>丢棋子问题</p>
 * <p>
 *     一座大楼有0～N层，地面算作第0层，最高的一层为第N层，已知棋子从第0层掉落肯定不会摔碎，从第i层掉落可能会摔碎，也可能不会摔碎(1<=i<=N)，
 *     给定整数N作为楼层数，再给定整数K作为棋子数，返回如果想找到棋子不会摔碎的最高层数，即使在最差的情况下扔的最少次数。一次只能扔一个棋子。
 *     举例：
 *        N=10，K=1
 *        返回10，因为只有1棵棋子，所以不得不从第1层开始一直试到第10层，在最差的情况下，即第10层是不会摔坏的最高层，最少也要扔10次。
 *        N=3，K=2
 *        返回2，先在2层扔1棵棋子，如果碎了，试第1层，如果没碎，试第3层。
 *        N=105，K=2
 *        返回14，第一个棋子先在14层扔，碎了则用仅存的一个棋子试1～13层；
 *        若没碎，第一个棋子继续在27层扔，碎了则用仅存的一个棋子试15～26层；
 *        若没碎，第一个棋子继续在39层扔，碎了则用仅存的一个棋子试28～38层；
 *        若没碎，第一个棋子继续在50层扔，碎了则用仅存的一个棋子试40～49层；
 *        若没碎，第一个棋子继续在60层扔，碎了则用仅存的一个棋子试51～59层；
 *        若没碎，第一个棋子继续在69层扔，碎了则用仅存的一个棋子试61～68层；
 *        若没碎，第一个棋子继续在77层扔，碎了则用仅存的一个棋子试70～76层；
 *        若没碎，第一个棋子继续在84层扔，碎了则用仅存的一个棋子试78～83层；
 *        若没碎，第一个棋子继续在90层扔，碎了则用仅存的一个棋子试85～89层；
 *        若没碎，第一个棋子继续在95层扔，碎了则用仅存的一个棋子试91～94层；
 *        若没碎，第一个棋子继续在99层扔，碎了则用仅存的一个棋子试96～98层；
 *        若没碎，第一个棋子继续在102层扔，碎了则用仅存的一个棋子试100、101层；
 *        若没碎，第一个棋子继续在104层扔，碎了则用仅存的一个棋子试103层；
 *        若没碎，第一个棋子继续在105层扔，若到这一步还没碎，那么105便是结果。
 *     解决方案一：
 *        暴力递归。假设递归函数P(N, K)的返回值是N层楼有K个棋子在最差情况下扔的最少次数。
 *        1、如果N == 0，也就是楼层只有第0层，那么不用试，棋子肯定不会摔碎，即P(0, K) = 0。
 *        2、如果K == 1，也就是楼层有N层，但是只有一个棋子，这时只能从一层开始一层一层往上试，最坏情况下要一直试到第N层，即P(N, 1) = N。
 *        3、对于一般情况(N>0, k>1)，我们需要考虑第一个棋子从哪层开始扔，如果第1个棋子从第i层开始扔，有两种情况：
 *        3.1、碎了，此时只需要考虑i层以下的楼层，因此问题就变成了还剩下i-1层楼，还剩下K-1个棋子，所以总步数为1 + P(i-1, K-1)；
 *        3.2、没碎，此时只需要考虑i层以上的楼层，因此问题就变成了还剩下N-i层楼，仍有K个棋子，所以总步数为1 + P(N-i, K)。
 *        3.1和3.2中哪个情况最差，就选哪一种情况，即最后取值为max{P(i-1, K-1), P(N-i, K)} + 1。
 *        那么，第一个棋子应该从哪层开始扔会使得所扔的次数最少？依次从1层试到N层，选择最终步数最少的哪种情况即可。
 *     解决方案二：
 *        动态规划。通过方案一的分析可知，P(N, K)过程依赖P(0...N-1, K-1)和P(0...N-1, K)。
 *        所以可以用填表格的方式优化暴力递归，减少递归重复计算，如下所示：
 *        dp[0][K] = 0, dp[N][1] = N, dp[N][K] = min{max{dp[i-1][K-1], dp[N-i][k]}(1<=i<=N)} + 1。
 *     解决方案三：
 *        动态规划的空间压缩。分析方案二可知，dp[N][K]只需要它左边的数据dp[0...N-1][K-1]和它上面的数据dp[0...N-1][K]。
 *        所以可以使用两个数组不停复用的方式替换掉二维数组。
 *     解决方案四：
 *        利用 “四边形不等式”进行优化。
 *        1、如果已经求出了k+1个棋子在解决n层楼时的最少步骤为dp[n][k+1]，假设此时第一个棋子在第m层扔最终导致了最优解，
 *           那么在求k个棋子解决n层楼时，第一个棋子不需要再尝试m以上的楼层。
 *           例如，3个棋子在解决100层楼时，第一个棋子扔在37层时最终导致了最优解，那么2个棋子在解决100层楼时，第一个棋子不需要再试37层以上。
 *        2、如果已经求出了k个棋子在解决n层楼时的最少步骤为dp[n][k]，假设此时第一个棋子在第m层扔最终导致了最优解，
 *           那么在求k个棋子解决n+1层楼时，第一个棋子不需要再尝试m以下的楼层。
 *           例如，2个棋子在解决10层楼时，第一个棋子扔在4层时最终导致了最优解，那么2个棋子在解决11层楼时，第一个棋子不需要再试4层以下。
 *        也就是说，动态规划表中两个参数分别为棋子数和楼数，楼数变多以后，第一个棋子的尝试楼层的下限是可以确定的。
 *        棋子变少之后，第一个棋子的尝试楼层的上限是可以确定。这样就省去了很多无效的枚举过程。
 *     解决方案五：
 *        最优解。以上解决方案解决的都是N层楼有K个棋子最少扔多少次，现在反过来看K个棋子如果扔M次，最多能够解决多少层楼。
 *        根据上面实现的函数可以生成下表：
 *            0   1   2   3   4   5   6   7   8   9   10  -> 次数
 *        1   0   1   2   3   4   5   6   7   8   9   10
 *        2   0   1   3   6   10  15  21  28  36  45  55
 *        3   0   1   3   7   14  25  41  63  92  129 175
 *        4   0   1   3   7   15  30  56  98  162 255 385
 *        5   0   1   3   7   15  31  62  119 218 381 637
 *        |
 *        棋子
 *        map[i][j]表示i个棋子扔j次最多搞定的楼层。可以发现第一排的值从左到右一次是1，2，3，...，第一列都为0，
 *        其他位置map[i][j] == map[i][j-1] + map[i-1][j-1] + 1
 *        假设i个棋子扔j次最多搞定m层楼，搞定最多说明每次扔的位置都是最优的且棋子够用，假设第一个棋子扔在a层楼是最优的，
 *        1、如果第一个棋子碎了，那就向下，看i-1个棋子扔j-1次最多搞定多少楼层；
 *        2、如果第一个棋子没碎，那就向上，看i个棋子扔j-1次最多搞定多少楼层；
 *        3、a层楼本身也是被搞定的1层。
 *        1、2、3的总楼数就是i个棋子扔j次最多搞定的楼数，也就对应于上面的map表格。
 *        原始问题可以用map表得到很好的解决，比如相求5个棋子搞定200层楼需要扔多少次，map[5][8]==218，是第5航的值首次超过200，
 *        可知5个棋子搞定200层楼最少扔8次。另外，N层楼完全用二分的方式扔logN+1次就可以确定会碎的最低楼层，
 *        因此当K > logN+1时，直接返回logN+1即可。
 * </p>
 * <p>
 *     方案一时间复杂度为O(N!)，方案二和方案二的时间复杂度均为O(k*N^2)，方案四时间复杂度为O(N^2)
 *     方案五，如果棋子数为K、楼层数为N、最终结果为M次，那么时间复杂度为O(K*M)，棋子数大于logN+1时，时间复杂度为O(logN)
 * </p>
 *
 * @author wangzi
 */
public class ThrowChessPieces {

    /**
     * 暴力递归
     */
    public static int solutionOne(int n, int k) {
        if (n < 1 || k < 1) {
            return 0;
        }
        return processOne(n, k);
    }

    private static int processOne(int n, int k) {
        if (n == 0) {
            return 0;
        }
        if (k == 1) {
            return n;
        }
        int min = Integer.MAX_VALUE;
        for (int i = 1; i < n + 1; i++) {
            // 碎了，i层以上不用再试，剩下i-1层楼和k-1个棋子；没碎，i层以下不用再试，剩下N-i层楼和k个棋子
            min = Math.min(min, Math.max(processOne(i - 1, k - 1), processOne(n - i, k)));
        }
        return min + 1;
    }

    /**
     * 动态规划
     */
    public static int solutionTwo(int n, int k) {
        if (n < 1 || k < 1) {
            return 0;
        }
        if (k == 1) {
            return n;
        }

        int[][] dp = new int[n + 1][k + 1];
        // dp[N][1]=N，只使用一个棋子
        for (int i = 1; i < dp.length; i++) {
            dp[i][1] = i;
        }
        // i层j个棋子时的次数
        for (int i = 1; i < dp.length; i++) {
            for (int j = 2; j < dp[0].length; j++) {
                int min = Integer.MAX_VALUE;
                // 1～i层逐个尝试最优解
                for (int h = 1; h < i + 1; h++) {
                    // 碎了，h层以上不用再试，剩下h-1层楼和j-1个棋子；没碎，h层以下不用再试，剩下i-h层楼和j个棋子
                    min = Math.min(min, Math.max(dp[h - 1][j - 1], dp[i - h][j]));
                }
                dp[i][j] = min + 1;
            }
        }
        return dp[n][k];
    }

    /**
     * 动态规划空间压缩
     */
    public static int solutionThree(int n, int k) {
        if (n < 1 || k < 1) {
            return 0;
        }
        if (k == 1) {
            return n;
        }

        int[] preArray = new int[n + 1];
        int[] curArray = new int[n + 1];
        // 只使用一个棋子
        for (int i = 1; i < curArray.length; i++) {
            curArray[i] = i;
        }

        for (int i = 1; i < k; i++) {
            int[] tmp = preArray;
            preArray = curArray;
            curArray = tmp;
            for (int j = 1; j < curArray.length; j++) {
                int min = Integer.MAX_VALUE;
                for (int h = 1; h < j + 1; h++) {
                    // dp[N][K]只依赖了左边的数据dp[0...N-1][K-1]和它上面的数据dp[0...N-1][K]
                    min = Math.min(min, Math.max(preArray[h - 1], curArray[j - h]));
                }
                curArray[j] = min + 1;
            }
        }
        return curArray[n];
    }

    /**
     * 四边形不等式
     */
    public static int solutionFour(int n, int k) {
        if (n < 1 || k < 1) {
            return 0;
        }
        if (k == 1) {
            return n;
        }

        int[][] dp = new int[n + 1][k + 1];
        // 只使用一个棋子
        for (int i = 1; i != dp.length; i++) {
            dp[i][1] = i;
        }

        // 最优解
        int[] levels = new int[k + 1];
        // 只有一层
        for (int i = 1; i != dp[0].length; i++) {
            dp[1][i] = 1;
            levels[i] = 1;
        }

        // i层j个棋子时的次数
        for (int i = 2; i < n + 1; i++) {
            for (int j = k; j > 1; j--) {
                int min = Integer.MAX_VALUE;
                // 楼数变多以后，第一个棋子尝试楼层的下限可以确定
                int minEnum = levels[j];
                // 棋子变少之后，第一个棋子尝试楼层的上限可以确定
                // 首次在中间楼层开始，后面取上次的最优解
                int maxEnum = j == k ? i / 2 + 1 : levels[j + 1];
                // minEnum～maxEnum层逐个尝试最优解
                for (int h = minEnum; h < maxEnum + 1; h++) {
                    int cur = Math.max(dp[h - 1][j - 1], dp[i - h][j]);
                    if (cur <= min) {
                        min = cur;
                        levels[j] = h;
                    }
                }
                dp[i][j] = min + 1;
            }
        }
        return dp[n][k];
    }

    /**
     * 最优解，i个棋子最多解决的楼层数
     */
    public static int solutionFive(int n, int k) {
        if (n < 1 || k < 1) {
            return 0;
        }
        // 完全二分方式所需次数
        int bsTimes = log2N(n) + 1;
        if (k >= bsTimes) {
            return bsTimes;
        }

        // i个棋子最多搞定的楼层数
        int[] dp = new int[k];
        int result = 0;
        while (true) {
            result++;
            // dp[i-1]
            int previous = 0;
            for (int i = 0; i < dp.length; i++) {
                int tmp = dp[i];
                dp[i] = dp[i] + previous + 1;
                previous = tmp;
                if (dp[i] >= n) {
                    return result;
                }
            }
        }
    }

    private static int log2N(int n) {
        int result = -1;
        while (n != 0) {
            result++;
            n >>>= 1;
        }
        return result;
    }

    public static void main(String[] args) {
        int n = 21, k = 2;
        System.out.println(solutionOne(n, k));
        System.out.println(solutionTwo(n, k));
        System.out.println(solutionThree(n, k));
        System.out.println(solutionFour(n, k));
        System.out.println(solutionFive(n, k));

        n = 105;
        System.out.println(solutionTwo(n, k));
        System.out.println(solutionThree(n, k));
        System.out.println(solutionFour(n, k));
        System.out.println(solutionFive(n, k));
    }
}
