package com.song.usercenter.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录请求体
 *
 * @author TheOutsider
 */
@Data
public class UserLoginRequest implements Serializable {
    private static final long serialVersionUID = -4718392848688758288L;

    private String userAccount;
    private String userPassword;
}
