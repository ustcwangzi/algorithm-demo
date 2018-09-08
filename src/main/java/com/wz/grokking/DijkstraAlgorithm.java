/**
 * <p>Title: DijkstraAlgorithm</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/9/8</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.grokking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Dijkstra算法</p>
 * <p>
 *     Dijkstra算法不能处理含有"负权值"的图
 *     因为当一个图含有负权值时，处理新节点时可能会更新已经"处理过的"节点
 *     而Dijkstra算法中对于"处理过的"节点不会再进行更新操作
 * </p>
 *
 * @author wangzi
 */
public class DijkstraAlgorithm {
    /**
     * 表示图
     */
    private static Map<Integer, Map<Integer, Double>> graph = new HashMap<>();
    /**
     * 已处理过的的节点
     */
    private static List<Integer> processed = new ArrayList<>();

    private static void findShortestPath(int source, int target){

    }

    private static Integer findShortestDistancesNode(Map<Integer, Double> distances){
        Double shortestDistance = Double.POSITIVE_INFINITY;
        Integer shortestDistanceNode = null;

        for (Map.Entry<Integer, Double> node : distances.entrySet()){
            double distance = node.getValue();
            if (distance < shortestDistance && !processed.contains(node.getKey())){
                shortestDistance = distance;
                shortestDistanceNode = node.getKey();
            }
        }

        return shortestDistanceNode;
    }
}
