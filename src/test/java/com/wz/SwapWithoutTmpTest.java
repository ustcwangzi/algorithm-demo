package com.wz;

public class SwapWithoutTmpTest {

    public static void main(String[] args) {
        int a = 5, b = 6;
        a = a ^ b;
        b = a ^ b;
        a = a ^ b;
        System.out.println("a: " + a + ", b: " + b);
    }
}
