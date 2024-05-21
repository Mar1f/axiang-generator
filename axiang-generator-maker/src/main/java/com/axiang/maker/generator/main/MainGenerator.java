package com.axiang.maker.generator.main;


import freemarker.template.TemplateException;

import java.io.IOException;

/**
 * @description；
 * @author:mar1
 * @data:2024/05/19
 **/
public class MainGenerator extends GenerateTemplate{
    @Override
    protected void buildDist(String outputPath, String sourceCopyDestPath, String jarPath, String shellOutputFilePath) {
        System.out.println("不要输出dist了");
    }

}
