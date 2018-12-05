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
