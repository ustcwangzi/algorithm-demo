/**
 * <p>Title: SubtreeEquals</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/11/3</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.binarytree;

/**
 * <p>一棵树中是否有与另一棵树拓扑结构完全相同的子树</p>
 * <p>
 *     把两棵树按照先序遍历的方式进行序列化 @see com.wz.binarytree.SerializeTree
 *     self序列化后的结果为selfStr，other序列化后的结果为otherStr，验证otherStr是否是selfStr的子串即可
 *     使用KMP算法可在线性时间内解决该问题，KMP算法时间复杂度为O(M+N)，KMP算法详细内容不在此进行展开
 *     序列化时间复杂度为O(N)，KMP算法时间复杂度为O(M+N)，因此总的时间复杂度为O(M+N)
 * </p>
 *
 * @author wangzi
 */
public class SubtreeEquals {
    private static final String SYMBOL_NULL = "#";
    private static final String SYMBOL_END = "!";

    private static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    public static boolean isSubtree(Node self, Node other) {
        String selfStr = serialByPreOrder(self);
        String otherStr = serialByPreOrder(other);
        return getIndexOf(selfStr, otherStr) != -1;
    }

    /**
     * 序列化-先序遍历
     */
    private static String serialByPreOrder(Node head) {
        if (head == null) {
            return SYMBOL_NULL + SYMBOL_END;
        }
        String result = head.value + SYMBOL_END;
        result += serialByPreOrder(head.left);
        result += serialByPreOrder(head.right);
        return result;
    }

    /**
     * KMP算法
     *   str：... ... ... ... |...c...|A| ...
     * match：|...a...|C| ... |...b...|B| ...
     * match：----> ...  ---> |...a...|C| ...
     * 上图中，str和match匹配到A字符和B字符时才发生的不匹配，因此c区域与b区域相等
     * 根据nextArray的定义，b区域又与a区域相等，所以a区域和c区域不用检查，直接将match滑到字符C开始匹配即可
     */
    private static int getIndexOf(String str, String match) {
        if (str == null || match == null || match.length() < 1 || str.length() < match.length()) {
            return -1;
        }

        char[] strArray = str.toCharArray();
        char[] matchArray = match.toCharArray();
        int[] nextArray = getNextArray(matchArray);
        int strIndex = 0, matchIndex = 0;
        while (strIndex < strArray.length && matchIndex < matchArray.length) {
            if (strArray[strIndex] == matchArray[matchIndex]) {
                strIndex++;
                matchIndex++;
            } else if (nextArray[matchIndex] == -1) {
                strIndex++;
            } else {
                // match向右滑动
                matchIndex = nextArray[matchIndex];
            }
        }
        return matchIndex == matchArray.length ? strIndex - matchIndex : -1;
    }

    /**
     * nextArray[i]的含义是：在match[0...i-1]中，以match[i-1]结尾的后缀子串(不包含match[0])
     * 与以match[0]开头的前缀子串(不包含match[i-1])，最大的匹配长度
     * eg. match为"aaaab"，nextArray[4]的最大匹配为match[1...3]与match[0...2]，即nextArray[4]=3
     * match为"abc1abc1"，nextArray[7]的最大匹配为match[4...6]与match[0...2]，即nextArray[7]=3
     */
    private static int[] getNextArray(char[] match) {
        if (match.length == 1) {
            return new int[]{-1};
        }
        int[] nextArray = new int[match.length];
        // 之前没有任何字符，故赋值为-1
        nextArray[0] = -1;
        // 任何子串的后缀不能包含第一个字符，因此nextArray[1]赋值为0
        nextArray[1] = 0;
        int position = 2;
        int cn = 0;
        while (position < nextArray.length) {
            if (match[position - 1] == match[cn]) {
                nextArray[position++] = ++cn;
            } else if (cn > 0) {
                cn = nextArray[cn];
            } else {
                nextArray[position++] = 0;
            }
        }
        return nextArray;
    }

    public static void main(String[] args) {
        Node self = new Node(1);
        self.left = new Node(2);
        self.right = new Node(3);
        self.left.left = new Node(4);
        self.left.right = new Node(5);
        self.right.left = new Node(6);
        self.right.right = new Node(7);
        self.left.left.right = new Node(8);
        self.left.right.left = new Node(9);

        Node other = new Node(2);
        other.left = new Node(4);
        other.left.right = new Node(8);
        other.right = new Node(5);
        other.right.left = new Node(9);

        System.out.println(isSubtree(self, other));
    }
}
