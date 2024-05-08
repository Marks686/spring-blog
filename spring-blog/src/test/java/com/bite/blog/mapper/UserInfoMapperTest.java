package com.bite.blog.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserInfoMapperTest {
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Test
    void queryByName() {
        System.out.println(userInfoMapper.queryByName("zhangsan"));
    }

    @Test
    void queryById() {
        System.out.println(userInfoMapper.queryById(2));
    }
}