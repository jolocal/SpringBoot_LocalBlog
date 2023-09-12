package com.local.blog.controller;

import com.local.blog.config.auth.PrincipalDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@Slf4j
public class BoardController {

    @GetMapping({"","/"})
    public String index(@AuthenticationPrincipal PrincipalDetail principal){ // 컨트롤러에서 세션을 어떻게 찾는지?
        // WEB-INF/views/index.jsp
        log.info("로그인 사용자 아이디 : {}",principal.getUsername());
        return "index";
    }
}
