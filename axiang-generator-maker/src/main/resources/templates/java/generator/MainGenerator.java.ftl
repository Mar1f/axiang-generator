package ${basePackage}.generator;
import ${basePackage}.model.DataModel;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;

/**
 * @description；
 * @author:mar1
 * @data:2024/05/16
 **/
public class MainGenerator {
    /**
     * 生成
     *
     * @param model 数据模型
     * @throws TemplateException
     * @throws IOException
     */
    public static void doGenerate(DataModel model) throws TemplateException, IOException {
        String inputRootPath = "${fileConfig.inputRootPath}";
        String outputRootPath ="${fileConfig.outputRootPath}";

        String inputPath;
        String outputPath;
    <#list modelConfig.models as modelInfo>
        ${modelInfo.type} ${modelInfo.fieldName} = model.${modelInfo.fieldName};
    </#list>

    <#list fileConfig.files as fileInfo>
        <#if fileInfo.groupKey??>
        // group = ${fileInfo.groupKey}
        <#if fileInfo.condition??>
        if(${fileInfo.condition}){
            <#list fileInfo.files as fileInfo>
                inputPath = new File(inputRootPath, "${fileInfo.inputPath}").getAbsolutePath();
                outputPath =  new File(outputRootPath, "${fileInfo.outputPath}").getAbsolutePath();
                <#if fileInfo.generateType == "static">
                    StaticGenerator.copyFilesByHutool(inputPath, outputPath);
                <#else>
                    DynamicGenerator.doGenerate(inputPath,outputPath,model);
                </#if>
            </#list>
         }
        <#else>
            <#list fileInfo.files as fileInfo>
                inputPath = new File(inputRootPath, "${fileInfo.inputPath}").getAbsolutePath();
                outputPath =  new File(outputRootPath, "${fileInfo.outputPath}").getAbsolutePath();
                <#if fileInfo.generateType == "static">
                    StaticGenerator.copyFilesByHutool(inputPath, outputPath);
                <#else>
                    DynamicGenerator.doGenerate(inputPath,outputPath,model);
                </#if>
            </#list>
        </#if>
        <#else>
        </#if>
        <#if fileInfo.condition??>
            if(${fileInfo.condition}){
            inputPath = new File(inputRootPath, "${fileInfo.inputPath}").getAbsolutePath();
            outputPath =  new File(outputRootPath, "${fileInfo.outputPath}").getAbsolutePath();
            <#if fileInfo.generateType == "static">
               StaticGenerator.copyFilesByHutool(inputPath, outputPath);
            <#else>
              DynamicGenerator.doGenerate(inputPath,outputPath,model);
            </#if>
            }
        <#else>
            inputPath = new File(inputRootPath, "${fileInfo.inputPath}").getAbsolutePath();
            outputPath =  new File(outputRootPath, "${fileInfo.outputPath}").getAbsolutePath();
            <#if fileInfo.generateType == "static">
                StaticGenerator.copyFilesByHutool(inputPath, outputPath);
            <#else>
                DynamicGenerator.doGenerate(inputPath,outputPath,model);
            </#if>
        </#if>
    </#list>
    }
}
