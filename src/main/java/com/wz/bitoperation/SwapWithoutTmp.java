/**
 * <p>Title: SwapWithoutTmp</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/12/18</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.bitoperation;

/**
 * <p>不用额外变量交换两个整数的值</p>
 * <p>
 *     假设a异或b的结果记为c，c就是a整数位和b整数位的所有不同信息。如a=4=100，b=3=011，a^b=c=000；
 *     a异或c的结果就是b。如a=4=100，c=000，a^c=011=3=b；
 *     b异或c的结果就是a。如b=3=011，c=000，b^c=100=4=a。
 *     a=a^b之后，a变成c，b不变；
 *     b=a^b之后，a依然是c，b变成了a；
 *     a=a^b之后，a变成b，b依然是a。
 * </p>
 *
 * @author wangzi
 */
public class SwapWithoutTmp {

    public static void main(String[] args) {
        int a = 16;
        int b = 17;
        System.out.println("before, a: " + a + ",b: " + b);
        a = a ^ b;
        b = a ^ b;
        a = a ^ b;
        System.out.println("after, a: " + a + ",b: " + b);
    }
}
