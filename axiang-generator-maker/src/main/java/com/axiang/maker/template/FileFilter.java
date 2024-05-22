package com.axiang.maker.template;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.replacer.StrReplacer;
import com.axiang.maker.template.enums.FileFilterRangeEnum;
import com.axiang.maker.template.enums.FileFilterRuleEnum;
import com.axiang.maker.template.model.FileFilterConfig;
import com.sun.org.apache.bcel.internal.generic.RETURN;

import javax.print.DocFlavor;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description；
 * @author:mar1
 * @data:2024/05/22
 **/
public class FileFilter {
    /**
     * 对某个文件或者目录进行过滤，返回文件列表
     * @param filePath
     * @param fileFilterConfigList
     * @return
     */
    public static List<File> doFilter(String filePath, List<FileFilterConfig> fileFilterConfigList){
            List<File> fileList = FileUtil.loopFiles(filePath);
            return fileList.stream()
                    .filter(file -> doSingFileFilter(fileFilterConfigList,file))
                    .collect(Collectors.toList());
    }
    public static boolean doSingFileFilter(List<FileFilterConfig> fileFilterConfigList, File file){
        String fileName = file.getName();
        String fileContent = FileUtil.readUtf8String(file);

        //所有过滤器校验结束后的结果
        boolean result = true;

        if(CollUtil.isEmpty(fileFilterConfigList)){
            return true;
        }
        for(FileFilterConfig fileFilterConfig : fileFilterConfigList){
            String range = fileFilterConfig.getRange();
            String rule = fileFilterConfig.getRule();
            String value = fileFilterConfig.getValue();

            FileFilterRangeEnum fileFilterRangeEnum = FileFilterRangeEnum.getEnumByValue(range);
            if(fileFilterRangeEnum == null){
                continue;
            }

            String content = fileName;
            switch (fileFilterRangeEnum){
                case FILE_NAME:
                    content = fileName;
                    break;
                case FILE_CONTENT:
                    content = fileContent;
                    break;
                default:
            }

            FileFilterRuleEnum fileFilterRuleEnum = FileFilterRuleEnum.getEnumByValue(rule);
            if(fileFilterRuleEnum == null){
                continue;
            }
            switch (fileFilterRuleEnum){
                case CONTAINS:
                    result = content.contains(value);
                    break;
                case STARTS_WITH:
                    result = content.startsWith(value);
                    break;
                case ENDS_WITH:
                    result = content.endsWith(value);
                    break;
                case REGEX:
                    result = content.matches(value);
                    break;
                case EQUALS:
                    result = content.equals(value);
                    break;
            }
            if(!result){
                return false;
            }
        }
        return true;
    }
}
