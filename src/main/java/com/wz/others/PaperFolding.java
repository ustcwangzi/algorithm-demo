/**
 * <p>Title: PaperFolding</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/19</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.others;

/**
 * <p>折纸问题</p>
 * <p>
 *     把一段纸条竖着放在桌子上，然后从纸条的下边向上方对折1次，压出折痕后展开，此时折痕是凹下去的，即折痕突起的方向指向纸条的背面。
 *     如果从纸条的下边向上方连续对折2次，压出折痕后展开，此时有三条折痕，从上到下依次是下折痕、下折痕和上折痕。
 *     给定一个输入参数N，代表纸条都从下边向上方连续对折N次，请从下到上打印所有折痕的方向。
 *     例如：N=1时，打印：down
 *          N=2时，打印：down down up
 *     解决方案：
 *        对折1次产生的折痕：               下
 *        对折2次产生的折痕：       上                下
 *        对折3次产生的折痕：   上       下       上        下
 *        对折4次产生的折痕：上    下  上   下  上    下  上    下
 *        如上图关系可以总结出：
 *        1、产生第i+1次折痕的过程，就是在对折i次产生的每一条折痕的左右两侧，依次插入上折痕和下折痕的过程
 *        2、所有折痕的结构是一棵满二叉树，在这棵二叉树中，头节点为下折痕，每一棵左子树的头节点为上折痕，每一棵右子树的头节点为下折痕
 *        3、从上到下打印所有折痕方向的过程，就是二叉树的先右、再中、最后左的中序遍历。
 * </p>
 *
 * @author wangzi
 */
public class PaperFolding {

    public static void printAllFolds(int n) {
        printProcess(1, n, true);
    }

    private static void printProcess(int i, int n, boolean down) {
        if (i > n) {
            return;
        }
        printProcess(i + 1, n, true);
        System.out.println(down ? "down " : "up ");
        printProcess(i + 1, n, false);
    }

    public static void main(String[] args) {
        printAllFolds(4);
    }
}
