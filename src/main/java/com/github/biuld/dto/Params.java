package com.github.biuld.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Params {

    @ApiModelProperty("页码")
    private Integer pageNum=1;

    @ApiModelProperty("每页数量")
    private Integer pageSize=20;
}
