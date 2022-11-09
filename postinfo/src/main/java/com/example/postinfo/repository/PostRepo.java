package com.example.postinfo.repository;

import com.baomidou.mybatisplus.extension.service.IService;

import com.example.postinfo.dao.PostInfo;
import org.springframework.stereotype.Service;

@Service
public interface PostRepo extends IService<PostInfo> {
}
