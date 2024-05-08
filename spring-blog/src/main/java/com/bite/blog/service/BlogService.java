package com.bite.blog.service;

import com.bite.blog.mapper.BlogInfoMapper;
import com.bite.blog.model.BlogInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService {
    @Autowired
    private BlogInfoMapper blogInfoMapper;

    public List<BlogInfo> getBlogList() {
        return blogInfoMapper.queryBlogList();
    }

    public BlogInfo getBlogDetail(Integer id) {
        return blogInfoMapper.queryById(id);
    }

    public Integer insertBlog(BlogInfo blogInfo) {
        return blogInfoMapper.insertBlog(blogInfo);
    }

    public Integer updateBlog(BlogInfo blogInfo) {
        return blogInfoMapper.updateBlog(blogInfo);
    }

    public Integer delete(Integer blogId) {
        return
                +blogInfoMapper.deleteBlog(blogId);
    }
}
