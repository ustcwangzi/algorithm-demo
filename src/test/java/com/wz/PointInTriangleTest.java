/**
 * <p>Title: PointInTriangleTest</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/3/30</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz;

import com.wz.others.PointInTriangle;

/**
 * <p>判断一个点是否在三角形内部，向量差乘法</p>
 *
 * @author wangzi
 */
public class PointInTriangleTest {
    private static boolean solution(double x1, double y1, double x2, double y2,
                                    double x3, double y3, double x, double y) {
        // AP * AB
        double productPAB = crossProduct(x - x1, y - y1, x2 - x1, y2 - y1);
        // BP * BC
        double productPBC = crossProduct(x - x2, y - y2, x3 - x2, y3 - y2);
        // CP * CA
        double productPCA = crossProduct(x - x3, y - y3, x1 - x3, y1 - y3);
        return (productPAB >= 0 && productPBC >= 0 && productPCA >= 0)
                || (productPAB < 0 && productPBC < 0 && productPCA < 0);
    }

    /**
     * 向量差乘
     */
    private static double crossProduct(double x1, double y1, double x2, double y2) {
        return x1 * y2 - x2 * y1;
    }

    public static void main(String[] args) {
        double x1 = -5;
        double y1 = 0;
        double x2 = 0;
        double y2 = 8;
        double x3 = 5;
        double y3 = 0;

        int times = 10;
        boolean result = true;
        for (int i = 0; i < times; i++) {
            for (int j = 0; j < times; j++) {
                if (solution(x1, y1, x2, y2, x3, y3, i, j) != PointInTriangle.isInsideTwo(x1, y1, x2, y2, x3, y3, i, j)) {
                    result = false;
                    System.out.println("Error, x:" + i + ", y:" + j);
                }
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
