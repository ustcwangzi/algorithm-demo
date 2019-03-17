/**
 * <p>Title: NumbersOfOne</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/26</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.others;

/**
 * <p>1至n中数字1出现的次数</p>
 * <p>
 *     给定一个整数n，求从1到n这n个整数的十进制表示中1出现的次数。
 *     例如，给定12，从1到12这些整数中包含1的数字有1、10、11和12，其中1一共出现了5次。
 *     解决方案一：
 *        逐个计算1～n的每一个数中1出现的次数，然后进行累加即可。
 *     解决方案二：
 *        求出每一位出现1的次数，然后进行累加。
 *        首先：
 *        从1至10，在它们的个位数中，1出现了1次；
 *        从1至100，在它们的十位数中，1都出现了10次；
 *        从1至1000，在它们的百位数中，1出现了100次；
 *        依次类推，从1至10^i，在它们的右数第i位，1出现了10^(i-1)次。
 *
 *        1、如果第i位(自右向左，从1开始标号)上的数字是0，则第i位可能出现1的次数由更高位决定(若没有高位，则视高位为0)，
 *           等于更高位数乘以当前位数的权重，即：(高位数) * 10^(i-1)；
 *           如203的十位包含1的情况为：10～19、110～119，共20个
 *        2、如果第i位上的数字为1，则第i位上出现1的次数不仅受更高位影响，还受低位影响(若没有低位，视低位为0)，
 *           等于更高位数乘以当前位数的权重，再加上低位数+1，即：(高位数) * 10^(i-1) + (低位数 + 1)；
 *           如213的十位包含1的情况为：10～19、110～119、210～213，共20+4个
 *        3、如果第i位上的数字大于1，则第i位上可能出现1的次数仅由更高位决定(若没有高位，视高位为0)，
 *           等于更高位数+1，然后乘以当前位数的权重，即：(高位数 + 1) * 10^(i-1)。
 *           如223的十位包含1的情况为：10～19、110～119、210～219，共30个
 *
 *        以上规律同样适用于1～n中X出现的次数：
 *        当计算右数第i位包含的X的个数时：
 *        1、取第i位左边(高位)的数字，乘以10^(i−1)，得到基础值a。
 *        2、取第i位数字，计算修正值：
 *        2.1、如果大于X，则结果为a+10^(i−1)。
 *        2.2、如果小于X，则结果为a。
 *        2.3、如果等X，则取第i位右边(低位)数字，设为b，最后结果为a+b+1。
 *
 *        以2013为例，说明以上计算1出现次数的过程：
 *        1、个位为3，1～2010中，包含201个10，因此1出现了201次，2011～2013中个位上出现1次1，
 *           因此个位上出现1的总次数为(201+1)*(10^(1-1)) = 202
 *        2、十位为1，1～2000中，包含20个100，因此1出现了20*10=200次，2001～2013中十位上出现1的次数为4，
 *           因此十位上出现1的总次数为20*(10^(2-1))+3+1 = 204
 *        3、百位为0，1～2000中，包含2个1000，因此1出现了2*100=200次，2001～2013中百位上出现1的次数为0，
 *           因此百位上出现1的总次数为2*(10^(3-1)) = 200
 *        4、千位为2，没有更高位，因此千位上出现1的次数为1000
 *        总次数为：202+204+200+1000=1606
 * </p>
 * <p>
 *     方案一时间复杂度为O(N*logN)，以10为底
 *     方案二时间复杂度为O(logN)，以10为底
 * </p>
 *
 * @author wangzi
 */
public class NumbersOfOne {

    public static int countOne(int number) {
        if (number < 1) {
            return 0;
        }
        int count = 0;
        // 每个数单独计算含有1的个数
        for (int i = 1; i < number + 1; i++) {
            count += getOneNumbers(i);
        }
        return count;
    }

    private static int getOneNumbers(int number) {
        int result = 0;
        while (number != 0) {
            if (number % 10 == 1) {
                result++;
            }
            number /= 10;
        }
        return result;
    }

    public static int countTwo(int number) {
        if (number < 1) {
            return 0;
        }
        int count = 0;
        int high = number, low, cur, remainder, i = 1;
        while (high != 0) {
            int power = (int) Math.pow(10, i);
            // 第i位的高位
            high = number / power;
            // 去除高位后的余数
            remainder = number % power;
            power = (int) Math.pow(10, i - 1);
            // 第i位
            cur = remainder / power;
            // 第i位的低位
            low = remainder % power;
            if (cur == 0) {
                count += high * power;
            } else if (cur == 1) {
                count += high * power + low + 1;
            } else {
                count += (high + 1) * power;
            }
            i++;
        }
        return count;
    }

    public static void main(String[] args) {
        int number = 87654321;

        System.out.println(countOne(number));
        System.out.println(countTwo(number));
    }
}
