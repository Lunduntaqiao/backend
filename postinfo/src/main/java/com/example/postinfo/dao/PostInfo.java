package com.example.postinfo.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Data
@TableName(value = "post_info")
public class PostInfo {

    @TableId(value= "post_id", type = IdType.AUTO)
    private int postId;
    private int userId;
    private String postTitle;
    @TableField(value = "post_info")
    private String postInfo;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",shape = JsonFormat.Shape.STRING, timezone="GMT+8")
    private Date postTime;
}
