package com.example.favorinfo.control;


import com.example.favorinfo.common.FavorReturnInfo;
import com.example.favorinfo.common.MyResult;
import com.example.favorinfo.service.FavorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/favor")
public class FavorContro {
    @Autowired
    private FavorService favorService;

    @PostMapping("/GetFavorites")
    public MyResult<List<FavorReturnInfo>> findFavorInfo(@RequestParam(name = "pageNum") int pageNum,
                                                         @RequestParam(name = "pageSize") int pageSize,
                                                         @RequestParam(name = "userId") int userId){
        return favorService.findFavorInfo(pageNum, pageSize, userId);
    }

}
