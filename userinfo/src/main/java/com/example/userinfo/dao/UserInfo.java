package com.example.userinfo.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "user_info")
public class UserInfo {

    @TableId(value= "id", type = IdType.AUTO)
    private int id;

    private String userName ;
    private String userPassword;
    private String userSex ;
//    private String userImage ;
    private String userPhone ;
    private Integer userStatus ;
    private String userEmail;
}
