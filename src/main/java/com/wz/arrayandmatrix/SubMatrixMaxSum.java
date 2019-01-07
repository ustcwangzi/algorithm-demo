/**
 * <p>Title: SubMatrixMaxSum</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/5</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.arrayandmatrix;

/**
 * <p>子矩阵的最大累加和</p>
 * <p>
 *     给定一个矩阵matrix，其中的值有正、有负、有0，获取子矩阵的最大累加和。
 *     例如，matrix为
 *        -90  48  78
 *        64  -40  64
 *        -81  -7  66
 *        其中，最大累加和的子矩阵为：
 *        48  78
 *        -40 64
 *        -7  66
 *        因此，最大累加和为209。
 *     解决方案：
 *        采用了与com.wz.arrayandmatrix.SubArrayMaxSum同样的思想。
 *        假设一个2*4的矩阵为：
 *        -2  3  -5  7
 *        1   4  -1 -3
 *        如何求必须含有2行元素的子矩阵中的最大累加和呢，可以吧两列的元素累加，得到累加矩阵[-1,7,-6,4]，然后再求累加矩阵的最大累加和，为7。
 *        也就是说，必须含有2行元素的子矩阵中的最大累加和为7，且这个子矩阵为
 *        3
 *        4
 *        也就是说，如果一个矩阵共用k行且限定必须含有k行元素的情况下，只需要把每一列的k个元素累加成一个累加数组，然后求这个数组的最大累加和，
 *        结果就是必须包含k行元素的子矩阵的最大累加和。
 *        以
 *        -90  48  78
 *        64  -40  64
 *        -81  -7  66
 *        为例说明以上过程：
 *        1、只考虑第一行[-90,48,78]时，因为只有一行，累加数组就是[-90,48,78]，最大累加和为126；
 *        1.2、考虑含有两行的矩阵：
 *             -90  48  78
 *             64  -40  64
 *             这个矩阵的累加数组为[-26,8,142]，最大累加和为150；
 *        1.3、考虑含有三行的矩阵：
 *             -90  48  78
 *             64  -40  64
 *             -81  -7  66
 *             这个矩阵的累加数组为[-107,1,208]，最大累加和为209；
 *        2、接下来从矩阵的第二行元素开始，继续以上过程，只含一行矩阵：
 *           64  -40  64
 *           因为只有一行，累加数组就是[64,-40,64]，最大累加和为88；
 *        2.1、考虑含有两行的矩阵：
 *             64  -40  64
 *             -81  -7  66
 *             这个矩阵的累加数组为[-17,-47,130]，最大累加和为130；
 *        3、接下来从矩阵的第三行元素开始，继续以上过程，只含一行矩阵：
 *           -81  -7  66
 *           因为只有一行，累加数组就是[-81,-7,66]，最大累加和为66；
 *        全部过程结束，所有的子矩阵都已经考虑到了，结果为以上所有累加和中的最大值209。
 *        整个过程最关键的两个地方为：
 *        1、用求累加数组的最大累加和的方式得到每一步的最大子矩阵的累加和；
 *        2、每一步的累加数组可以利用前一步求出的累加数组很方便的更新得到。
 * </p>
 * <p>
 *     如果矩阵大小为M*N，则时间复杂度为O(M*M*N)
 * </p>
 *
 * @author wangzi
 */
public class SubMatrixMaxSum {

    public static int maxSum(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }

        int max = Integer.MIN_VALUE;
        int sum;
        // 累加数组
        int[] sumArray;
        for (int i = 0; i < matrix.length; i++) {
            // 必须要包含第i行情况下的累加数组
            sumArray = new int[matrix[0].length];
            for (int j = i; j < matrix.length; j++) {
                sum = 0;
                // 从第i行到最后一行逐累加，然后计算最大累加和
                for (int k = 0; k < sumArray.length; k++) {
                    sumArray[k] += matrix[j][k];
                    sum += sumArray[k];
                    max = Math.max(max, sum);
                    sum = sum < 0 ? 0 : sum;
                }
            }
        }
        return max;
    }

    public static void main(String[] args) {
        int[][] matrix = {{-90, 48, 78}, {64, -40, 64}, {-81, -7, 66}};
        System.out.println(maxSum(matrix));
    }
}
