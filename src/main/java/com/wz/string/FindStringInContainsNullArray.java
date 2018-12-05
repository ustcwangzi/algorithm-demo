/**
 * <p>Title: FindStringInContainsNullArray</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/12/5</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.string;

/**
 * <p>在有序但包含空的数组中查找字符串</p>
 * <p>
 *     给定一个字符串数组array[]，在array中有些位置为null，但在不为null的位置上，其字符串是按照字典顺序由小到大出现的。
 *     在给定一个字符串target，查找target在array中出现的左侧的位置，target为null时返回-1，找不到时返回-1。
 *     解决方案：
 *        尽可能多的采用二分查找，过程如下：
 *        1、在array[left...right]上进行查找，result表示target在array中最左的位置，初始时left=0，right=array.length-1，result=-1
 *        2、令mid=(left+right)/2，array[mid]为array[left...right]中间位置的字符串
 *        3、如果array[mid]与target相等，说明找到了target，令result=mid，但要找的是最左的位置，所以还要在左半区查找，
 *           看有没有更左的target出现，令right=mid-1，然后重复步骤二
 *        4、如果array[mid]与target不等，且array[mid]!=null
 *        4.1、若array[mid]小于target，说明整个左半区不会出现target，在右半区查找，令left=mid+1，然后重复步骤二
 *        4.2、若array[mid]大于target，说明整个右半区不会出现target，在左半区查找，令right=mid-1，然后重复步骤二
 *        5、如果array[mid]与target不等，且array[mid]==null，此时从mid开始，从右到左遍历左半区，到第一个不为null的位置i停止
 *        5.1、如果i < left，说明整个左半区都为null，在右半区查找，令left=mid+1，然后重复步骤二
 *        5.2、如果array[i]小于target，说明整个左半区不会出现target，在右半区查找，令left=mid+1，然后重复步骤二
 *        5.3、如果array[i]等于target，说明找到了target，令result=i，但要找的是最左的位置，令right=i-1，然后重复步骤二。
 * </p>
 *
 * @author wangzi
 */
public class FindStringInContainsNullArray {
    public static int getIndex(String[] array, String target) {
        if (array == null || array.length == 0 || target == null) {
            return -1;
        }

        int result = -1;
        int left = 0, mid, right = array.length - 1;

        while (left <= right) {
            mid = (left + right) / 2;
            if (array[mid] != null && array[mid].equals(target)) {
                // 因为需要的是最左侧的位置，还需要再看mid的左侧是否存在target
                result = mid;
                right = mid - 1;
            } else if (array[mid] != null) {
                if (array[mid].compareTo(target) < 0) {
                    // 在右侧查找
                    left = mid + 1;
                } else {
                    // 在左侧查找
                    right = mid - 1;
                }
            } else {
                // 说明array[mid]==null
                int i = mid;
                while (array[i] == null && --i >= left) {
                    // 从mid开始，从右到左遍历左侧部分，结束时i为第一个不为null的位置
                }
                if (i < left || array[i].compareTo(target) < 0) {
                    // 整个左则部分都为null，或者第一个不为null的值小于target，说明左侧不存在，遍历右侧部分
                    left = mid + 1;
                } else {
                    // 找到了target，因为需要的是最左侧的位置，还需要再看i的左侧是否存在target
                    result = array[i].equals(target) ? i : result;
                    right = i - 1;
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {
        String[] array = new String[]{null, "a", null, "b", null, "b", "c", null, "d", null, "e", null};
        System.out.println(getIndex(array, "a"));
        System.out.println(getIndex(array, "b"));
        System.out.println(getIndex(array, "c"));
        System.out.println(getIndex(array, "d"));
        System.out.println(getIndex(array, "e"));
    }
}
