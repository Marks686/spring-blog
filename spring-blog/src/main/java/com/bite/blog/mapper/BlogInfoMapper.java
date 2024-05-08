package com.bite.blog.mapper;

import com.bite.blog.model.BlogInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface BlogInfoMapper {
    /**
     * 获取博客列表
     */
    @Select("select * from blog where delete_flag=0 order by create_time desc")
    List<BlogInfo> queryBlogList();
    /**
     * 根据博客ID, 获取博客详情
     */
    @Select("select * from blog where id=#{id} and delete_flag=0")
    BlogInfo queryById(Integer id);
    /**
     * 编辑博客
     */
    @Update("update blog set title=#{title}, content=#{content} where id=#{id}")
    Integer updateBlog(BlogInfo blogInfo);
    /**
     * 根据ID, 删除博客
     */
    @Update("update blog set delete_flag=1 where id = #{id}")
    Integer deleteBlog(Integer id);
    /**
     * 添加博客
     */
    @Insert("insert into blog(title,content, user_id) values (#{title},#{content},#{userId})")
    Integer insertBlog(BlogInfo blogInfo);
}
