package com.axiang.maker.meta;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.json.JSONUtil;

/**
 * @description；
 * @author:mar1
 * @data:2024/05/17
 **/
public class MetaManager {
    // 单例模式
    private  static  volatile Meta meta;
    public static Meta getMetaObject(){
        if(meta == null)
        synchronized (MetaManager.class) {
            if(meta == null ) {
                meta = initMeta();
            }
        }
        return meta;
    }
    private static Meta initMeta(){

        String metaJson = ResourceUtil.readUtf8Str("meta.json");
        Meta newMeta = JSONUtil.toBean(metaJson,Meta.class);
        //校验和处理默认值
        MetaValidator.doValidAndFill((newMeta));
        return newMeta;
    }
}
