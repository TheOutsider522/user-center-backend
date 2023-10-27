package com.song.usercenter.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.song.usercenter.common.ErrorCode;
import com.song.usercenter.constant.UserConstant;
import com.song.usercenter.exception.BusinessException;
import com.song.usercenter.model.domain.User;
import com.song.usercenter.service.UserService;
import com.song.usercenter.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 用户服务实现类
 * @author TheOutsider
 */

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    /**
     * 盐值 混淆密码
     */
    private static final String SALT = "song";

    /**
     * 用户注册
     * @param userAccount 用户账户
     * @param userPassword 用户密码
     * @param checkPassword 校验密码
     * @param registerCode 注册编号
     * @return
     */
    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword, String registerCode) {
        // 1.校验
        if (StringUtils.isAllBlank(userAccount, userPassword, checkPassword, registerCode)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if(userAccount.length() < UserConstant.USER_ACCOUNT_MIN_LENGTH) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号过短");
        }
        if(userPassword.length() < UserConstant.PASSWORD_MIN_LENGTH || checkPassword.length() < UserConstant.PASSWORD_MIN_LENGTH) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过短");
        }
        if(registerCode.length() > UserConstant.REGISTER_CODE_MAX_LENGTH) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "注册编号过长");
        }

        // 账户不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？ ]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账户中包含特殊字符");
        }

        // 密码与校验密码相同
        if (!checkPassword.equals(userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次输入的密码不一致");
        }

        // 账户不能重复
        QueryWrapper<User> userAccountWrapper = new QueryWrapper();
        userAccountWrapper.eq("userAccount", userAccount);
        long count = count(userAccountWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
        }

        // 注册编号不能重复
        QueryWrapper<User> registerCodeWrapper = new QueryWrapper<>();
        registerCodeWrapper.eq("registerCode", registerCode);
        long registerCodeCount = count(registerCodeWrapper);
        if(registerCodeCount > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "注册编号已存在");
        }

        // 2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());

        // 插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        user.setRegisterCode(registerCode);
        boolean saveResult = save(user);
        if (!saveResult) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号保存错误");
        }
        return user.getId();
    }

    /**
     * 用户登录
     * @param userAccount
     * @param userPassword
     * @param request
     * @return
     */
    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // 1.校验
        if (StringUtils.isAllBlank(userAccount, userPassword)){
            return null;
        }
        if(userAccount.length() < UserConstant.USER_ACCOUNT_MIN_LENGTH) {
            return null;
        }
        if(userPassword.length() < UserConstant.PASSWORD_MIN_LENGTH) {
            return null;
        }
        // 账户不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？ ]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()){
            return null;
        }

        // 2.加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());

        // 查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPassword);
        User user = getOne(queryWrapper);

        // 用户不存在
        if(user == null) {
            log.info("user login failed, userAccount cannot match userPassword");
            return null;
        }

        // 3.用户脱敏
        User safetyUser = getSafetyUser(user);

        // 4.记录用户的登录态
        request.getSession().setAttribute(UserConstant.USER_LOGIN_STATUS, safetyUser);

        return safetyUser;
    }

    /**
     * 搜索用户
     * @param username
     * @param request
     * @return
     */
    @Override
    public List<User> searchUsers(String username, HttpServletRequest request) {
        // 鉴权
        Object userObject = request.getSession().getAttribute(UserConstant.USER_LOGIN_STATUS);
        User user = (User) userObject;
        if(user == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN, "用户未登录");
        }
        if(user.getUserRole() != UserConstant.ADMIN_ROLE) {
            throw new BusinessException(ErrorCode.NO_AUTH, "不是管理员");
        }
        // 搜索
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNoneBlank(username)) {
            queryWrapper.like("username", username);
        }
        List<User> userList = list(queryWrapper);
        return userList.stream()
                .map(u -> getSafetyUser(u))
                .collect(Collectors.toList());
    }

    /**
     * 用户脱敏
     * @param originUser
     * @return
     */
    @Override
    public User getSafetyUser(User originUser){
        if (originUser == null){
            return null;
        }
        User safetyUser = new User();
        safetyUser.setId(originUser.getId());
        safetyUser.setUsername(originUser.getUsername());
        safetyUser.setUserAccount(originUser.getUserAccount());
        safetyUser.setAvatarUrl(originUser.getAvatarUrl());
        safetyUser.setGender(originUser.getGender());
        safetyUser.setPhone(originUser.getPhone());
        safetyUser.setEmail(originUser.getEmail());
        safetyUser.setUserStatus(originUser.getUserStatus());
        safetyUser.setCreateTime(originUser.getCreateTime());
        safetyUser.setUserRole(originUser.getUserRole());
        safetyUser.setRegisterCode(originUser.getRegisterCode());
        return safetyUser;
    }

    /**
     * 用户注销
     * @param request
     * @return
     */
    @Override
    public int userLogout(HttpServletRequest request) {
        // 移除用户的登录态
        request.getSession().removeAttribute(UserConstant.USER_LOGIN_STATUS);
        return 1;
    }
}
