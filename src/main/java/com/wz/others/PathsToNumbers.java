/**
 * <p>Title: PathsToNumbers</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/23</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.others;

import java.util.Arrays;

/**
 * <p>路径数组变为统计数组</p>
 * <p>
 *     给定一个路径数组paths，表示一张图。paths[i]==j代表城市i连向城市j，如果paths[i]==i表示i城市是首都，一张图里只会有一个首都，
 *     不会有分图且图中除了首都指向自己之外不会有环。
 *     例如：paths={9,1,4,9,0,4,8,9,0,1}，由这个数组表示的图/resources/PathsToNumbers.png如所示。
 *        城市1是首都所以距离为0；离首都距离为1的城市只有城市9；离首都距离为2的城市有城市0，3，7；离首都距离为3的城市有城市4，8；
 *        离首都距离为4的城市有城市2，5，6；所以，距离为0的城市有1座；距离为1的城市有1座；距离为2的城市有3座；距离为3的城市有2座；
 *        距离为4的城市有3座。那么统计数组为numbers={1,1,3,2,3,0,0,0,0,0}，numbers[i]==j代表距离为i的城市有j座。
 *        要求实现一个void类型的函数，输入一个路径数组paths，直接在原数组上调整，使之变为numbers数组。
 *        即paths={9,1,4,9,0,4,8,9,0,1}，函数处理后，paths={1,1,3,2,3,0,0,0,0,0}。
 *     解决方案：
 *        分为两步，第一步是将paths数组转换为距离数组，转换后的paths[i]==j代表城市i与首都的距离为-j；
 *        第二步将paths转换为统计数组，转换后paths[i]==j代表与首都距离为i的城市有j座。
 *        第一步，转换成距离数组，从左到右遍历paths：
 *        1、遍历位置0，paths[0]==9，令paths[0]=-1，城市0指向城市9，所以跳到城市9；
 *           跳到城市9后，paths[9]==1，说明城市9应该跳到城市1，又因为城市9是由城市0跳过来的，先令paths[9]=0，然后跳到城市1；
 *           跳到城市1后，paths[1]==1，说明城市1是首都，停止向前跳的过程；
 *           paths = [-1, 1, 4, 9, 0, 4, 8, 9, 0, 0]
 *           进入回跳过程，城市1是由城市9跳过来的，跳回到城市9；
 *           此时paths[9]==0，可以知道城市9应该回跳到城市0，跳回之前设置paths[9]=-1，表示城市9距离首都为1，然后跳回到城市0；
 *           此时paths[0]==-1，可以知道城市0是整个过程的发起城市，所以不需要再回跳，设置paths[0]=-2，表示城市0距离首都为2。
 *           在跳向首都的过程中，paths数组有一个路径反指的过程，这是为了保证找到首都之后能够完全跳回来，在跳回来的过程中，
 *           设置好这一路所跳过的城市的距离即可。
 *           paths = [-2, 1, 4, 9, 0, 4, 8, 9, 0, -1]
 *        2、遍历位置1，paths[1]==1，说明1是首都，令capital=1，不再做其他操作；
 *        3、遍历位置2，paths[2]==4，令paths[2]=-1，城市2指向城市4，所以跳到城市4；
 *           跳到城市4后，paths[4]==0，说明城市4应该跳到城市0，又因为城市4是由城市2跳过来的，先令paths[4]=2，然后跳到城市0；
 *           跳到城市0后，paths[0]==-2，表示这是之前已经计算过与首都的距离的值，而不是下一个应该跳到的城市，停止向前跳的过程；
 *           paths = [-2, 1, -1, 9, 2, 4, 8, 9, 0, -1]
 *           进入回调过程，跳回到城市4；
 *           此时paths[4]==2，可知城市4应该回调到城市2，先设置paths[4]=-3，因为城市4跳到城市0后发现paths[0]==-2，然后跳回到城市2；
 *           此时paths[2]==-1，可知城市2是整个过程的发起城市，不需要再回跳，设置paths[2]=-4(因为paths[4]==-3)；
 *           paths = [-2, 1, -4, 9, -3, 4, 8, 9, 0, -1]
 *        4、遍历位置3，paths[3]==9，令paths[3]=-1，城市3指向城市9，所以跳到城市9；
 *           跳到城市9后，paths[9]==-1，说明城市9已经计算过与首都的距离，停止向前跳的过程；
 *           paths = [-2, 1, -4, -1, -3, 4, 8, 9, 0, -1]
 *           进入回跳过程，跳回到城市3；
 *           此时paths[3]==-1，可知城市3是整个过程的发起城市，不需要再回跳，设置paths[3]=-2(因为paths[9]==-1)；
 *           paths = [-2, 1, -4, -2, -3, 4, 8, 9, 0, -1]
 *        5、遍历位置4，paths[4]==-3，说明之前已计算过城市4与首都的距离，直接继续下一步；
 *        6、遍历位置5，paths[5]==4，令paths[5]=-1，城市5指向城市4，所以跳到城市4；
 *           跳到城市4后，paths[4]==-3，说明城市4已经计算过与首都的距离，停止向前跳的过程；
 *           paths = [-2, 1, -4, -2, -3, -1, 8, 9, 0, -1]
 *           进入回跳过程，跳回到城市5；
 *           此时paths[5]==-1，可知城市5是整个过程的发起城市，不需要再回跳，设置paths[5]=-4(因为paths[4]==-3)；
 *           paths = [-2, 1, -4, -2, -3, -4, 8, 9, 0, -1]
 *        7、遍历位置6，paths[6]==8，令paths[6]=-1，城市6指向城市8，所以跳到城市8；
 *           跳到城市8后，paths[8]==0，说明城市8应该跳到城市0，又因为城市8是由城市6跳过来的，先令paths[8]=6，然后跳到城市0；
 *           跳到城市0后，paths[0]==-2，说明城市0已经计算过与首都的距离，停止向前跳的过程；
 *           paths = [-2, 1, -4, -2, -3, -4, -1, 9, 6, -1]
 *           进入回跳过程，跳回到城市8；
 *           此时paths[8]==6，可以知道城市8应该回跳到城市6，跳回之前设置paths[8]=-3，然后跳回到城市6；
 *           此时paths[6]==-1，可知城市6是整个过程的发起城市，不需要再回跳，设置paths[6]=-4(因为paths[8]==-3)；
 *           paths = [-2, 1, -4, -2, -3, -4, -4, 9, -3, -1]
 *        8、遍历位置7，paths[7]==9，令paths[7]=-1，城市7指向城市9，所以跳到城市9；
 *           跳到城市9后，paths[9]==-1，说明城市9已经计算过与首都的距离，停止向前跳的过程；
 *           paths = [-2, 1, -4, -2, -3, -4, -4, -1, -3, -1]
 *           进入回跳过程，跳回到城市7；
 *           此时paths[7]==-1，可知城市7是整个过程的发起城市，不需要再回跳，设置paths[7]=-2(因为paths[9]==-1)；
 *           paths = [-2, 1, -4, -2, -3, -4, -4, -2, -3, -1]
 *        9、位置8和位置9都已经是负数，可知之前已计算过，所以不用调整，遍历结束。
 *        10、根据步骤二设置的capital，可知首都是城市1，设置paths[1]=0，此时paths = [-2, 0, -4, -2, -3, -4, -4, -2, -3, -1]。
 *        第二步，将距离数组转为铜壶数组，从左到右遍历paths：
 *        1、遍历位置0，paths[0]==-2，说明距离为2的城市发现了一座，先令paths[0]=0，表示paths[0]的值已经不再表示城市0与首都的距离，
 *           而表示统计距离首都为0的城市数量；
 *           距离为2的城市发现一座，应该设置paths[2]=1，此时发现paths[2]==-4，说明距离为4的城市发现一座；
 *           应该设置paths[4]=1，此时发现paths[4]==-3，说明距离为3的城市发现一座；
 *           应该设置paths[3]=1，此时发现paths[3]==-2，说明距离为2的城市发现一座；
 *           paths = [0, 0, 1, 1, 1, -4, -4, -2, -3, -1]
 *           在设置paths[2]时发现paths[2]==1，已经为正数，不再表示与首都的距离，而表示数量统计，因此直接令paths[2]++；
 *           paths = [0, 0, 2, 1, 1, -4, -4, -2, -3, -1]
 *        2、遍历位置1，paths[1]==0，为正数，直接忽略；
 *        3、位置2、3、4上的值也是正数，忽略；
 *        4、遍历位置5，paths[5]==-4，说明距离为4的城市发现一座，先令paths[5]=0，此时发现paths[4]==1，直接令paths[4]++；
 *           paths = [0, 0, 2, 1, 2, 0, -4, -2, -3, -1]
 *        5、位置6～9与步骤四处理过程基本相同，每一步处理后：
 *           paths = [0, 0, 2, 1, 3, 0, 0, -2, -3, -1]
 *           paths = [0, 0, 3, 1, 3, 0, 0, 0, -3, -1]
 *           paths = [0, 0, 3, 2, 3, 0, 0, 0, 0, -1]
 *           paths = [0, 1, 3, 2, 3, 0, 0, 0, 0, 0]
 *        6、设置paths[0]=1，因为距离为0的城市只有首都。
 *           paths = [1, 1, 3, 2, 3, 0, 0, 0, 0, 0]
 * </p>
 * <p>
 *     如果paths长度为N，时间复杂度为O(N)，额外空间复杂度为O(1)
 * </p>
 *
 * @author wangzi
 */
public class PathsToNumbers {

    public static void pathsToNumbers(int[] paths) {
        if (paths == null || paths.length == 0) {
            return;
        }
        // citiesPath -> distancesArray
        pathsToDistances(paths);

        // distancesArray -> numberArray
        distanceToNumbers(paths);
    }

    private static void pathsToDistances(int[] paths) {
        // 首都
        int capital = 0;
        for (int i = 0; i < paths.length; i++) {
            if (paths[i] == i) {
                capital = i;
            } else if (paths[i] > -1) {
                int cur = paths[i];
                paths[i] = -1;
                int pre = i;
                // 从cur开始向下一个城市跳，同时记录从哪里跳过来的
                while (paths[cur] != cur) {
                    if (paths[cur] > -1) {
                        // cur的下一个城市
                        int next = paths[cur];
                        // 记录从哪个城市跳过来的
                        paths[cur] = pre;
                        // 继续向下跳
                        pre = cur;
                        cur = next;
                    } else {
                        // 已处理过
                        break;
                    }
                }

                // 跳到了首都的位置，距离设为0，否则直接使用cur距离首都的距离
                int value = paths[cur] == cur ? 0 : paths[cur];
                // 从pre开始回跳，回跳过程中设置距离
                while (paths[pre] != -1) {
                    int lastPre = paths[pre];
                    paths[pre] = --value;
                    pre = lastPre;
                }
                paths[pre] = --value;
            }
        }
        paths[capital] = 0;
    }

    private static void distanceToNumbers(int[] distances) {
        for (int i = 0; i < distances.length; i++) {
            int index = distances[i];
            if (index < 0) {
                distances[i] = 0;
                while (true) {
                    index = -index;
                    if (distances[index] > -1) {
                        // 值是正数，表示当前值代表的是距离为index的城市数量统计
                        distances[index]++;
                        break;
                    } else {
                        // 值是负数，表示当前值代表的是城市index与首都的距离
                        int nextIndex = distances[index];
                        distances[index] = 1;
                        index = nextIndex;
                    }
                }
            }
        }
        // 距离为0的城市只有首都
        distances[0] = 1;
    }

    public static void main(String[] args) {
        int[] paths = {9, 1, 4, 9, 0, 4, 8, 9, 0, 1};
        pathsToNumbers(paths);
        System.out.println(Arrays.toString(paths));
    }
}
