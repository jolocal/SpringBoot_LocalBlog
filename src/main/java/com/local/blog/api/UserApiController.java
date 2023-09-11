package com.local.blog.api;

import com.local.blog.dto.ResponseDto;
import com.local.blog.model.User;
import com.local.blog.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class UserApiController {

    @Autowired
    private UserService userService;


    @PostMapping("/auth/joinProc")
    public ResponseDto<Integer> save(@RequestBody User user){ // username, password, email
        log.info("UserApiController : save호출됨");
        // 실제로 DB에 insert를 하고 아래에서 return이 되면 되요.
        userService.회원가입(user);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); // 자바오브젝트를 json으로 변환해서 리턴(Jackson)
    }

}
