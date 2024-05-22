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

    private TemplateMakerFileConfig fileConfig = new TemplateMakerFileConfig();

    private TemplateMakerModelConfig modelConfig = new TemplateMakerModelConfig();

    private TemplateMakerOutputConfig outputConfig = new TemplateMakerOutputConfig();
}
