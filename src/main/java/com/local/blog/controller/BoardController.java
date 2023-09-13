package com.local.blog.controller;

import com.local.blog.config.auth.PrincipalDetail;
import com.local.blog.model.Board;
import com.local.blog.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

@Controller
@Slf4j
public class BoardController {

    @Autowired
    private BoardService boardService;

    // 컨트롤러에서 세션을 어떻게 찾는지?
    // 메인페이지 + 페이징
    @GetMapping({"","/"})
    public String index(Model model,
                        @PageableDefault(
                                size = 4,
                                sort = "id",
                                direction = Sort.Direction.DESC) // 최신글을 먼저보이게
                        Pageable pageable
    ){
        model.addAttribute("boards",boardService.글목록(pageable));
        return "index"; // viewResolver 작동(model에 정보를 index로 들고 이동)
    }

    // 글 상세보기
    @GetMapping("/board/{id}")
    public String findById(@PathVariable int id, Model model){
        model.addAttribute("board",boardService.글상세보기(id));
        return "board/detail";
    }

    // 글쓰기
    // user 권한 필요
    @GetMapping("/board/saveForm")
    public String saveForm(){
        return "board/saveForm";
    }

    //글 수정
    @GetMapping("/board/{id}/updateForm")
    public String updateForm(@PathVariable int id,Model model){
        model.addAttribute("board",boardService.글상세보기(id));
        return "board/updateForm";
    }
}
