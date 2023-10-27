package com.song.usercenter.constant;

/**
 * 用户常量
 */
public interface UserConstant {

    /**
     * 用户登陆态键
     */
    String USER_LOGIN_STATUS = "userLoginStatus";

    /**
     * 权限:管理员权限
     */
    int ADMIN_ROLE = 1;

    /**
     * 权限: 默认权限
     */
    int DEFAULT_ROLE = 0;

    /**
     * 用户账户最短长度
     */
    int USER_ACCOUNT_MIN_LENGTH = 4;

    /**
     * 用户密码最短长度
     */
    int PASSWORD_MIN_LENGTH = 8;

    /**
     * 注册编号最长长度
     */
    int REGISTER_CODE_MAX_LENGTH = 5;
}
