package com.song.usercenter.service;

import com.song.usercenter.mapper.UserMapper;
import com.song.usercenter.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;

/**
 * 用户服务测试
 */
@SpringBootTest
public class UserServiceTest {

    @Resource
    private UserService userService;

    @Resource
    private UserMapper userMapper;

    @Test
    public void testAddUser(){
        // User user = new User();
        // user.setUsername("song");
        // user.setUserAccount("song");
        // user.setAvatarUrl("avatar");
        // user.setGender(0);
        // user.setUserPassword("12345678");
        // user.setPhone("18300206590");
        // user.setEmail("18300206590@163.com");
        // boolean result = userService.save(user);
        // System.out.println(user.getId());
        // Assertions.assertTrue(result);
    }

    @Test
    void userRegister() {
        // // 非空判断
        // String userAccount = "song";
        // String userPassword = "";
        // String checkPassword = "123456";
        // String registerCode = "1";
        // long result = userService.userRegister(userAccount, userPassword, checkPassword, registerCode);
        // Assertions.assertEquals(-1, result);
        //
        // // 账户长度不能小于4位
        // userAccount = "sxl";
        // userPassword = "12345678";
        // checkPassword = "12345678";
        // result = userService.userRegister(userAccount, userPassword, checkPassword, registerCode);
        // Assertions.assertEquals(-1, result);
        //
        // // 密码不小于8位
        // userAccount = "sxl";
        // userPassword = "123456";
        // checkPassword = "123456";
        // result = userService.userRegister(userAccount, userPassword, checkPassword, registerCode);
        // Assertions.assertEquals(-1, result);
        //
        // // 账户不能重复
        // userAccount = "song";
        // userPassword = "12345678";
        // checkPassword = "12345678";
        // result = userService.userRegister(userAccount, userPassword, checkPassword, registerCode);
        // Assertions.assertEquals(-1, result);
        //
        // // 账户不包含特殊字符
        // userAccount = "song xl";
        // userPassword = "12345678";
        // checkPassword = "12345678";
        // result = userService.userRegister(userAccount, userPassword, checkPassword, registerCode);
        // Assertions.assertEquals(-1, result);
        //
        // // 密码与校验密码相同
        // userAccount = "songxueliang";
        // userPassword = "12345678";
        // checkPassword = "123456789";
        // result = userService.userRegister(userAccount, userPassword, checkPassword, registerCode);
        // Assertions.assertEquals(-1, result);
        //
        // // 正常插入
        // userAccount = "songAdmin";
        // userPassword = "123456789";
        // checkPassword = "123456789";
        // result = userService.userRegister(userAccount, userPassword, checkPassword, registerCode);
        // Assertions.assertTrue(result > 0);
    }

    @Test
    void userLogin() {
        // User user = new User();
        // user.setUserAccount("song");
        // user.setUserPassword("12345678");
        // String encryptPassword = DigestUtils.md5DigestAsHex(("song" + user.getUserPassword()).getBytes());
        // user.setUserPassword(encryptPassword);
        // int insert = userMapper.insert(user);
        // Assertions.assertEquals(1, insert);
    }
}