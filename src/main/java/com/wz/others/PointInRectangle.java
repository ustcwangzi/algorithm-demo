/**
 * <p>Title: PointInRectangle</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/17</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.others;

/**
 * <p>判断一个点是否在矩形内部</p>
 *
 * @author wangzi
 */
public class PointInRectangle {

    public static boolean isInside(double x1, double y1, double x2, double y2,
                                   double x3, double y3, double x4, double y4, double x, double y) {
        if (y1 == y2) {
            return isInside(x1, y1, x4, y4, x, y);
        }
        // 直角边A
        double legA = Math.abs(y4 - y3);
        // 直角边B
        double legB = Math.abs(x4 - x3);
        // 斜边
        double hypotenuse = Math.sqrt(legB * legB + legA * legA);
        double sin = legA / hypotenuse;
        double cos = legB / hypotenuse;
        // 旋转变换后的坐标
        double x1Rotate = cos * x1 + sin * y1;
        double y1Rotate = -x1 * sin + y1 * cos;
        double x4Rotate = cos * x4 + sin * y4;
        double y4Rotate = -x4 * sin + y4 * cos;
        double xRotate = cos * x + sin * y;
        double yRotate = -x * sin + y * cos;
        return isInside(x1Rotate, y1Rotate, x4Rotate, y4Rotate, xRotate, yRotate);
    }

    /**
     * 矩形平行于坐标轴情况下
     */
    private static boolean isInside(double left, double down, double right, double top, double x, double y) {
        if (x <= left) {
            return false;
        }
        if (x >= right) {
            return false;
        }
        if (y >= down) {
            return false;
        }
        if (y <= top) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        // (x1,y1) should be the most left
        double x1 = 0;
        double y1 = 3;
        // (x2,y2) should be the most top
        double x2 = 3;
        double y2 = 7;
        // (x3,y3) should be the most below
        double x3 = 4;
        double y3 = 0;
        // (x4,y4) should be the most right.
        double x4 = 7;
        double y4 = 4;

        double x = 4;
        double y = 3;
        System.out.print(isInside(x1, y1, x2, y2, x3, y3, x4, y4, x, y));
    }
}
