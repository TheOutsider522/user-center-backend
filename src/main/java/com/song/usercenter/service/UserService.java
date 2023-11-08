package com.song.usercenter.service;

import com.song.usercenter.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @description 用户服务
 * @author TheOutsider
 */
public interface UserService extends IService<User> {


    /**
     * 用户注册
     * @param userAccount 用户账户
     * @param userPassword 用户密码
     * @param checkPassword 校验密码
     * @param registerCode 注册编号
     * @return 新用户id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword, String registerCode);

    /**
     * 用户登录
     * @param userAccount
     * @param userPassword
     * @param request
     * @return
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户搜索
     * @param username
     * @param request
     * @return
     */
    List<User> searchUsers(String username, HttpServletRequest request);

    /**
     * 用户脱敏
     * @param originUser
     * @return
     */
    User getSafetyUser(User originUser);

    /**
     * 用户注销
     * @param request
     * @return
     */
    int userLogout(HttpServletRequest request);

    /**
     * 根据标签查询用户
     * @param tagNameList
     * @return
     */
    List<User> searchUserByTags(List<String> tagNameList);

    /**
     * 更新用户信息
     * @param user
     * @param loginUser
     * @return
     */
    Integer updateUser(User user, User loginUser);

    /**
     * 获取当前登录用户信息
     * @param request
     * @return User
     */
    User getLoginUser(HttpServletRequest request);

    /**
     * 校验用户是否为管理员
     * @param request
     * @return
     */
    boolean isAdmin(HttpServletRequest request);

    /**
     * 校验用户是否为管理员
     * @param loginUser
     * @return
     */
    boolean isAdmin(User loginUser);
}
