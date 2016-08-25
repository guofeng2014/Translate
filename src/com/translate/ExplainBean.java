package com.translate;

import java.util.List;

/**
 * 作者: guofeng
 * 日期: 16/8/25.
 */
public class ExplainBean {
    public Basic basic;
    public List<String> translation;
    public int errorCode;

    class Basic {
        public List<String> explains;
    }
}
