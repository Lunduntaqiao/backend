package com.example.favorinfo.dao;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "favor_info")
public class FavorInfo {

    @TableId(value = "user_id")
    private int userId;
    @TableId(value = "post_id")
    private String postId;
}
