package com.example.userinfo.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.userinfo.dao.UserInfo;
import org.springframework.stereotype.Service;

@Service
public interface UserRepo extends IService<UserInfo>  {

}
