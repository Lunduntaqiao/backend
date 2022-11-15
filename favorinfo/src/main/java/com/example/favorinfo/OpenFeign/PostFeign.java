package com.example.favorinfo.OpenFeign;


import com.example.favorinfo.common.MyResult;
import com.example.favorinfo.common.PostInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("postinfo")
@RequestMapping("/post")
public interface PostFeign {

    @PostMapping("/openFeignFindInfo")
    public List<PostInfo> OpenFeignFindInfo(@RequestParam(name = "noSelected",required = false) String noSelected,
                                            @RequestParam(name = "postId",required = false) String postId );
}
