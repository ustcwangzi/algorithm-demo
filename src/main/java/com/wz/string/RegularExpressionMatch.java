/**
 * <p>Title: RegularExpressionMatch</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/12/16</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.string;

/**
 * <p>字符串匹配问题</p>
 * <p>
 *     给顶一个字符串str，其中不含有'.'和'*'，在给定一个exp，其中可以含有'.'或'*'，'*'不能是exp的首字符，且任意两个'*'字符不相邻。
 *     exp中的'.'代表任意一个字符，exp中的'*'表示'*'的前一个字符可以有0个或多个。请判断str能否被exp匹配。
 *     例如：
 *     str="abc"，exp="abc"，返回true；
 *     str="abc"，exp="a.c"，返回true；
 *     str="abcd"，exp=".*"，当exp是"...."时可以匹配，返回true；
 *     str=""，exp="..*"，".*"之前的'.'不受'*'影响，str至少要有一个字符才能匹配，返回false。
 *     解决方案一：
 *        递归，process(str,exp,strIndex,expIndex)代表str[strIndex...strLen]能否被exp[expIndex,expLen]匹配
 *        1、expIndex是exp的结束位置，如果strIndex也是str的结束位置，返回true，因为""可以被""匹配，否则返回false
 *        2、expIndex的下一个字符不是'*'
 *        2.1、exp[expIndex]与str[strIndex]匹配，继续看后续字符，即process(str,exp,strIndex+1,expIndex+1)
 *        2.1、exp[expIndex]与str[strIndex]不匹配，直接返回false
 *        3、expIndex的下一个字符是'*'
 *        3.1、exp[expIndex]与str[strIndex]不匹配，只能让exp[expIndex...expIndex+1]为""才行，即exp[expIndex]出现次数为0，
 *             然后考查process(str,exp,strIndex,expIndex+2)
 *             如str[strIndex...strLen]为"bXXX"，exp[expIndex...expLen]为"a*YYY"，'a'与'b'不匹配，只能让'a'出现的次数为0，
 *             然后继续看"bXXX"与"YYY"能否匹配
 *        3.2、exp[expIndex]与str[strIndex]匹配，假设str[strIndex...strLen]为"aaaXXX"，exp[expIndex...expLen]为"a*YYY"
 *             如果令"a"与"a*"匹配，且有"aaXXX"与"YYY"匹配，返回true；
 *             如果令"aa"与"a*"匹配，且有"aXXX"与"YYY"匹配，返回true；
 *             如果令"aaa"与"a*"匹配，且有"XXX"与"YYY"匹配，返回true；
 *             即exp[expIndex...expIndex+1]如果能匹配str后续很多位置的时候，只要一个返回true，就可以直接返回true。
 *     解决方案二：
 *        动态规划，方案一中的递归过程中str和exp是始终不变的，而且process(strIndex,expIndex)总是依赖于
 *        process(strIndex+1,expIndex+1)或者process(strIndex+k,expIndex+2)，(k>=0)，可用二维数组dp[i][j]表示这些值，
 *        dp[i][j]依赖于dp[i+1][j+1]或者dp[i+k][j+2)，(k>=0)，所以只要从二维数组右下角开始，从右到左、从下到上计算每个值即可。
 *        1、dp[strLen][...]，dp最后一行，对于dp[strLen][expLen]，str已结束，exp已结束，""可以匹配""，dp[strLen][expLen]=true，
 *           其他dp[strLen][j]表示str已结束，exp未结束，exp剩下的字符串exp[j...expLen-1]只能是""才可以，与方案一中3.1类似，
 *           需要exp[j...expLen-1]只能是反复出现"X*"的形式，如果是则能匹配""，否则不能匹配
 *        2、dp[0...strLen-1][expLen]，dp最后一列，str未结束，exp已结束，无法匹配，返回false
 *        3、dp[0...strLen-1][expLen-1]，dp倒数第二列，exp还剩下一个字符，str还剩一个或多个字符，很明显str剩下多个字符时无法匹配，
 *           只看一个字符的情况，如果str[strLen-1]与exp[expLen-1]能够匹配，返回true，否则false
 *        4、dp[i][j]依赖于dp[i+1][j+1]或者dp[i+k][j+2)，(k>=0)，所以在单独计算完最后一行、最后一列和倒数第二列之后，
 *           剩下的位置从右到左、再从下到上计算即可
 *        时间复杂度为O(N*N*M)，空间复杂度为O(N*M)，N为str长度，M为exp长度
 * </p>
 *
 * @author wangzi
 */
public class RegularExpressionMatch {

    public static boolean isMatch(String str, String exp) {
        if (str == null || exp == null) {
            return false;
        }
        char[] strArray = str.toCharArray();
        char[] expArray = exp.toCharArray();
        return isValid(strArray, expArray) && process(strArray, expArray, 0, 0);
    }

    private static boolean process(char[] str, char[] exp, int strIndex, int expIndex) {
        if (expIndex == exp.length) {
            return strIndex == str.length;
        }
        if (expIndex + 1 == exp.length || exp[expIndex + 1] != '*') {
            return strIndex != str.length && (exp[expIndex] == str[strIndex] || exp[expIndex] == '.')
                    && process(str, exp, strIndex + 1, expIndex + 1);
        }
        while (strIndex != str.length && (exp[expIndex] == str[strIndex] || exp[expIndex] == '.')) {
            if (process(str, exp, strIndex, expIndex + 2)) {
                return true;
            }
            strIndex++;
        }
        return process(str, exp, strIndex, expIndex + 2);
    }

    private static boolean isValid(char[] str, char[] exp) {
        // str中不能含有'*'或'.'
        for (char s : str) {
            if (s == '*' || s == '.') {
                return false;
            }
        }
        // exp首字符不能是'*'，任意两个'*'不能相邻
        for (int i = 0; i < exp.length; i++) {
            if (exp[i] == '*' && (i == 0 || exp[i - 1] == '*')) {
                return false;
            }
        }
        return true;
    }

    public static boolean isMatchDP(String str, String exp) {
        if (str == null || exp == null) {
            return false;
        }

        char[] strArray = str.toCharArray();
        char[] expArray = exp.toCharArray();
        if (!isValid(strArray, expArray)) {
            return false;
        }
        // 动态规划矩阵
        boolean[][] dp = initDPMap(strArray, expArray);
        for (int i = strArray.length - 1; i > -1; i--) {
            for (int j = expArray.length - 2; j > -1; j--) {
                if (expArray[j + 1] != '*') {
                    dp[i][j] = (strArray[i] == expArray[j] || expArray[j] == '.')
                            && dp[i + 1][j + 1];
                } else {
                    int strIndex = i;
                    while (strIndex != strArray.length && (strArray[strIndex] == expArray[j] || expArray[j] == '.')) {
                        if (dp[strIndex][j + 2]) {
                            dp[i][j] = true;
                            break;
                        }
                        strIndex++;
                    }
                    if (!dp[i][j]) {
                        dp[i][j] = dp[strIndex][j + 2];
                    }
                }
            }
        }
        return dp[0][0];
    }

    private static boolean[][] initDPMap(char[] str, char[] exp) {
        int strLen = str.length;
        int expLen = exp.length;
        boolean[][] dp = new boolean[strLen + 1][expLen + 1];
        // str和exp都已经结束，""可以匹配""
        dp[strLen][expLen] = true;
        //
        // dp最后一行，str已经结束，exp还未结束，只有重复出现"X*"可以匹配""
        for (int j = expLen - 2; j > -1; j = j - 2) {
            if (exp[j] != '*' && exp[j + 1] == '*') {
                dp[strLen][j] = true;
            } else {
                break;
            }
        }
        // dp倒数第二列，str还剩一个或多个字符，exp还剩一个字符
        if (strLen > 0 && expLen > 0) {
            if (exp[expLen - 1] == '.' || str[strLen - 1] == exp[expLen - 1]) {
                dp[strLen - 1][expLen - 1] = true;
            }
        }
        return dp;
    }

    public static void main(String[] args) {
        String str = "abcccdefg";
        String exp = "ab.*d.*e.*";
        System.out.println(isMatch(str, exp));
        System.out.println(isMatchDP(str, exp));
    }
}
