package com.example.postinfo.control;


import com.example.postinfo.Service.PostService;
import com.example.postinfo.common.MyResult;
import com.example.postinfo.dao.PostInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     * @param postId
     * @return
     */
    @PostMapping("/findPostInfo")
    public MyResult<List<PostInfo>> briefInfo(@RequestParam(name = "pageNum") int pageNum,
                                              @RequestParam(name = "pageSize") int pageSize,
                                              @RequestParam(name = "noSelected",required = false) String noSelected,
                                              @RequestParam(name = "postId",required = false) String postId ){
        return postService.findPostInfo(pageNum, pageSize,noSelected, postId);
    }


    @PostMapping("/OpenFeignFindInfo")
    public List<PostInfo> OpenFeignFindInfo(@RequestParam(name = "noSelected",required = false) String noSelected,
                                       @RequestParam(name = "postId",required = false) String postId ){
        return postService.OpenFeignFindInfo(noSelected, postId);
    }
}
