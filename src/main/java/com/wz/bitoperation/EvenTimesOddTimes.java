/**
 * <p>Title: EvenTimesOddTimes</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/12/22</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.bitoperation;

/**
 * <p>其他数出现偶数次的数组中找到出现奇数次的数</p>
 *
 * @author wangzi
 */
public class EvenTimesOddTimes {

    public static void printOneOddTimesNum(int[] array){
        int tmp = 0;
        for (int cur : array){
            tmp^=cur;
        }
        System.out.println(tmp);
    }

    public static void printTwoOddTimesNum(int[] array){
        int tmp=0,one=0;
        for (int cur : array){
            tmp^=cur;
        }
        // 第k位为1
        int rightOne = tmp &(~tmp+1);
        for (int cur:array){
            // 只与第k位是1的异或，结束后one就是两个数中的一个
            if ((cur&rightOne) !=0){
                one^=cur;
            }
        }
        System.out.println(one + ", " + (tmp^one));
    }

    public static void main(String[] args) {
        int[] arr1 = { 3, 3, 2, 3, 1, 1, 1, 3, 1, 1, 1 };
        printOneOddTimesNum(arr1);

        int[] arr2 = { 4, 3, 4, 2, 2, 2, 4, 1, 1, 1, 3, 3, 1, 1, 1, 4, 2, 2 };
        printTwoOddTimesNum(arr2);
    }
}
