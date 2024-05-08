package com.bite.blog.controller;

import com.bite.blog.model.Result;
import com.bite.blog.model.UserInfo;
import com.bite.blog.service.UserService;
import com.bite.blog.utils.JwtUtils;
import com.bite.blog.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/user")
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public Result login(String userName, String password){
        /**
         * 1. 参数校验
         * 2. 密码校验
         * 3. 生成token, 并返回
         */
        if (!StringUtils.hasLength(userName) || !StringUtils.hasLength(password)){
            return Result.fail("用户名或密码为空");
        }
        //获取数据库中的密码
        UserInfo userInfo = userService.queryByName(userName);
        if (userInfo==null || userInfo.getId()<0){
            return Result.fail("用户不存在");
        }
//        if (!password.equals(userInfo.getPassword())){
//            return Result.fail("密码错误!");
//        }
        if (!SecurityUtils.verify(password, userInfo.getPassword())){
            return Result.fail("密码错误!");
        }
        //生成token, 并返回
        Map<String, Object> claim = new HashMap<>();
        claim.put("id", userInfo.getId());
        claim.put("name", userInfo.getUserName());
        String token = JwtUtils.genToken(claim);
        return Result.success(token);
    }
    /**
     * 获取登录用户的信息
     */
    @RequestMapping("/getUserInfo")
    public UserInfo getUserInfo(HttpServletRequest request){
        //1. 从token中获取用户ID
        //2. 根据用户ID, 获取用户信息
        String token = request.getHeader("user_token");
        Integer userId = JwtUtils.getUserIdFromToken(token);
        if (userId==null){
            return null;
        }
        return userService.queryById(userId);
    }
    /**
     * 获取当前作者的信息
     */
    @RequestMapping("/getAuthorInfo")
    public UserInfo getAuthorInfo(Integer blogId){
        if (blogId==null && blogId<0){
            return null;
        }
        return userService.getAuthorInfo(blogId);
    }
}
