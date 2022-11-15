package com.example.postinfo.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.postinfo.OpenFeign.UserFeign;
import com.example.postinfo.common.MyResult;
import com.example.postinfo.common.SendPostInfo;
import com.example.postinfo.dao.PostInfo;
import com.example.postinfo.mapper.PostMapper;
import com.example.postinfo.repository.PostRepo;

import javafx.geometry.Pos;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@Service
public class PostService extends ServiceImpl<PostMapper, PostInfo> implements PostRepo {

    /**
     * the path of post content
     */
    private static final String Path = "postContent";

    private static final String filetupe=".text";
    @Autowired
    private PostMapper postMapper;

    @Autowired
    private UserFeign userFeign;

    @Autowired
    private PostRepo postRepo;

    /**
     *
     * @param pageNum
     * @param pageSize
     * @param noSelected
     * @return
     */
    public MyResult<List<SendPostInfo>> findPostInfo(int pageNum, int pageSize, String noSelected){

        MyResult<List<SendPostInfo>> myResult = new MyResult<>();
        QueryWrapper<PostInfo> queryWrapper = new QueryWrapper<>();

        // delect columns
        List<String> noSelectedList = Arrays.asList(noSelected.split(","));
        if(!noSelected.isEmpty()){
            queryWrapper.select(PostInfo.class, item ->!noSelectedList.contains(item.getColumn()));
        }

        Page<PostInfo> page = new Page<>(pageNum, pageSize);
        postMapper.selectPage(page, queryWrapper);
        List<PostInfo> postInfoList = page.getRecords();
        List<SendPostInfo> sendPostInfoList = new ArrayList<>();

        if (postInfoList.size() != 0) {
            // postInfo to sendPostInfo
            for(PostInfo postInfo:postInfoList){
                SendPostInfo sendPostInfo = new SendPostInfo();

                sendPostInfo.setPostId(postInfo.getPostId());
                String author = userFeign.FindUserName(postInfo.getUserId());
                sendPostInfo.setAuthor(author);
                sendPostInfo.setPostTitle(postInfo.getPostTitle());
                sendPostInfo.setPostTime(postInfo.getPostTime());
                sendPostInfoList.add(sendPostInfo);
            }
            myResult.setCode(200);
            myResult.setMessage("查询数据为:" + page.getTotal() + ", 总页数:" + page.getPages());
            myResult.setData(sendPostInfoList);
        }else{
            myResult.setCode(200);
            myResult.setMessage("查询数据为:0");
        }
        return myResult;
    }


    /**
     * save post info
     * @param userName
     * @param sendPostInfo
     * @return
     * @throws IOException
     */
    public MyResult<String> savePostInfo(String userName, SendPostInfo sendPostInfo) throws IOException {
        MyResult<String> myResult = new MyResult<>();
        // check folder is exists?
        File folder = new File(Path);
        if(!folder.exists()){
            folder.mkdirs();
        }

        PostInfo postInfo = new PostInfo();
        int userId = userFeign.findUserId(userName);
        postInfo.setUserId(userId);
        //  get content from postInfoPath
        //  save content in the computer
        //  save path in database
        String content = sendPostInfo.getPostInfo();
        if(content.isEmpty()){
            myResult.setCode(400);
            myResult.setMessage("Please input the post content");
            return myResult;
        }else{
            // file name use UUID
            long uuid = UUID.randomUUID().getMostSignificantBits();
            String uuidStr = String.valueOf(uuid).replace("-","");
            String newFileName =  userId+uuidStr+filetupe;

            File targeFile = new File(Path, newFileName);
            FileWriter fileWriter = new FileWriter(targeFile);
            fileWriter.write(content);
            fileWriter.close();

            postInfo.setPostTitle(sendPostInfo.getPostTitle());
            postInfo.setPostInfoPath(String.valueOf(targeFile));
            postInfo.setPostTime(nowDateTime());
            postRepo.save(postInfo);
            myResult.setCode(200);
            myResult.setMessage("post uploaded successfully");
            return myResult;
        }
    }


    public MyResult<SendPostInfo> findAllPostInfo(int postId) throws IOException {
        MyResult<SendPostInfo> myResult = new MyResult<>();
        PostInfo postInfo = postMapper.findAllByPostId(postId);
        SendPostInfo sendPostInfo = new SendPostInfo();

        sendPostInfo.setPostId(postId);
        String author = userFeign.FindUserName(postInfo.getUserId());
        sendPostInfo.setAuthor(author);

        sendPostInfo.setPostTitle(postInfo.getPostTitle());

        // find postInfo from path
        String filePath = postInfo.getPostInfoPath();
        File file = new File(filePath);
        InputStreamReader in = new InputStreamReader (Files.newInputStream(file.toPath()), StandardCharsets.UTF_8);
        BufferedReader reader=new BufferedReader(in);
        String line;
        StringBuilder content = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            content.append(line+"\n");
        }
        sendPostInfo.setPostInfo(content.toString());

        sendPostInfo.setPostTitle(postInfo.getPostTitle());

        sendPostInfo.setPostTime(postInfo.getPostTime());

        myResult.setCode(200);
        myResult.setData(sendPostInfo);
        return myResult;
    }


    /**
     * get now time
     * @return
     */
    public Date nowDateTime() {
        Date date;
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        date = Date.from(zdt.toInstant());
        return date;
    }

    /**
     * openfeign
     */
    public List<PostInfo> openFeignFindInfo(String noSelected, String postId){

        QueryWrapper<PostInfo> queryWrapper = new QueryWrapper<>();

        // delect columns
        List<String> noSelectedList = Arrays.asList(noSelected.split(","));
        if(!noSelected.isEmpty()){
            queryWrapper.select(PostInfo.class, item ->!noSelectedList.contains(item.getColumn()));
        }

        Collection<String> collection = Arrays.asList(postId.split(","));
        queryWrapper.in("post_id", collection);
        List<PostInfo> postInfoList = postMapper.selectList(queryWrapper);

        return postInfoList;
    }


}