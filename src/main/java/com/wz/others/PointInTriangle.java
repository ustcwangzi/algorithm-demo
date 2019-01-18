/**
 * <p>Title: PointInRectangle</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/18</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.others;

/**
 * <p>判断一个点是否在三角形内部</p>
 *
 * @author wangzi
 */
public class PointInTriangle {

    /**
     * 面积解法
     */
    public static boolean isInsideOne(double x1, double y1, double x2, double y2,
                                      double x3, double y3, double x, double y) {
        double areaOAB = getArea(x1, y1, x2, y2, x, y);
        double areaOAC = getArea(x1, y1, x3, y3, x, y);
        double areaOBC = getArea(x2, y2, x3, y3, x, y);
        double areaABC = getArea(x1, y1, x2, y2, x3, y3);
        return areaOAB + areaOAC + areaOBC - areaABC < 1e-10;
    }

    private static double getArea(double x1, double y1, double x2, double y2, double x3, double y3) {
        double lengthAB = getLength(x1, y1, x2, y2);
        double lengthAC = getLength(x1, y1, x3, y3);
        double lengthBC = getLength(x2, y2, x3, y3);
        double p = (lengthAB + lengthAC + lengthBC) / 2;
        return Math.sqrt(p * (p - lengthAB) * (p - lengthAC) * (p - lengthBC));
    }

    private static double getLength(double x1, double y1, double x2, double y2) {
        double a = Math.abs(x1 - x2);
        double b = Math.abs(y1 - y2);
        return Math.sqrt(a * a + b * b);
    }

    /**
     * 向量差乘
     */
    private static double crossProduct(double x1, double y1, double x2, double y2) {
        return x1 * y2 - x2 * y1;
    }

    /**
     * 向量解法
     */
    public static boolean isInsideTwo(double x1, double y1, double x2, double y2,
                                      double x3, double y3, double x, double y) {
        // AB * AO
        double productOAB = crossProduct(x2 - x1, y2 - y1, x - x1, y - y1);
        // BC * BO
        double productOBC = crossProduct(x3 - x2, y3 - y2, x - x2, y - y2);
        // CA * CO
        double productOAC = crossProduct(x1 - x3, y1 - y3, x - x3, y - y3);

        // 判断O是否在三个边的同侧
        return (productOAB <= 0 && productOBC <= 0 && productOAC <= 0)
                || (productOAB > 0 && productOBC > 0 && productOAC > 0);
    }

    public static void main(String[] args) {
        double x1 = -5;
        double y1 = 0;
        double x2 = 0;
        double y2 = 8;
        double x3 = 5;
        double y3 = 0;
        double x = 0;
        double y = 5;

        System.out.println(isInsideOne(x1, y1, x2, y2, x3, y3, x, y));
        System.out.println(isInsideTwo(x1, y1, x2, y2, x3, y3, x, y));
    }
}
