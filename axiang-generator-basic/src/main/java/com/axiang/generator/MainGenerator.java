package com.axiang.generator;

import com.axiang.model.MainTemplateConfig;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;

/**
 * @description；
 * @author:mar1
 * @data:2024/05/16
 **/
public class MainGenerator {
    public static void main(String[] args) throws IOException, TemplateException {

        String projectPath = System.getProperty("user.dir");
        System.out.println(projectPath);
        // 输入路径
        String inputPath = projectPath + File.separator + "axiang-generator-demo-projects"+ File.separator+"acm-template";
        // 输出路径
        String outputPath = projectPath;
        // 保存文件
        StaticGenerator.copyFileByRecursive(new File(inputPath), new File(outputPath));

        //d动态生成
        String dynamicInputPath = projectPath + File.separator + "axiang-generator-basic"+File.separator+"src/main/resources/templates/MainTemplate.java.ftl";
        String dynamicOutputPath = projectPath + File.separator + "acm-template/src/com/axiang/acm/MainTemplate.java";

        MainTemplateConfig mainTemplateConfig = new MainTemplateConfig();
        mainTemplateConfig.setAuthor("mar1");
        mainTemplateConfig.setOutputText("最终结果：");
        mainTemplateConfig.setLoop(false);
        DynamicGenerator.doGenerate(dynamicInputPath, dynamicOutputPath, mainTemplateConfig);
    }
}
