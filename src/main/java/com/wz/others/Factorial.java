/**
 * <p>Title: Factorial</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/15</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.others;

/**
 * <p>阶乘相关问题</p>
 * <p>
 *     问题一：
 *        给定一个非负整数N，求出N!末尾为0的数量。
 *     问题二：
 *        给定一个非负整数N，求出N!二进制表达式中最低位的1在哪个位置上，最右位置为位置0。
 *     问题一解答：
 *        方案一：
 *           N!末尾有多少个0可以转换为1,2,3,...,N-1,N，N的序列中一共有多少个因子5，这是因为阶乘过程中因子2的数目比因子5的数目多，
 *           所以不管有多少个因子5，都有足够的因子2与其相乘得到10。因此只要找出1～N所有数中，一共有多少个因子5就可以。
 *        方案二：
 *           方案一的时间复杂度为O(N*logN)，效率不高，可以对方案一进行优化，到达时间复杂度O(logN)。
 *           把1～N所有的数列出来：1,2,3,4,5,6,...,10...,15...,20...,25...,30...,35...,40...,45...,50...
 *           若每5个含有0个因子5的数(1,2,3,4,5)组成一组，这一组中的第5个数就含有5^1的因子(5)；
 *           若每5个含有1个因子5的数(5,10,15,20,25)组成一组，这一组中的第5个数就含有5^2的因子(25)；
 *           若每5个含有2个因子5的数(25,50,75,100,125)组成一组，这一组中的第5个数就含有5^3的因子(125)；
 *           若每5个含有i个因子5的数组成一组，这一组中的第5个数就含有5^i+1的因子...
 *           所以，如果把N!的结果中因子5的总个数记为Z，就可以得到：Z=N/5+N/(5^2)+N/(5^3)+...+N/(5^i)，直到5^i>N。
 *     问题二解答：
 *        方案一：
 *           最低位的1在哪个位置上，取决于1～N的数中一共有多少个因子2，因为只要出现一个因子2，最低位的1就会向左移动一位。
 *           所以，如果把N!的结果中因子2的总个数记为Z，就可以得到：Z=N/2+N/(2^2)+N/(2^3)+...+N/(2^i)，直到2^i>N。
 *        方案二：
 *           如果把N!的结果中因子2的总个数记为Z，把N的二进制中1的个数极为m，则有N/2+n/4+N/8+...=N-m，即Z=N-m。
 * </p>
 *
 * @author wangzi
 */
public class Factorial {

    public static int zeroNumOne(int num) {
        if (num < 0) {
            return 0;
        }

        int result = 0;
        int cur;
        for (int i = 5; i <= num; i++) {
            cur = i;
            while (cur % 5 == 0) {
                result++;
                cur /= 5;
            }
        }

        return result;
    }

    public static int zeroNumTwo(int num) {
        if (num < 0) {
            return 0;
        }

        int result = 0;
        while (num > 0) {
            result += num / 5;
            num /= 5;
        }

        return result;
    }

    public static int rightOneOne(int num) {
        if (num < 1) {
            return -1;
        }

        int result = 0;
        while (num > 0) {
            // 无符号右移，即除2
            num >>>= 1;
            result += num;
        }

        return result;
    }

    public static int rightOneTwo(int num) {
        if (num < 1) {
            return -1;
        }

        // num二进制中1的数量
        int oneNumbers = 0;
        int tmp = num;
        while (tmp > 0) {
            oneNumbers += (tmp & 1) != 0 ? 1 : 0;
            tmp >>>= 1;
        }

        return num - oneNumbers;
    }

    public static void main(String[] args) {
        int num = 1000000000;
        System.out.println(zeroNumOne(num));
        System.out.println(zeroNumTwo(num));
        System.out.println(rightOneOne(num));
        System.out.println(rightOneTwo(num));
    }
}
