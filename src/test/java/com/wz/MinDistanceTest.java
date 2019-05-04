/**
 * <p>Title: MinDistanceTest</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/5/4</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz;

import com.wz.string.MinDistance;

import java.util.Arrays;
import java.util.Random;

/**
 * <p>数组中两个字符的最小距离</p>
 *
 * @author wangzi
 */
public class MinDistanceTest {
    private static int solution(String[] array, String self, String other){
        if (array == null || self == null || other==null || array.length == 0){
            return -1;
        }
        if (self.equals(other)){
            return 0;
        }
        int result = Integer.MAX_VALUE;
        int latestSelf = -1,latestOther = -1;
        for (int i =0;i<array.length;i++){
            if (array[i].equals(self)) {
                result = Math.min(result, latestOther == -1 ? result : i - latestOther);
                latestSelf=i;
            }
            if (array[i].equals(other)){
                result = Math.min(result, latestSelf == -1 ? result : i - latestSelf);
                latestOther = i;
            }
        }
        return result == Integer.MAX_VALUE ? -1 : result;
    }

    public static void main(String[] args) {
        int times = 100;
        boolean result = true;
        for (int i = 0; i < times; i++) {
            String[] array = RandomUtils.genRandomStringArray();
            String self = array[new Random().nextInt(array.length)];
            String other = array[new Random().nextInt(array.length)];
            if (solution(array,self,other) != MinDistance.minDistance(array,self,other)) {
                result = false;
                System.out.println("Error, array:" + Arrays.toString(array));
            }
        }
        if (result) {
            System.out.println("Past");
        }
    }
}
