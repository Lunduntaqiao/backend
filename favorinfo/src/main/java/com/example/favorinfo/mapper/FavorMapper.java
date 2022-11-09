package com.example.favorinfo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.favorinfo.dao.FavorInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface FavorMapper extends BaseMapper<FavorInfo> {


    @Select("select * from favor_info where user_id = #{user_id}")
    FavorInfo selectAllByUserId(@Param("user_id") int userId);
}
