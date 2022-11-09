package com.example.favorinfo.service;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.favorinfo.OpenFeign.UserFeign;
import com.example.favorinfo.common.FavorReturnInfo;
import com.example.favorinfo.common.MyResult;
import com.example.favorinfo.dao.FavorInfo;
import com.example.favorinfo.mapper.FavorMapper;
import com.example.favorinfo.repository.FavorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class FavorService extends ServiceImpl<FavorMapper, FavorInfo> implements FavorRepo {

    @Autowired
    private UserFeign userFeign;


    public MyResult<List<FavorReturnInfo>> findFavorInfo(int pageNum, int pageSize, int userId){
        MyResult<List<FavorReturnInfo>> myResult = new MyResult<>();

        String userName  = userFeign.FindUserName(userId);

        return myResult;
    }

}
