/**
 * <p>Title: FlowUtils</p>
 * <p>Description: </p>
 * <p>Created by wangzi on 2018/8/26</p>
 * <p>Emil: ustcwangzi@foxmail.com</p>
 * <p>WebSite: https://github.com/ustcwangzi/</p>
 */
package com.wz.utils;

import com.wz.flow.FlowEdge;
import com.wz.flow.FlowNetwork;

/**
 * <p>流量工具类</p>
 *
 * @author wangzi
 */
public class FlowUtils {
    public static FlowNetwork initFlowNetwork() {
        FlowNetwork network = new FlowNetwork(6);
        network.addEdge(new FlowEdge(0, 1, 2));
        network.addEdge(new FlowEdge(0, 2, 3));
        network.addEdge(new FlowEdge(1, 3, 3));
        network.addEdge(new FlowEdge(1, 4, 1));
        network.addEdge(new FlowEdge(2, 3, 1));
        network.addEdge(new FlowEdge(2, 4, 1));
        network.addEdge(new FlowEdge(3, 5, 2));
        network.addEdge(new FlowEdge(4, 5, 3));
        return network;
    }
}
