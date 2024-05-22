package com.axiang.maker.template.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.util.List;

/**
 * @description；
 * @author:mar1
 * @data:2024/05/22
 **/
@Data
public class TemplateMakerFileConfig {

    private List<FileInfoConfig> files;

    private FileGroupConfig fileGroupConfig;
    @NoArgsConstructor
    @Data
    public static class FileInfoConfig{
        private String path;
        private List<FileFilterConfig>FilterConfigList;
    }

    @Data
    public static class FileGroupConfig{
        private String condition;

        private String groupKey;

        private String groupName;
    }

}
