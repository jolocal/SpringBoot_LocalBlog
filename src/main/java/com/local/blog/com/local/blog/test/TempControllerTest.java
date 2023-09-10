package com.local.blog.com.local.blog.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class TempControllerTest {

    // http://localhost:8080/blog/temp/home
    @GetMapping("/temp/home")
    public String temHone(){
        log.info("tempHome start");
        // 파일리턴 기본경로: src/main/resources/static
        // 리턴명: /home.html
        // 풀경로: src/main/resources/static/home.html
        return "/home.html";
    }

    /*
    * @RestController 에서 String으로 리턴할때는 문자 자체를 리턴
    * @Controller 에서 String으로 리턴할때는 해당 경로 이하에있는 파일을 리턴
    * */

    @GetMapping("/temp/img")
    public String tempImg(){
        return "/a.png";
    }

    public String temJsp(){
        // prefix : /WEB-INF/views/
        // suffix: .jsp
        // 풀네임: /WEB-INF/views//test.jsp.jsp
        //return "/test.jsp"

        // 풀네임: /WEB-INF/views/test.jsp
        return "test";
    }
}
