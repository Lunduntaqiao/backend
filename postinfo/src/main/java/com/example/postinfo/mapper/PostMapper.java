package com.example.postinfo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.postinfo.dao.PostInfo;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PostMapper extends BaseMapper<PostInfo> {

    @Select("select * from post_info where post_id = #{post_id}")
    PostInfo findAllByPostId(@Param("post_id") int postId);
}
