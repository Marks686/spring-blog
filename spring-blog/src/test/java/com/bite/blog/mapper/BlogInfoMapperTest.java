package com.bite.blog.mapper;

import com.bite.blog.model.BlogInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BlogInfoMapperTest {

    @Autowired
    private BlogInfoMapper blogInfoMapper;

    @Test
    void queryBlogList() {
        System.out.println(blogInfoMapper.queryBlogList());
    }

    @Test
    void queryById() {
        System.out.println(blogInfoMapper.queryById(1));
    }

    @Test
    void updateBlog() {
        BlogInfo blogInfo = new BlogInfo();
        blogInfo.setTitle("test111111");
        blogInfo.setContent("测试修改内容");
        blogInfo.setId(3);
        blogInfoMapper.updateBlog(blogInfo);
    }

    @Test
    void deleteBlog() {
        blogInfoMapper.deleteBlog(3);
    }

    @Test
    void insertBlog() {
        BlogInfo blogInfo = new BlogInfo();
        blogInfo.setTitle("test");
        blogInfo.setContent("测试插入内容");
        blogInfo.setUserId(1);
        blogInfoMapper.insertBlog(blogInfo);
    }
}