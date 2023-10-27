package com.song.usercenter.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 * @author TheOutsider
 */
@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = 1731989672650203573L;

    private String userAccount;
    private String userPassword;
    private String checkPassword;
    private String registerCode;
}
