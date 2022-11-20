package com.example.userinfo.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.userinfo.dao.UserInfo;
import com.example.userinfo.mapper.UserMapper;
import com.example.userinfo.common.MyResult;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.example.userinfo.repository.UserRepo;

import java.util.*;
import java.io.File;
import java.io.IOException;

@Service
public class UserService extends ServiceImpl<UserMapper, UserInfo> implements UserRepo {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserMapper userMapper;
    /**
     * the path of image
     */
    private static final String Path = "images";

    private static final String DEFAULT_PATH = "images/0.jpg";

    private static final String TEACHER_STATUS = "Teacher";
    private static final String STUDENT_STATUS = "Student";
    /**
     * upload pictures
     * @param userName
     * @param file
     * @return file path
     * @throws IOException
     */
    public MyResult<String> upload(String userName, MultipartFile file) throws IOException {
        MyResult<String> myResult = new MyResult<>();
        // check folder is exists?
        File folder = new File(Path);
        if(!folder.exists()){
            folder.mkdirs();
        }
        if(file.isEmpty()){
            myResult.setCode(400);
            myResult.setMessage("Please select Photo");
            return myResult;
        }
        String originalFilename = file.getOriginalFilename();
        String filetype = originalFilename.substring(originalFilename.indexOf("."));
        // chech image format
        if(".jpg".equals(filetype) || ".gif".equals(filetype)){
            String newFileName = userName +filetype;
            File targetFile = new File(Path, newFileName);
            FileUtils.copyInputStreamToFile(file.getInputStream(),targetFile);
            myResult.setCode(200);
            myResult.setMessage("Image uploaded successfully");
            myResult.setData(targetFile.toString());
        }else{
            myResult.setCode(400);
            myResult.setMessage("The image format only supports jpg and gif");
        }
        return myResult;
    }

    /**
     * register a user
     * @param userInfo
     * @return
     */
    public MyResult<String> register(UserInfo userInfo){
        MyResult<String> myResult = new MyResult<>();
//        if(userInfo.getUserImage().isEmpty()){
//            userInfo.setUserImage(DEFAULT_PATH);
//        }

        // check username
        if(userMapper.selectAllByName(userInfo.getUserName()) != null){
            myResult.setCode(400);
            myResult.setMessage("The user name already exists");
            return myResult;
        }

        // Password encryption
        String md5passWord = DigestUtils.md5DigestAsHex(userInfo.getUserPassword().getBytes());
        userInfo.setUserPassword(md5passWord);
        userRepo.save(userInfo);
        myResult.setCode(200);
        myResult.setMessage("User registration succeeded");
        return myResult;
    }

    /**
     * change user password
     * @param userInfo
     * @return message
     */
    public MyResult<String> changePassword(UserInfo userInfo){
        MyResult<String> myResult = new MyResult<>();

        if(userMapper.selectAllByName(userInfo.getUserName()) == null){
            myResult.setCode(400);
            myResult.setMessage("The user name is not exist");
            return myResult;
        }

        // Password encryption
        String md5passWord = DigestUtils.md5DigestAsHex(userInfo.getUserPassword().getBytes());
        userInfo.setUserPassword(md5passWord);

        UpdateWrapper<UserInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_name", userInfo.getUserName());
        userMapper.update(userInfo, updateWrapper);
        myResult.setCode(200);
        myResult.setMessage("Change succeeded");
        return myResult;
    }

    /**
     * user login
     * @param userInfo
     * @return message
     */
    public MyResult<Integer> userLogin(UserInfo userInfo){
        MyResult<Integer> myResult = new MyResult<>();
        // check username
        UserInfo getUserInfo = userMapper.selectAllByName(userInfo.getUserName());
        if(getUserInfo == null){
            myResult.setCode(400);
            myResult.setMessage("The user name is not exist");
            return myResult;
        }
        // check password
        String md5passWord = DigestUtils.md5DigestAsHex(userInfo.getUserPassword().getBytes());
        if(getUserInfo.getUserPassword().equals(md5passWord)){
            myResult.setCode(200);
            myResult.setMessage("Login successful");
            myResult.setData(getUserInfo.getUserStatus());
            return myResult;
        }else{
            myResult.setCode(400);
            myResult.setMessage("The password is not fail");
            return myResult;
        }
    }

    /**
     * get user info
     * @param userName
     * @return Data : all userInfo
     */
    public MyResult<UserInfo> getUserInfo(String userName){
        MyResult<UserInfo> myResult = new MyResult<>();

        UserInfo userInfo = userMapper.selectAllByName(userName);
        userInfo.setUserPassword(null);
        myResult.setCode(200);
        myResult.setData(userInfo);
        return myResult;
    }


    /**
     * OpenFeign
     */
    public String findUserName(int userId){
        return userMapper.selcetUserNameById(userId);
    }


    public int findUserId(String userName){
        return userMapper.selcetUserIdByUserName(userName);
    }

}
