package com.example.favorinfo.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.favorinfo.dao.FavorInfo;
import org.springframework.stereotype.Service;

@Service
public interface FavorRepo extends IService<FavorInfo> {
}
