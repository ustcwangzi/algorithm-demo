/**
 * <p>Title: DijkstraAlgorithm</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/9/8</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.grokking;

import java.util.*;

/**
 * <p>Dijkstra算法</p>
 * <p>
 *     初始时，parentMap就是与source直接相邻的节点
 *     distanceMap就是与source直接相邻的节点的距离
 *     然后遍历distanceMap寻找最短路径，将其和已知最短路径作比较，
 *     更小的话则更新distanceMap和parentMap
 *     每处理完一个节点后，记录下来，后续的处理中不会再将这个节点考虑进去
 *     重复以上过程，得到最终的最短路径
 * </p>
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
    private static Map<String, Map<String, Double>> graph = new HashMap<>();
    /**
     * 保存已知路径的父节点
     */
    private static Map<String, String> parentMap = new HashMap<>();
    /**
     * 已处理过的的节点
     */
    private static List<String> processed = new ArrayList<>();
    /**
     * 最短距离
     */
    private static Map<String, Double> distanceMap = new HashMap<>();

    private static void findShortestPath(String source, String target) {
        distanceMap.putAll(graph.get(source));
        for (Map.Entry<String, Map<String, Double>> entry : graph.entrySet()) {
            distanceMap.putIfAbsent(entry.getKey(), Double.POSITIVE_INFINITY);
            for (String key : entry.getValue().keySet()) {
                parentMap.put(key, entry.getKey());
                distanceMap.putIfAbsent(key, Double.POSITIVE_INFINITY);
            }
        }

        String node = findShortestDistancesNode(distanceMap);
        while (node != null) {
            // 到达终点
            if (node.equals(target)) {
                distanceMap.put(source, 0.0);
                return;
            }

            // 已知最短路径
            Double distance = distanceMap.get(node);
            Map<String, Double> neighbors = graph.get(node);
            for (String key : neighbors.keySet()) {
                double newDistance = distance + neighbors.get(key);
                // 更新最短路径
                if (distanceMap.get(key) > newDistance) {
                    distanceMap.put(key, newDistance);
                    parentMap.put(key, node);
                }
            }
            // 已处理节点，后续不会再次处理
            processed.add(node);
            node = findShortestDistancesNode(distanceMap);
        }
    }

    /**
     * 遍历相邻节点，寻找最短路径
     */
    private static String findShortestDistancesNode(Map<String, Double> distances) {
        Double shortestDistance = Double.POSITIVE_INFINITY;
        String shortestDistanceNode = null;

        for (Map.Entry<String, Double> node : distances.entrySet()) {
            double distance = node.getValue();
            if (distance < shortestDistance && !processed.contains(node.getKey())) {
                shortestDistance = distance;
                shortestDistanceNode = node.getKey();
            }
        }

        return shortestDistanceNode;
    }

    private static void printPath(String source, String target) {
        Stack<String> pathStack = new Stack<>();
        pathStack.push(target);
        String w = parentMap.get(target);
        while (!w.equals(source)) {
            pathStack.push(w);
            w = parentMap.get(w);
        }
        pathStack.push(source);

        while (!pathStack.empty()) {
            String path = pathStack.pop();
            if (path.equals(target)) {
                System.out.print(path);
            } else {
                System.out.print(path + " -> ");
            }
        }
    }

    public static void main(String[] args) {
        graph.put("A", new HashMap<>());
        graph.get("A").put("B", 6.0);
        graph.get("A").put("C", 2.0);

        graph.put("B", new HashMap<>());
        graph.get("B").put("D", 1.0);

        graph.put("C", new HashMap<>());
        graph.get("C").put("B", 3.0);
        graph.get("C").put("D", 5.0);

        String source = "A", target = "D";
        findShortestPath(source, target);
        System.out.println(distanceMap);
        printPath(source, target);
    }
}
