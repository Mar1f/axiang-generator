package com.axiang.maker.template.enums;

import cn.hutool.core.util.ObjectUtil;

/**
 * @description；文件过滤范围枚举
 * @author:mar1
 * @data:2024/05/22
 **/

public enum FileFilterRangeEnum {

    FILE_NAME("文件名称", "fileName"),
    FILE_CONTENT("文件内容", "fileContent");

    private final String text;

    private final String value;

    FileFilterRangeEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public String getValue() {
        return value;
    }

    public static FileFilterRangeEnum getEnumByValue(String value){
        if(ObjectUtil.isEmpty(value)){
            return null;
        }
        for(FileFilterRangeEnum anEnum : FileFilterRangeEnum.values()){
            if(anEnum.value.equals(value)){
                return anEnum;
            }
        }
        return null;
    }
}