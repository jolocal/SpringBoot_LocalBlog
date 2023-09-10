package com.local.blog.com.local.blog.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// 사용자가 요청 -> 응답(Data)
@RestController
@Slf4j
public class HttpControllerTest {
    private static final String TAG = "HttpControllerTest";

    // 인터넷 브라우저 요청은 무조건 get 요청밖에 할 수 없다.
    // http://localhost:8080/http/get (select)
    @GetMapping("/http/get")
    public String getTest(Member m){
        log.info("{} getter : {}",TAG,m.getId());
        m.setId(5000);
        log.info("{} getter : {}",TAG,m.getId());
        Member m1 = new Member();
        return "get 요청: "+m.getId()+","+ m.getUserName()+","+m.getUserName()+","+m.getPassword();
    }

    // http://localhost:8080/http/post(insert)
    @PostMapping("/http/post")
    public String postTest(@RequestBody Member m){ // test/plain , application/json
        return "post 요청: "+m.getId()+","+ m.getUserName()+","+m.getUserName()+","+m.getPassword();
    }
}
