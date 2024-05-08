package com.bite.blog.service;

import com.bite.blog.mapper.BlogInfoMapper;
import com.bite.blog.mapper.UserInfoMapper;
import com.bite.blog.model.BlogInfo;
import com.bite.blog.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private BlogInfoMapper blogInfoMapper;

    public UserInfo queryByName(String userName) {
        return userInfoMapper.queryByName(userName);
    }

    public UserInfo queryById(Integer userId) {
        return userInfoMapper.queryById(userId);
    }

    public UserInfo getAuthorInfo(Integer blogId) {
        //1. 根据blogId 获取userId
        //2. 根据userid 获取userInfo
        BlogInfo blogInfo = blogInfoMapper.queryById(blogId);
        if (blogInfo==null || blogInfo.getUserId()<0){
            return null;
        }
        return userInfoMapper.queryById(blogInfo.getUserId());
    }
}
