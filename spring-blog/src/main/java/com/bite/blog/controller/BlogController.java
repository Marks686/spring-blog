package com.bite.blog.controller;

import com.bite.blog.model.BlogInfo;
import com.bite.blog.model.Result;
import com.bite.blog.service.BlogService;
import com.bite.blog.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/blog")
@RestController
public class BlogController {
    @Autowired
    private BlogService blogService;

    /**
     * 获取博客列表
     * @return
     */
    @RequestMapping("/getlist")
    public List<BlogInfo> getBlogList(){
        return blogService.getBlogList();
    }
    /**
     * 获取博客详情
     */
    @RequestMapping("/getBlogDetail")
    public BlogInfo getBlogDetail(Integer blogId,HttpServletRequest request){
        BlogInfo blogInfo = blogService.getBlogDetail(blogId);
        //判断作者是否为登录用户
        //从token中获取userId
        String token = request.getHeader("user_token");
        Integer userId = JwtUtils.getUserIdFromToken(token);
        if (userId!=null && userId==blogInfo.getUserId()){
            blogInfo.setLoginUser(true);
        }
        return blogInfo;
    }
    /**
     * 发布博客
     */
    @RequestMapping("/add")
    public Result publishBlog(String title, String content, HttpServletRequest request){
        //从token中获取userId
        String token = request.getHeader("user_token");
        Integer userId = JwtUtils.getUserIdFromToken(token);
        if (userId==null || userId<0){
            return Result.fail("用户未登录",false);
        }
        //插入博客
        BlogInfo blogInfo = new BlogInfo();
        blogInfo.setUserId(userId);
        blogInfo.setTitle(title);
        blogInfo.setContent(content);
        blogService.insertBlog(blogInfo);
        return Result.success(true);
    }
    /**
     * 编辑博客
     */
    @RequestMapping("/update")
    public boolean updateBlog(Integer id, String title, String content){
        BlogInfo blogInfo = new BlogInfo();
        blogInfo.setTitle(title);
        blogInfo.setContent(content);
        //根据博客ID去更新数据
        blogInfo.setId(id);
        /**
         * 判断是否为作者本人
         * 投机取巧的方式
         * 从token中获取登录用户
         * 更新的sql: id = ? and userId= ?(登录用户)
         */
        blogService.updateBlog(blogInfo);
        return true;
    }
    /**
     * 删除博客
     */
    @RequestMapping("/delete")
    public boolean deleteBlog(Integer blogId){
        /**
         * 判断是否为作者本人
         * 需要获取登录用户和当前作者
         * 登录用户从token中获取
         * 当前作者, 需要再查一次数据库, 进行判断
         */
        blogService.delete(blogId);
        return true;
    }
}
