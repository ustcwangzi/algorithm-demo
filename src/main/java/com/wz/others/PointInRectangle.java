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
 * <p>
 *     在二维坐标系中，所有的值都是double类型，那么一个矩形可以由四个点代表，(x1,y1)为最左的点，(x2,y2)为最上的点，(x3,y3)为最下的点，
 *     (x4,y4)为最右的点，给定四个点代表的矩形，再给定一个点(x,y)，判断(x,y)是否在矩形中。
 *     解决方案：
 *        当矩形的边平行于坐标轴时，直接判断该点是否完全在矩形的左侧、右侧、上侧或下侧，如果都不是，就一定在矩形中。
 *        当矩形的边不平行于坐标轴时，可通过坐标变换把矩形转成平行的情况，在旋转时所有的点跟着转动，旋转完成后，再进行判断。
 *        旋转变换就是将平面上任意一点绕原点旋转θ角，一般规定逆时针方向为正，顺时针方向为负，具体如图resources/PointInRectangle.png。
 *        又有公式：sin(A+B) = sinAcosB+cosAsinB，sin(A-B) = sinAcosB-cosAsinB，
 *                cos(A+B) = cosAcosB-sinAsinB，cos(A-B) = cosAcosB+sinAsinB
 *        假设矩形在旋转前与坐标轴的角度为A，则：x=r*cosA，y=r*sinA
 *        在这里将矩形按照顺时针方向进行旋转，因此可以得到：
 *        x1 = r*cos(A-B) = r*(cosAcosB+sinAsinB) = r*cosAcosB+r*sinAsinB = x*cosB+y*sinB
 *        y1 = r*sin(A-B) = r*(sinAcosB-cosAsinB) = r*sinAcosB-r*cosAsinB = y*cosB-x*sinB
 * </p>
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
