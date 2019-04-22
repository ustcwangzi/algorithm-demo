package com.wz;

/**
 * <p>不使用额外变量交换两个正数的值</p>
 *
 * @author wangzi
 */
public class SwapWithoutTmpTest {

    public static void main(String[] args) {
        int a = 5, b = 6;
        // a变为c，b不变
        a = a ^ b;
        // b变为a，a还是c
        b = a ^ b;
        // a变为b，b还是a
        a = a ^ b;
        System.out.println("a: " + a + ", b: " + b);
    }
}
