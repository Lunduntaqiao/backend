package com.example.userinfo.control;

import com.example.userinfo.common.MyResult;
import com.example.userinfo.dao.UserInfo;
import com.example.userinfo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserContro {

    @Autowired
    private UserService userService;




    /**
     * upload pictures
     * @param userName
     * @param file
     * @return file path
     * @throws IOException
     */
    @PostMapping("/upload")
    public MyResult<String> upload(@RequestParam(name = "userName") String userName,
                                   @RequestParam(name="Image") MultipartFile file) throws IOException {
        return userService.upload(userName, file);
    }

    @PostMapping("/register")
    public MyResult<String> register(@RequestBody UserInfo userInfo){
        return userService.register(userInfo);
    }

    @PostMapping("/changePassword")
    public MyResult<String> changePassword(@RequestBody UserInfo userInfo){
        return userService.changePassword(userInfo);
    }

    @PostMapping("/login")
    public MyResult<Integer> userLogin(@RequestBody UserInfo userInfo){
        return userService.userLogin(userInfo);
    }

    @PostMapping("/GetUserInfo")
    public MyResult<UserInfo> getUserInfo(@RequestParam(name = "userName") String userName){
        return userService.getUserInfo(userName);
    }


    /**
     * OpenFeign
     */
    @PostMapping("/findUserName")
    public String findUserName(@RequestParam(name = "userId") int userId){
        return userService.findUserName(userId);
    }

    @PostMapping("/findUserId")
    public int findUserId(@RequestParam(name = "userName") String userName){
        return userService.findUserId(userName);
    }
}
