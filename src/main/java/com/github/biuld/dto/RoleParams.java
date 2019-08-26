package com.github.biuld.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RoleParams extends Params{

    private Integer userId;
}
