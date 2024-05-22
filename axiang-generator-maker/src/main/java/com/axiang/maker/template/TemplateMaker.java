package com.axiang.maker.template;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONTokener;
import cn.hutool.json.JSONUtil;
import com.axiang.maker.meta.Meta;
import com.axiang.maker.meta.enums.FileGenerateTypeEnum;
import com.axiang.maker.meta.enums.FileTypeEnum;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class TemplateMaker {
    /**
     *
     * @param newMeta
     * @param originProjectPath
     * @param inputFilePathList
     * @param modelInfo
     * @param searchStr
     * @param id
     * @return
     */
    private static long makeTemplate(Meta newMeta, String originProjectPath,List<String> inputFilePathList, Meta.ModelConfig.ModelInfo modelInfo, String searchStr, Long id) {
        if (id == null) {
            id = IdUtil.getSnowflakeNextId();
        }

        // 复制目录

        String projectPath = System.getProperty("user.dir");
        String tempDirPath = projectPath + File.separator + ".temp";
        String templatePath = tempDirPath + File.separator + id;
        // 是否为首次制作模板
        // 目录不存在，则是首次制作
        if (!FileUtil.exist(templatePath)) {
            FileUtil.mkdir(templatePath);
            FileUtil.copy(originProjectPath, templatePath, true);
        }

        //2.输入文件信息
        // 要挖坑的项目根目录
        File tempFile = new File(templatePath);
        templatePath = tempFile.getAbsolutePath();
        String sourceRootPath = templatePath + File.separator + FileUtil.getLastPathEle(Paths.get(originProjectPath)).toString();
        //win 要转义
        sourceRootPath = sourceRootPath.replaceAll("\\\\","/");

        List<Meta.FileConfig.FileInfo> newFileInfoList = new ArrayList<>();
        // 遍历输入文件
        for(String inputFilePath : inputFilePathList){
            String inputFileAbsolutePath = sourceRootPath + File.separator + inputFilePath;

            if(FileUtil.isDirectory(inputFileAbsolutePath)){
                List<File> fileList = FileUtil.loopFiles(inputFileAbsolutePath);
                for(File file : fileList){
                    Meta.FileConfig.FileInfo fileInfo = makerFileTemplate( modelInfo, searchStr, sourceRootPath,file);
                    newFileInfoList.add(fileInfo);
                }
            }
            else{
                Meta.FileConfig.FileInfo fileInfo = makerFileTemplate( modelInfo, searchStr, sourceRootPath,new File(inputFileAbsolutePath));
                newFileInfoList.add(fileInfo);
            }
        }


        // 生成配置文件
        String metaOutputPath = sourceRootPath + File.separator + "meta.json";
        // 如果已有 meta 文件，说明不是第一次制作，则在 meta 基础上进行修改
        if (FileUtil.exist(metaOutputPath)) {
            Meta oldMeta = JSONUtil.toBean(FileUtil.readUtf8String(metaOutputPath), Meta.class);

            //1。追加配置参数
            List<Meta.FileConfig.FileInfo> fileInfoList = oldMeta.getFileConfig().getFiles();
            fileInfoList.addAll(newFileInfoList);

            List<Meta.ModelConfig.ModelInfo> modelInfoList = oldMeta.getModelConfig().getModels();
            modelInfoList.add(modelInfo);

            //配置去重
            oldMeta.getFileConfig().setFiles((distinctFile(fileInfoList)));
            oldMeta.getModelConfig().setModels(distinctModel(modelInfoList));

            //2.输出元信息文件
            FileUtil.writeUtf8String(JSONUtil.toJsonPrettyStr(oldMeta), metaOutputPath);
        } else {


            Meta.FileConfig fileConfig = new Meta.FileConfig();
            newMeta.setFileConfig(fileConfig);
            fileConfig.setSourceRootPath(sourceRootPath);
            List<Meta.FileConfig.FileInfo> fileInfoList = new ArrayList<>();
            fileConfig.setFiles(fileInfoList);


            fileInfoList.addAll(newFileInfoList);

            Meta.ModelConfig modelConfig = new Meta.ModelConfig();
            newMeta.setModelConfig(modelConfig);
            List<Meta.ModelConfig.ModelInfo> modelInfoList = new ArrayList<>();
            modelConfig.setModels(modelInfoList);
            modelInfoList.add(modelInfo);

            //2.输出元信息文件
            FileUtil.writeUtf8String(JSONUtil.toJsonPrettyStr(newMeta), metaOutputPath);
        }
        return id;
    }

    /**
     * 制作模板文件
     * @param modelInfo
     * @param searchStr
     * @param sourceRootPath
     * @param inputFile
     * @return
     */
    private static Meta.FileConfig.FileInfo makerFileTemplate(Meta.ModelConfig.ModelInfo modelInfo, String searchStr, String sourceRootPath, File inputFile) {
        String fileInputAbsolutePath = inputFile.getAbsolutePath();
        fileInputAbsolutePath = fileInputAbsolutePath.replaceAll("\\\\","/");

        //要挖坑的文件
        String fileInputPath = fileInputAbsolutePath.replace(sourceRootPath + "/", "");

        String fileOutputPath = fileInputPath + ".ftl";

        //二、使用字符串替换，生成模板文件
        String fileOutputAbsolutePath = fileInputAbsolutePath + ".ftl";

        String fileContent;
        //如果已有模板文件，在 模板基础上进行挖坑
        if (FileUtil.exist(fileOutputAbsolutePath)) {
            fileContent = FileUtil.readUtf8String(fileOutputAbsolutePath);
        } else {
            fileContent = FileUtil.readUtf8String(fileInputAbsolutePath);
        }
        String replacement = String.format("${%s}", modelInfo.getFieldName());
        String newFileContent = StrUtil.replace(fileContent, searchStr, replacement);


        // 文件配置信息
        Meta.FileConfig.FileInfo fileInfo = new Meta.FileConfig.FileInfo();
        fileInfo.setInputPath(fileInputPath);
        fileInfo.setGenerateType(FileGenerateTypeEnum.DYNAMIC.getValue());


        //和原文件内容一直，没有挖坑，静态生成
        if(newFileContent.equals(fileContent)){
            fileInfo.setOutputPath(fileInputPath);
            //输出路径 == 输入路径
            fileInfo.setGenerateType(FileGenerateTypeEnum.STATIC.getValue());
        }else{
            fileInfo.setGenerateType(FileGenerateTypeEnum.DYNAMIC.getValue());
            //输出模板文件
            FileUtil.writeUtf8String(newFileContent, fileOutputAbsolutePath);

        }

        return fileInfo;
    }

    /**
     * 文件去重
     *
     * @param args
     */
    public static void main(String[] args) {

        //1.构造配置参数对象
        Meta meta = new Meta();
        meta.setName("acm-template-generator");
        meta.setDescription("ACM 示例模板生成器");
        String projectPath = System.getProperty("user.dir");
        String originProjectPath = FileUtil.getAbsolutePath(new File(projectPath).getParentFile()) + File.separator + "axiang-generator-demo-projects/springboot-init";
        String fileInputPath1 = "src/main/java/com/yupi/springbootinit/common";
        String fileInputPath2 = "src/main/java/com/yupi/springbootinit/controller";
        List<String> inputFilePathList = Arrays.asList(fileInputPath1,fileInputPath2);
        //3.输入的模型参数信息
//        Meta.ModelConfig.ModelInfo modelInfo = new Meta.ModelConfig.ModelInfo();
//        modelInfo.setFieldName("outputText");
//        modelInfo.setType("String");
//        modelInfo.setDefaultValue("sum = ");

        //输入模型参数信息：
        Meta.ModelConfig.ModelInfo modelInfo = new Meta.ModelConfig.ModelInfo();
        modelInfo.setFieldName("className");
        modelInfo.setType("String");
        //首次替换变量
//        String searchStr = "Sum: ";
        String searchStr = "BaseResponse";

        long id = TemplateMaker.makeTemplate(meta, originProjectPath, inputFilePathList, modelInfo, searchStr, null);
        System.out.println(id);

    }

    private static List<Meta.FileConfig.FileInfo> distinctFile(List<Meta.FileConfig.FileInfo> fileInfoList) {
        List<Meta.FileConfig.FileInfo> newFileInfoList = new ArrayList<>(fileInfoList.stream()
                .collect(
                        Collectors.toMap(Meta.FileConfig.FileInfo::getInputPath, o -> o, (e, r) -> r)
                ).values());
        return newFileInfoList;
    }

    /**
     * 模型去重
     *
     * @param modelInfoList
     * @return
     */
    private static List<Meta.ModelConfig.ModelInfo> distinctModel(List<Meta.ModelConfig.ModelInfo> modelInfoList) {
        List<Meta.ModelConfig.ModelInfo> newModelInfoList = new ArrayList<>(modelInfoList.stream()
                .collect(
                        Collectors.toMap(Meta.ModelConfig.ModelInfo::getFieldName, o -> o, (e, r) -> r)
                ).values());
        return newModelInfoList;
    }

}