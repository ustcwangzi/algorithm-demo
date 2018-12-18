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
