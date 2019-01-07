# algorithm-demo
## sort 排序
- Insertion：插入排序
- Selection：选择排序
- Shell：希尔排序
- Quick：快速排序
- Merge：归并排序
- Heap：堆排序
- LSD：低位优先排序
- MSD：高位优先排序
- QuickThreeWay：三向快速排序

## search 查找
- SequentialSearch：顺序查找
- BinarySearch：二分查找
- BinarySearchTree：二叉查找树
- SeparateChainingHashSearch：基于链表的散列表查找
- LinearProbingHashSearch：基于线性探测的散列表查找
### str 字符串查找
- TrieSearchTree：R向单词查找树
- ThreeWaySearchTree：三向单词查找树
- KMP：KMP字符串查找算法
- RabinKarp：RabinKarp指纹字符串查找算法
- RegExpSearch：正则表达式匹配

## unionfind 动态连通性
- UnionFind：普通UnionFind算法
- QuickUnionFind：快速UnionFind算法
- WeightedQuickUnionFind：加权快速UnionFind算法

## graph 图
### undirected 无向图
- Graph：使用邻接表表示图
- DepthFirstSearch：深度优先遍历
- DepthFirstPaths：使用深度优先遍历查找路径
- ConnectedComponent：计算联通分量
- BreadthFirstPaths：广度优先遍历查找路径
### directed 有向图
- Digraph：使用邻接表表示有向图
- DepthFirstOrder：基于深度优先搜索的顶点排序
- DepthFirstDirectedPaths：使用深度优先搜索查找有向图中的路径
- BreadthFirstDirectedPaths：使用广度优先搜索查找有向图中的路径
- DirectedCycle：寻找有向环
- DirectedDFS：深度优先遍历计算有向图的可达性
- KosarajuConnectedComponent：计算强联通分量
- Topological：拓扑排序
- TransitiveClosure：顶点对的可达性
### mst 最小生成树(Minimum Spanning Tree)
- WeightedGraph：加权无向图
- LazyPrimMST：Prim算法延时实现
- PrimMST：Prim算法即时实现
- KruskalMST：Kruskal算法
### shortestpath 最短路径(Shortest Path)
- WeightedDigraph：加权有向图
- DijkstraSP：Dijkstra算法
- BellmanFordSP：BellmanFord算法
### flow 流量
- FordFulkerson：Ford-Fulkerson最大流量算法

## grokking 算法图解
- BinarySearch：二分查找
- SelectionSort：选择排序
- QuickSort：快速排序
- BreadthFirstSearch：广度优先搜索
- DijkstraAlgorithm：Dijkstra算法
- SetCoverage：贪婪算法解决集合覆盖
- LongestCommonSubsequence：最长公共子序列

## daily 随手
- BubbleSort：冒泡排序

## stackandqueue 栈和队列
- GetMinStack：有getMin()功能的栈
- TwoStacksImplementQueue：两个栈实现队列
- ReverseStackUsingRecursive：使用递归将栈逆置
- DogCatQueue：猫狗队列
- StackSortStack：用一个栈实现另一个栈的排序
- HanoiStack：汉诺塔问题
- SlidingWindowMaxArray：生成窗口最大值数组
- MaxTree：构造数组的MaxTree
- MaxRectangle：最大子矩阵
- AllLessNumSubArray：最大最小值之差小于等于num的子数组数量

## list 链表
- CommonPart：两个有序链表的公共部分
- RemoveLastKthNode：删除单链表和双链表倒数第K个节点
- RemoveNodeByRatio：删除链表中间节点和指定比例处节点
- ReverseList：反转单链表和双向链表
- ReversePartList：反转部分单链表
- JosephusProblem：环形单链表的约瑟夫问题
- IsPalindromeList：判断链表是否为回文结构
- PartitionList：按照给定值划分链表
- CopyListWithRandom：复制含有随机指针节点的链表
- AddList：将两个链表生成相加链表
- FindIntersect：两个链表的第一个相交节点
- ConvertEveryGroup：将每K个节点分组逆序
- RemoveRepetition：删除值重复的节点
- RemoveGivenValue：删除给定值
- BinaryTreeToList：二叉树转换为双向链表
- ListSelectionSort：对链表进行选择排序
- RemoveNodeWired：怪异的节点删除方式
- InsertToCircularList：有序环形单链表中插入新节点
- MergeTwoLinkedLists：合并两个有序单链表
- RelocateLinkedList：按照左右半区重新组合单链表

## binarytree 二叉树
- TreeTraversal：二叉树遍历
- PrintEdgeNodes：打印二叉树边界节点
- PrintBinaryTree：直观的打印二叉树
- SerializeTree：二叉树序列化和反序列化
- MorrisTraversal：Morris二叉树遍历
- LongestPathSum：累加和为指定值的最长路径长度
- BiggestSubBinarySearchTree：最大搜索二叉子树
- BiggestBinarySearchTreeTopology：符合搜索二叉树的最大拓扑结构
- PrintBinaryTreeByLevel：按层打印二叉树
- RecoverBinarySearchTree：调整搜索二叉树中的两个错误节点
- ContainsTopology：一棵树是否包含另一棵树全部的拓扑结构
- SubtreeEquals：一棵树是否有与另一棵树拓扑结构完全相同的子树
- IsBalancedTree：判断二叉树是否为平衡二叉树
- IsBinarySearchTree：判断二叉树是否为搜索二叉树
- IsCompleteBinaryTree：判断二叉树是否为完全二叉树
- PosArrayToBinarySearchTree：根据后序数组重建二叉搜索树
- SortedArrayToBalancedBinarySearchTree：有序数组生成平衡搜索二叉树
- DescendantNode：二叉树中找到一个节点的后继节点
- NearestCommonAncestor：二叉树中两个节点最近的公共祖先
- TarjanAndDisjointSetsForNCA：Tarjan算法与并查集解决二叉树节点间最近公共祖先的批量查询
- MaxDistanceInTree：二叉树节点之间的最大距离
- PreInPosArrayToTree：先序、中序和后序数组两两结合重建二叉树
- PreInArrayToPosArray：通过先序和中序数组生成后序数组
- UniqueBinarySearchTree：统计和生成所有不同的二叉树
- CompleteTreeNodeNumber：统计完全二叉树的节点数

## recursionanddp 递归和动态规划(dynamicprogramming)
- Fibonacci：斐波那契数列系列问题
- MinPathSum：矩阵的最小路径和
- CoinsMin：换钱的最小货币数
- CoinsWay：换钱的方法数
- LongestIncreaseSubSequence：最长递增子序列
- LongestCommonSubSequence：最长公共子序列
- LongestCommonSubString：最长公共子串
- HanoiTower：汉诺塔问题
- EditCost：最小编辑代价
- StringCross：字符串的交错组成
- DungeonGame：龙与地下城游戏问题
- NumberToAlphabet：数字字符串转换为字符组合的种数
- ExpressionNumber：表达式得到期望结果的组成种数
- CardsInLine：排成一条线的纸牌博弈问题
- JumpGame：跳跃游戏
- LongestConsecutive：数组中的最长连续序列
- NQueens：N皇后问题

## string 字符串
- IsDeformation：两个字符串是否互为变形词
- IsRotation：两个字符串是否互为旋转词
- AllNumbersSum：字符串中所有数字子串的求和
- RemoveKZeros：去掉字符串中连续出现k个0的子串
- ConvertStringToInteger：整数字符串转成整数值
- ConvertStringToCount：字符串的统计字符串
- ReplaceString：替换字符串中连续出现的指定字符
- IsAllUnique：判断字符数组中是否所有的字符都只出现过一次
- FindStringInContainsNullArray：在有序但包含空的数组中查找字符串
- ModifyAndReplace：字符串调整与替换
- RotateString：翻转字符串
- MinDistance：数组中两个字符串的最小距离
- PalindromeString：添加最少字符使字符串变成回文字符串
- PalindromeMinCut：回文最小分割数
- ParenthesesMatch：括号字符串的有效性和最长有效长度
- ExpressionCompute：公式字符串求值
- ZeroLeftOneStringNumber：0左边必有1的二进制字符串数量
- LowestLexicography：拼接所有字符串产生字典顺序最小的字符串
- LongestNoRepeatSubstring：字符串中最长无重复子串的长度
- FindNewTypeChar：找到指定位置的新类型字符
- MinWindowLength：最小包含子串的长度
- RegularExpressionMatch：字符串匹配问题
- TrieTree：字典树(前缀树)的实现

## bitoperation 位运算
- SwapWithoutTmp：不用额外遍历交换两个整数的值
- GetMax：不用任何比较判断找出两个数中较大的数
- AddMinusMultiDivideByBit：只用位运算实现正数的加减乘除运算
- NumberOfOne：整数的二进制中有多少个1
- EvenTimesOddTimes：其他数出现偶数次的数组中找到出现奇数次的数
- KTimesOneTime：其他数都出现k次的数组中找到只出现一次的数

## arrayandmatrix 数组和矩阵
- PrintMatrixSpiralOrder：转圈打印矩阵
- RotateMatrix：将正方形矩阵顺时间转动90度
- PrintMatrixZigZag：之字形打印矩阵
- FindMinKNumbers：找到无序数组中最小的k个数
- MinLengthForSort：需要排序的最短子数组长度
- FindKMajority：数组中找到出现次数大于N/K的数
- FindNumInSortedMatrix：在行列都有序的矩阵中找数
- LongestIntegratedLength：最长可整合子数组长度
- PrintUniquePairAndTriad：获取有序数组中相加和为给定值的不重复二元组和三元组
- LongestSumSubArrayLength：无序数组中累加和为给定值的最长子数组长度
- LongestLessSumSubArrayLength：无序数组中累加和小于等于给定值的最长子数组长度
- SmallSum：计算数组的小和
- SortNaturalNumberArray：自然数数组的排序
- EvenInEvenOddInOdd：奇数位置都是奇数或者偶数位置都是偶数
- SubArrayMaxSum：子数组的最大累加和
- SubMatrixMaxSum：子矩阵的最大累加和
- FindOneLessValueIndex：在数组中找到一个局部最小的位置
