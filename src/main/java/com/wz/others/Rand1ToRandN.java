/**
 * <p>Title: Rand1ToRandN</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/14</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.others;

/**
 * <p>从随机到随机的扩展</p>
 * <p>
 *     问题一：
 *        给定等概率随机产生1～5的随机函数rand1To5：
 *        private static int rand1To5() {
 *           return (int) (Math.random() * 5) + 1;
 *        }
 *        除此之外，不能使用任何额外的随机机制，请使用rand1To5实现等概率随机产生1～7的随机函数rand1To7。
 *     问题二：
 *        给定一个以p概率产生0，以1-p概率产生1的随机函数rand01p：
 *        private static int rand01p() {
 *           // you can change p to what you like, but it must be (0,1)
 *           double p = 0.83;
 *           return Math.random() < p ? 0 : 1;
 *        }
 *        除此之外，不能使用任何额外的随机机制，请使用rand01p实现等概率随机产生1～6的随机函数rand1To6。
 *     问题三：
 *        给定等概率随机产生1～M的随机函数rand1ToM：
 *        private static int rand1ToM(int m) {
 *           return (int) (Math.random() * m) + 1;
 *        }
 *        除此之外，不能使用任何额外的随机机制，有两个输入参数m和n，请使用rand1ToM实现等概率随机产生1～N的随机函数rand1ToN。
 *     问题一解答：
 *        1、rand1To5等概率随机产生1,2,3,4,5
 *        2、rand1To5-1等概率随机产生0,1,2,3,4
 *        3、(rand1To5-1)*5等概率随机产生0,5,10,15,20
 *        4、(rand1To5-1)*5+(rand1To5-1)等概率随机产生0,1,2,3,...,22,23,24，注意这里的两个rand1To5是指独立的两次调用，不能化简，
 *           这是"插空"的过程
 *        5、如果步骤四产生的结果大于20，则重复执行步骤四，直到产生的结果在0～20之间，同时可知出现21～24的概率，会平均分配到0～20上，
 *           这是"筛"的过程
 *        6、步骤五会等概率随机产生0～20，所以步骤五的结果再%7，就会等概率随机产生0～6
 *        7、步骤六的结果再加上1，就会等概率随机产生1～7。
 *     问题二解答：
 *        虽然rand01p以p概率产生0，以1-p概率产生1，但是rand01p产生01和10的概率都是p(1-p)，
 *        可以利用这一点来实现等概率随机产生0和1的函数rand01。
 *        1、rand01等概率随机产生0和1
 *        2、rand01*2等概率随机产生0和2
 *        3、rand01*2+rand01等概率随机产生0,1,2,3，即等概率随机产生0～3的函数rand0To3，注意这里的两个rand01是独立的两次调用，不能化简，
 *           这是"插空"的过程
 *        4、rand0To3*4+rand0To3等概率随机产生0,1,2,3,...,13,14,15，注意这里的两个rand0To3是独立的两次调用，不要化简，
 *           这还是"插空"的过程
 *        5、如果步骤四产生的结果大于11，则重复执行步骤四，直到产生的结果在0～11直接，同时可知出现12～15的概率，会平均分配到0～11上，
 *           这是"筛"的过程
 *        6、步骤五会等概率随机产生0～11，所以步骤五的结果再%6，就会等概率随机产生0～5
 *        7、步骤六的结果再加1，就会等概率随机产生1～6。
 *     问题三解答：
 *        通过"插空"和"筛"过程，只要给定某一个区间上的等概率随机函数，就可以实现任意区间上的随机函数。
 *        如果M>=N，直接进入"筛"过程；如果M<N，先进入"插空"过程，直到产生比N的范围大的随机范围后，再进入"筛"的过程。
 *        具体来说，就是调用k次rand1ToM，产生有k位的M进制数，并且产生的范围要大于等于N。
 *        比如随机5到随机7的问题中，首先产生0～24范围的数，其实就是0～(5^2-1)范围的数。
 *        在把范围扩到大于等于N的级别之后，如果真是产生的数大于或等于N，就忽略，这是"筛"的过程。
 *        只留下小于等于N的数，那么在0～N-1上就可以做到均匀分布。
 * </p>
 *
 * @author wangzi
 */
public class Rand1ToRandN {

    /**
     * 利用rand1To5生成rand1To7
     */
    public static int rand1To7() {
        int num;
        do {
            num = (rand1To5() - 1) * 5 + rand1To5() - 1;
        } while (num > 20);
        return num % 7 + 1;
    }

    /**
     * 已知条件
     */
    private static int rand1To5() {
        return (int) (Math.random() * 5) + 1;
    }

    /**
     * 利用rand01生成rand1To6
     */
    public static int rand1To6() {
        int num;
        do {
            num = rand0To3() * 4 + rand0To3();
        } while (num > 11);
        return num % 6 + 1;
    }

    /**
     * 利用rand01生成rand0To3
     */
    private static int rand0To3() {
        return rand01() * 2 + rand01();
    }

    /**
     * 利用rand01p生成rand01
     */
    private static int rand01() {
        int num;
        do {
            num = rand01p();
        } while (num == rand01p());
        return num;
    }

    /**
     * 已知条件
     */
    private static int rand01p() {
        // you can change p to what you like, but it must be (0,1)
        double p = 0.83;
        return Math.random() < p ? 0 : 1;
    }

    /**
     * 利用rand1ToM生成rand1ToN
     */
    public static int rand1ToN(int n, int m) {
        // n-1转换成m进制数
        int[] mSystemNum = getMSystemNum(n - 1, m);
        // 等概率随机产生0～n-1，m进制表达
        int[] randNum = getRandMSystemNumLessN(mSystemNum, m);
        // 将产生的随机数转换成十进制，然后加1，即等概率随机产生1～n
        return getNumFromMSystemNum(randNum, m) + 1;
    }

    /**
     * 将十进制的value转换为m进制的数
     */
    private static int[] getMSystemNum(int value, int m) {
        int[] result = new int[32];
        int index = result.length - 1;
        while (value != 0) {
            result[index--] = value % m;
            value = value / m;
        }
        return result;
    }

    /**
     * 将m进制的数转换为十进制数
     */
    private static int getNumFromMSystemNum(int[] mSystemNum, int m) {
        int res = 0;
        for (int cur : mSystemNum) {
            res = res * m + cur;
        }
        return res;
    }

    /**
     * 等概率随机产生一个0～mSystemNum范围上的数，m进制表达
     */
    private static int[] getRandMSystemNumLessN(int[] mSystemNum, int m) {
        int[] result = new int[mSystemNum.length];
        // start为mSystemNum的最高位
        int start = 0;
        while (mSystemNum[start] == 0) {
            start++;
        }
        // index为当前需要生成的位
        int index = start;
        // lastEqual为true时，需要将生成的result[index]与mSystemNum[index]比较
        // 若result[index]>mSystemNum[index]，生成的随机数必然大于mSystemNum，丢弃，重新从start处生成新的随机数
        // lastEqual为false时，后面无论生成的是什么，result肯定小于mSystemNum，不需要比较result[index]与mSystemNum[index]
        boolean lastEqual = true;
        while (index < mSystemNum.length) {
            result[index] = rand1ToM(m) - 1;
            if (lastEqual) {
                if (result[index] > mSystemNum[index]) {
                    index = start;
                    lastEqual = true;
                    continue;
                } else {
                    lastEqual = result[index] == mSystemNum[index];
                }
            }
            index++;
        }
        return result;
    }

    /**
     * 已知条件
     */
    private static int rand1ToM(int m) {
        return (int) (Math.random() * m) + 1;
    }

    private static void printCountArray(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.println(i + " appears " + array[i] + " times");
        }
    }

    public static void main(String[] args) {
        int times = 100000;
        int[] array = new int[8];
        for (int i = 0; i < times; i++) {
            array[rand1To7()]++;
        }
        printCountArray(array);

        System.out.println("=====================");

        array = new int[7];
        for (int i = 0; i < times; i++) {
            array[rand1To6()]++;
        }
        printCountArray(array);

        System.out.println("=====================");

        int n = 17;
        int m = 3;
        array = new int[n + 1];
        for (int i = 0; i != 2000000; i++) {
            array[rand1ToN(n, m)]++;
        }
        printCountArray(array);
    }
}
