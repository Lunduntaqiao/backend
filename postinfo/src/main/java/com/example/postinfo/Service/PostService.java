package com.example.postinfo.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.postinfo.common.MyResult;
import com.example.postinfo.dao.PostInfo;
import com.example.postinfo.mapper.PostMapper;
import com.example.postinfo.repository.PostRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service
public class PostService extends ServiceImpl<PostMapper, PostInfo> implements PostRepo {

    @Autowired
    private PostMapper postMapper;

    public MyResult<List<PostInfo>> findPostInfo(int pageNum, int pageSize, String noSelected, String postId){
        MyResult<List<PostInfo>> myResult = new MyResult<>();
        QueryWrapper<PostInfo> queryWrapper = new QueryWrapper<>();

        // delect columns
        List<String> noSelectedList = Arrays.asList(noSelected.split(","));
        if(!noSelected.isEmpty()){
            queryWrapper.select(PostInfo.class, item ->!noSelectedList.contains(item.getColumn()));
        }


        if(postId.isEmpty()){
            queryWrapper.isNotNull("post_id");
        }else{
            Collection<String> collection = Arrays.asList(postId.split(","));
            queryWrapper.in("post_id", collection);
        }
        Page<PostInfo> page = new Page<>(pageNum, pageSize);
        postMapper.selectPage(page, queryWrapper);
        List<PostInfo> postInfoList = page.getRecords();

        if (postInfoList.size() != 0) {
            myResult.setCode(200);
            myResult.setMessage("查询数据为:" + page.getTotal() + ", 总页数:" + page.getPages());
            myResult.setData(postInfoList);
        }else{
            myResult.setCode(200);
            myResult.setMessage("查询数据为:0");
        }
        return myResult;
    }

}