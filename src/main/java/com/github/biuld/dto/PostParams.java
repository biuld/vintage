package com.github.biuld.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PostParams extends Params {

    @ApiModelProperty("作者")
    private Integer userId;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("标签")
    private String tags;

    @ApiModelProperty("关键字（多个用英文逗号隔开）")
    private String keyword;

    @ApiModelProperty("最新文章：1-今日，2-今周，3-今月, 4-今年")
    private Integer sortByTime;
}
