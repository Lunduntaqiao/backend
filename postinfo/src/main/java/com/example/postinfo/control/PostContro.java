package com.example.postinfo.control;


import com.example.postinfo.Service.PostService;
import com.example.postinfo.common.MyResult;
import com.example.postinfo.common.SendPostInfo;
import com.example.postinfo.dao.PostInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/post")
public class PostContro {

    @Autowired
    private PostService postService;

    /**
     * get post information
     * @param pageNum if this use in homepage, get 1
     * @param pageSize if this use in homepage, get 1
     * @param noSelected if this use in homepage, no send post info
     * @return
     */
    @PostMapping("/findPostInfo")
    public MyResult<List<SendPostInfo>> findPostInfo(@RequestParam(name = "pageNum") int pageNum,
                                              @RequestParam(name = "pageSize") int pageSize,
                                              @RequestParam(name = "noSelected",required = false) String noSelected){
        return postService.findPostInfo(pageNum, pageSize,noSelected);
    }

    @PostMapping("/savePostInfo")
    public MyResult<String> savePostInfo(@RequestParam(name = "userName") String userName,
                                         @RequestBody SendPostInfo sendPostInfo) throws IOException{
        return postService.savePostInfo(userName, sendPostInfo);
    }


    @PostMapping("/openFeignFindInfo")
    public List<PostInfo> openFeignFindInfo(@RequestParam(name = "noSelected",required = false) String noSelected,
                                       @RequestParam(name = "postId",required = false) String postId ){
        return postService.openFeignFindInfo(noSelected, postId);
    }

    @PostMapping("/findAllPostInfo")
    public MyResult<SendPostInfo> findAllPostInfo(@RequestParam(name = "postId")int postId) throws IOException{
        return postService.findAllPostInfo(postId);
    }
}
