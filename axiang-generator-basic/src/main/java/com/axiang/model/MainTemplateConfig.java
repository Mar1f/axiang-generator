package com.axiang.model;

import lombok.Data;

/**
 * @description；
 * @author:mar1
 * @data:2024/5/16
 **/
@Data
public class MainTemplateConfig {
    // 动态生成的需求
    // 1.开头加上作者注释（增加代码）
    // 2.修改程序输出的信息提示（替换代码）
    // 3.将循环读取输入改为单词读取（可选代码）
    /**
     * 填充值
     */
    private String author = "mar1";

    // 输出信息
    private String outputText = "输出结果";

    /***
     * 是否循环(开关)
     */
    private boolean loop;
}




