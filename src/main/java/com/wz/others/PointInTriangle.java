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
 * <p>
 *     在二维坐标系中，所有的值都是double类型，给定3个点代表的三角形，再给定一个点(x,y)，判断该点是否在三角形中。
 *     解决方案一：
 *        面积法。如果点O在三角形ABC的内部，那么有：面积ABC=面积OAB+面积OAC+面积OBC，根据海伦公式计算面积，然后进行比较即可。
 *        海伦公式见/resources/PointInTriangle.png。
 *     解决方案二：
 *        向量法。方案一中由于精度问题可能会导致判断出错，方案二不受精度影响。
 *        如果O在三角形的内部，假设如图/resources/PointInTriangle.png所示，那么当沿着ABCA的方向在三条边上行走时，
 *        会发现点O始终位于边AB、BC和CA的左侧；但ABC的坐标可能不是按照逆时针给出的，有可能按照顺时针给出的，沿着ABCA的方向在三条边上行走时，
 *        点O始终位于边AB、BC和CA的右侧。因此可以通过"点O"是否在三条边的同一侧来进行判断点是否在三角形内部。
 *        通过叉积可以判断出点在直线的哪一侧，AB×AP = |AB|*|AP|*sin∠PAB，P在AB的左边，则∠PAB在0°到180°之间，sin∠PAB > 0；
 *        P在AB右边时，则∠PAB在-180°到0°之间，sin∠PAB < 0；因此，只要用AB和AP的叉积的正负，就可以判断P和AB的相对位置。
 * </p>
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

    /**
     * 计算三角形面积
     */
    private static double getArea(double x1, double y1, double x2, double y2, double x3, double y3) {
        double lengthAB = getLength(x1, y1, x2, y2);
        double lengthAC = getLength(x1, y1, x3, y3);
        double lengthBC = getLength(x2, y2, x3, y3);
        double p = (lengthAB + lengthAC + lengthBC) / 2;
        return Math.sqrt(p * (p - lengthAB) * (p - lengthAC) * (p - lengthBC));
    }

    /**
     * 计算两个点的距离
     */
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

        System.out.println(isInsideOne(x1, y1, x2, y2, x3, y3, 0, 5));
        System.out.println(isInsideTwo(x1, y1, x2, y2, x3, y3, 0, 5));
        System.out.println(isInsideOne(x1, y1, x2, y2, x3, y3, 0, 10));
        System.out.println(isInsideTwo(x1, y1, x2, y2, x3, y3, 0, 10));
    }
}
