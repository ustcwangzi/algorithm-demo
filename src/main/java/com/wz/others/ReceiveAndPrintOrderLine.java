/**
 * <p>Title: ReceiveAndPrintOrderLine</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2019/1/31</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.others;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>一种消息接收并打印的结构设计</p>
 * <p>
 *     消息流吐出2，一种结构接收而不打印2，因为1还没出现；
 *     消息流吐出1，一种结构接收1，并且打印：1，2；
 *     消息流吐出4，一种结构接收而不打印4，因为3还没有出现；
 *     消息流吐出5，一种结构接收而不打印5，因为3还没有出现；
 *     消息流吐出7，一种结构接收而不打印7，因为3还没有出现；
 *     消息流吐出3，一种结构接收3，并且打印：3，4，5；
 *     消息流吐出9，一种结构接收而不打印9，因为6还没出现；
 *     消息流吐出8，一种结构接收而不打印8，因为6还没出现；
 *     消息流吐出6，一种结构接收6，并且打印：6，7，8，9。
 *     已知一个消息流会不断地吐出整数1～N，但不一定按照顺序吐出。
 *     如果上次打印的数为i，那么当i+1出现时，请打印i+1及其之后接收过的并且连续的所有数，直到1～N全部接收并且打印完。
 *     解决方案：
 *        对于接收的每一个数，如果能和之前接收的数连续起来，就归入之前的数据区间中，如果不能就单独成为一个区间。每一个区间使用单链表结构实现。
 *        以实例说明过程：
 *        1、消息流吐出2，接收并生成连续区间{2}，不打印，因为1未出现；
 *        2、消息流吐出1，接收并生成连续区间{1}，发现可以与{2}连在一起，生成连续区间{1,2}，此时打印1，2，然后删除连续区间{1,2}；
 *        3、消息流吐出4，接收并生成连续区间{4}；
 *        4、消息流吐出5，接收并生成连续区间{5}，发现可以与{4}连在一起，生成连续区间{4,5}；
 *        5、消息流吐出7，接收并生成连续区间{7}，此时有两个连续区间，分别为{4,5}和{7}；
 *        6、消息流吐出9，接收并生成连续区间{9}，此时有三个连续区间，分别为{4,5}、{7}和{9}；
 *        7、消息流吐出8，接收并生成连续区间{8}，发现可以与{7}和{9}连在一起生成{7,8,9}，此时有两个连续区间，分别为{4,5}和{7,8,9}；
 *        8、消息流吐出6，接收并生成连续区间{6}，发现可以与{4,5}和{7,8,9}连在一起，生成{4,5,6,7,8,9}；
 *        9、消息流吐出3，接收并生成连续区间{3}，发现可以与{4,5,6,7,8,9}连在一起，生成{3,4,5,6,7,8,9}，3出现，打印3,4,5,6,7,8,9。
 *        下面是整个算法过程：
 *        1、当接收一个数num时，根据num生成一个单链表节点的实例，单链表节点结构为Node
 *        2、连续结构就是一个单链表结构，为了加速合并的过程，使用两个哈希表headMap和tailMap，分别用来存放区间的开头节点和结尾节点。
 *           比如连续区间{4,5,6,7,8,9}，值为4的节点记为start，值为9的节点记为end，则headMap中有(4,start)，tailMap中有(9,end)。
 *        3、接收num之后，根据num生成节点cur，现在的num可以自己成为一个联系区间，即在headMap和tailMap中加入(num,cur)
 *        3.1、在tailMap中检查是否存在key==num-1的记录，若有，说明存在连续区间以num-1结尾，记为A，那么A可与num的连续区间合并，
 *             假设A区间最后的数num-1对应的节点为end，令end.next=cur，然后在tailMap中删除(num-1,end)，在headMap中删除(num,cur)。
 *             如步骤8中加入6时，发现tailMap存在key==5，因此令5.next=6，tailMap中删除5。
 *        3.2、在headMap中检查是否存在key==num+1的纪录，若有，说明存在连续区间以num+1开始，记为B，那么B可与num的连续区间合并，
 *             假设num+1对应的节点为start，令cur.next=start，然后在headMap中删除(num+1,start)，在tailMap中删除(num,cur)。
 *             如步骤8中加入6时，发现headMap存在key==7，因此令6.next=7，headMap中删除7。
 *        4、设置一个变量lastPrint，表示上一次打印的最后一个元素是什么，然后每次在headMap中寻找是否有lastPrint+1元素，
 *           如果有的话，将整个以lastPrint+1开头的区间的数都打印，然后更新lastPrint。
 * </p>
 * <p>时间复杂度为O(N)</p>
 *
 * @author wangzi
 */
public class ReceiveAndPrintOrderLine {

    private static class Node {
        public int num;
        public Node next;

        public Node(int num) {
            this.num = num;
        }
    }

    public static class MessageBox {
        /**
         * 连续区间开始节点
         */
        private Map<Integer, Node> headMap;
        /**
         * 连续区间结束节点
         */
        private Map<Integer, Node> tailMap;
        /**
         * 最后一个打印的数
         */
        private int lastPrint;

        public MessageBox() {
            this.headMap = new HashMap<>();
            this.tailMap = new HashMap<>();
            this.lastPrint = 0;
        }

        public void receive(int num) {
            if (num < 1) {
                return;
            }
            Node cur = new Node(num);
            headMap.put(num, cur);
            tailMap.put(num, cur);

            if (tailMap.containsKey(num - 1)) {
                tailMap.get(num - 1).next = cur;
                tailMap.remove(num - 1);
                headMap.remove(num);
            }
            if (headMap.containsKey(num + 1)) {
                cur.next = headMap.get(num + 1);
                tailMap.remove(num);
                headMap.remove(num + 1);
            }
            if (headMap.containsKey(lastPrint + 1)) {
                print();
            }
        }

        private void print() {
            Node node = headMap.get(++lastPrint);
            headMap.remove(lastPrint);
            while (node != null) {
                System.out.print(node.num + " ");
                node = node.next;
                lastPrint++;
            }
            tailMap.remove(--lastPrint);
            System.out.println();
        }
    }

    public static void main(String[] args) {
        MessageBox box = new MessageBox();

        // - 2
        box.receive(2);
        // 1 2 -> print, trigger is 1
        box.receive(1);

        // - 4
        box.receive(4);
        // - 4 5
        box.receive(5);
        // - 4 5 - 7
        box.receive(7);
        // - 4 5 - 7 8
        box.receive(8);
        // - 4 5 6 7 8
        box.receive(6);
        // 3 4 5 6 7 8 -> print, trigger is 3
        box.receive(3);

        // 9 -> print, trigger is 9
        box.receive(9);

        // 10 -> print, trigger is 10
        box.receive(10);

        // - 12
        box.receive(12);
        // - 12 13
        box.receive(13);
        // 11 12 13 -> print, trigger is 11
        box.receive(11);
    }
}
