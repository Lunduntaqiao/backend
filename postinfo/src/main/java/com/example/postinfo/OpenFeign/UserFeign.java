package com.example.postinfo.OpenFeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("userinfo")
@RequestMapping("/user")
public interface UserFeign {

    @PostMapping("/findUserId")
    int findUserId(@RequestParam(name = "userName") String userName);

    @PostMapping("/findUserName")
    String FindUserName(@RequestParam(name = "userId") int userId);

}
