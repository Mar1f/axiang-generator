package com.axiang.maker.template.model;

import com.axiang.maker.meta.Meta;
import lombok.Data;

/**
 * @description；
 * @author:mar1
 * @data:2024/05/22
 **/
@Data
public class TemplateMakerConfig {

    private Long id;

    private Meta meta = new Meta();

    private String originProjectPath;

    TemplateMakerFileConfig fileConfig = new TemplateMakerFileConfig();

    TemplateMakerModelConfig modelConfig = new TemplateMakerModelConfig();

    TemplateMakerOutputConfig outputConfig = new TemplateMakerOutputConfig();
}