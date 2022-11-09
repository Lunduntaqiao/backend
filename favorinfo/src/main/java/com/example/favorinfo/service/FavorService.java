package com.example.favorinfo.service;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.favorinfo.OpenFeign.UserFeign;
import com.example.favorinfo.common.FavorReturnInfo;
import com.example.favorinfo.common.MyResult;
import com.example.favorinfo.dao.FavorInfo;
import com.example.favorinfo.mapper.FavorMapper;
import com.example.favorinfo.repository.FavorRepo;
import com.google.common.base.Joiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class FavorService extends ServiceImpl<FavorMapper, FavorInfo> implements FavorRepo {

    @Autowired
    private UserFeign userFeign;

    @Autowired
    private FavorMapper favorMapper;


    public MyResult<List<FavorReturnInfo>> findFavorInfo(int pageNum, int pageSize, int userId){
        MyResult<List<FavorReturnInfo>> myResult = new MyResult<>();
        String userName  = userFeign.FindUserName(userId);



        return myResult;
    }

    public MyResult<String> changeFavor(int userId, int postId){
        MyResult<String> myResult = new MyResult<>();
        FavorInfo favorInfo = favorMapper.selectAllByUserId(userId);
        List<String> postIdList =new ArrayList<>(Arrays.asList(favorInfo.getPostId().split(",")));
        int index = postIdList.indexOf(String.valueOf(postId));
        if(index == -1){
            postIdList.add(String.valueOf(postId));
            myResult.setMessage("The tweet has been marked");
        }else{
            postIdList.remove(index);
            myResult.setMessage("The tweet has been unmarked");
        }

        String changePostList = Joiner.on(",").join(postIdList);
        FavorInfo newFavorInfo = new FavorInfo();
        newFavorInfo.setUserId(userId);
        newFavorInfo.setPostId(changePostList);
        UpdateWrapper<FavorInfo> updateWrapper = new UpdateWrapper<>();

        updateWrapper.eq("user_id", userId);
        favorMapper.update(newFavorInfo, updateWrapper);
        myResult.setCode(200);
        return myResult;
    }

}
