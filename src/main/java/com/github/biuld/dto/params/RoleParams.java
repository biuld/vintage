package com.github.biuld.dto.params;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RoleParams extends Params{

    private Integer userId;
}
