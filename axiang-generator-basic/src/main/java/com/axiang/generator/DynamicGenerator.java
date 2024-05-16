package com.axiang.generator;

/**
 * @description；
 * @author:mar1
 * @data:2024/05/16
 **/

import com.axiang.model.MainTemplateConfig;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * 动态文件生成
 */
public class DynamicGenerator {
    public static void main(String[] args) throws IOException, TemplateException {
        String projectPath = System.getProperty("user.dir") + File.separator + "axiang-generator-basic";
        String inputPath = projectPath + File.separator + "src/main/resources/templates/MainTemplate.java.ftl";
        String outputPath = projectPath + File.separator + "MainTemplate.java";

        MainTemplateConfig mainTemplateConfig = new MainTemplateConfig();
        mainTemplateConfig.setAuthor("mar1");
        mainTemplateConfig.setOutputText("最终结果：");
        mainTemplateConfig.setLoop(false);
        doGenerate(inputPath, outputPath, mainTemplateConfig);
    }

    /**
     *
     * @param inputPath
     * @param outputPath
     * @param model
     * @throws IOException
     * @throws TemplateException
     */

    public static void doGenerate(String inputPath, String outputPath, Object model) throws IOException, TemplateException{
        // new 出 Configuration 对象 ，参数为 FreeMarker 版本号
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_32);

        File templateDir = new File(inputPath).getParentFile();
        configuration.setDirectoryForTemplateLoading(templateDir);

        //设置模板文件使用的字符集
        configuration.setDefaultEncoding("utf-8");

        configuration.setNumberFormat("0.#####");

        //创建模板对象，加载指定的模板
        String templateName = new File(inputPath).getName();
        Template template = configuration.getTemplate(templateName);

        Writer out = new FileWriter(outputPath);

        template.process(model, out);

        //关闭文件
        out.close();
    }
}
