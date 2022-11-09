package com.example.userinfo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.userinfo.dao.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper extends BaseMapper<UserInfo> {

    @Select("select * from user_info where user_name = #{user_name}")
    UserInfo selectAllByName(@Param("user_name") String userName);

    @Select("select user_email from user_info where user_email = #{user_email}")
    String selcetUserEmail(@Param("user_email") String userEmail);

    @Select("select user_name from user_info where user_id = #{user_id}")
    String selcetUserNameById(@Param("user_id") int userId);
}
