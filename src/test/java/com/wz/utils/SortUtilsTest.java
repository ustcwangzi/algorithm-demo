package com.wz.utils;

import org.junit.Assert;
import org.junit.Test;

/**
 * <p>单元测试</p>
 *
 * @author wangzi
 */
public class SortUtilsTest {
    @Test
    public void less() throws Exception {
        Assert.assertTrue(SortUtils.less('a', 'b'));
    }

    @Test
    public void exch() throws Exception {
        String[] a = new String[]{"a", "b"};
        SortUtils.show(a);
        SortUtils.exch(a, 0, 1);
        SortUtils.show(a);
    }

    @Test
    public void show() throws Exception {
        String[] a = new String[]{"a", "b"};
        SortUtils.show(a);
    }

    @Test
    public void isSorted() throws Exception {
        String[] a = new String[]{"a", "b"};
        Assert.assertTrue(SortUtils.isSorted(a));
    }

}