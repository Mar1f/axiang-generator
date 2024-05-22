package com.axiang.maker.template.enums;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import org.omg.CORBA.PUBLIC_MEMBER;

/**
 * @description；文件过滤规则枚举
 * @author:mar1
 * @data:2024/05/22
 **/

public enum FileFilterRuleEnum {

    CONTAINS("包含", "contains"),
    STARTS_WITH("前缀匹配", "startsWith"),

    ENDS_WITH("后缀匹配", "endsWith"),
    REGEX("正则", "regex"),
    EQUALS("相等", "equals");

    private final String text;

    private final String value;

    FileFilterRuleEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public String getValue() {
        return value;
    }

    public static FileFilterRuleEnum getEnumByValue(String value){
        if(ObjectUtil.isEmpty(value)){
            return null;
        }
        for(FileFilterRuleEnum anEnum : FileFilterRuleEnum.values()){
            if(anEnum.value.equals(value)){
                return anEnum;
            }
        }
        return null;
    }
}