/**
 * <p>Title: LongestLessSumSubArrayLength</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/2</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.arrayandmatrix;

/**
 * <p>无序数组中累加和小于等于给定值的最长子数组长度</p>
 * <p>
 *     给定一个无序数组array，其中元素可正、可负、可0，给定一个整数k，求array所有的子数组中累加和小于等于k的最长子数组长度。
 *     例如，array=[3,-2,-4,0,6]，k=-2，累加和小于等于-2的最长子数组为{3,-2,-4,0}，因此返回4。
 *     解决方案：
 *        依次求出以数组的每个位置结尾的、累加和小于等于k的最长子数组长度，其中最长的那个就是结果。
 *        假如当前处理到位置30，从位置0到30的累加和为100，现在要求出以位置30结尾的、累加和小于等于10的最长子数组长度，
 *        那么只需要知道从位置0到哪个位置时累加和第一次大于等于90即可，假设从位置0到20时累加和第一次大于等于90，
 *        则可以知道以位置30结尾的、累加和小于等于10的最长子数组为array[21...30]。
 *        也就是说，如果从位置0到j的累加和为sum[0...j]，此时想求以位置j结尾的、累加和小于等于k的最长子数组长度，
 *        只需要知道大于等于(sum[0...j]-k)的累计和最早出现在j之前的什么位置即可，假设为位置i，则array[i+1...j]就是需要的子数组。
 *        为方便找到大于等于某个值的累加和最早出现的位置，可以按照如下方法生成辅助数组maxArray：
 *        1、首先生成array每个位置从左到右的累加和数组sumArray。以[1,2,-1,5,-2]为例，sumArray=[0,1,3,2,7,5]。
 *           注意sumArray中第一个数为0，表示没有任何一个数时的累加和为0。
 *        2、生成sumArray的左侧最大值数组maxArray，sumArray=[0,1,3,2,7,5] -> maxArray=[0,1,3,3,7,7]。
 *           注意原来的2和5变成了3和7，因此这里只保留更大的、出现更早的累加和。
 *        3、maxArray一定是有序的，因此可以使用二分查找大于等于某个值的最早出现的位置。
 *        接下来以array=[3,-2,-4,0,6]，k=-2为例，说明整个求解过程：
 *        1、array=[3,-2,-4,0,6] -> sumArray=[0,3,1,-3,-3,3] -> maxArray=[0,3,3,3,3,3]
 *        2、j=0时，sum[0...0]=sumArray[1]=3，在maxArray中二分查找大于等于3-(-2)=5第一次出现的位置，未出现，
 *           即以位置0结尾的所有子数组累加后没有小于等于-2的。
 *        3、j=1时，sum[0...1]=sumArray[2]=1，在maxArray中二分查找大于等于1-(-2)=3第一次出现的位置，是位置1，
 *           对应到array中是位置0，所以array[1...1]满足条件。
 *        4、j=2时，sum[0...2]=sumArray[3]=-3，在maxArray中二分查找大于等于-3-(-2)=-1第一次出现的位置，是位置0，
 *           对应到array中是位置-1，表示一个数都不累加的情况，所以array[0...2]满足条件。
 *        5、j=3时，sum[0...3]=sumArray[4]=-3，在maxArray中二分查找大于等于-3-(-2)=-1第一次出现的位置，是位置0，
 *           对应到array中是位置-1，表示一个数都不累加的情况，所以array[0...3]满足条件。
 *        6、j=4时，sum[0...4]=sumArray[5]=3，在maxArray中二分查找大于等于3-(-2)=5第一次出现的位置，未出现，
 *           即以位置4结尾的所有子数组累加后没有小于等于-2的。
 * </p>
 * <p>时间复杂度为O(N*logN)，空间复杂度为O(N)</p>
 *
 * @author wangzi
 */
public class LongestLessSumSubArrayLength {

    public static int getMaxLength(int[] array, int k) {
        int[] maxArray = new int[array.length + 1];
        maxArray[0] = 0;
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
            maxArray[i + 1] = Math.max(sum, maxArray[i]);
        }
        sum = 0;
        int pre, length;
        int result = 0;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
            // 大于等于(sum-k)第一次出现的位置
            pre = getLessIndex(maxArray, sum - k);
            // pre到i当前就是满足(小于等于k)的最长数组
            length = pre == -1 ? 0 : i - pre + 1;
            result = Math.max(result, length);
        }
        return result;
    }

    /**
     * 二分查找大于等于num第一次出现的位置
     */
    private static int getLessIndex(int[] array, int num) {
        int low = 0, mid = 0, high = array.length - 1;
        int result = -1;
        while (low <= high) {
            mid = (low + high) / 2;
            if (array[mid] >= num) {
                result = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] array = {3, -2, -4, 0, 6};
        System.out.println(getMaxLength(array, -2));
    }
}
