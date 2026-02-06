package com.sky.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "Data Transfer Object model for Employee login function")
public class EmployeeLoginDTO implements Serializable {

    @ApiModelProperty(value = "username")
    private String username;

    @ApiModelProperty("password")
    private String password;
}
