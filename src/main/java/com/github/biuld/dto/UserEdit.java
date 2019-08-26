package com.github.biuld.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by biuld on 2019/8/27.
 */
@Data
public class UserEdit {

    @ApiModelProperty(hidden = true)
    private Integer id;

    private String avatar;

    private String password;

    private String email;
}
