package com.local.blog.api;

import com.local.blog.config.auth.PrincipalDetail;
import com.local.blog.dto.ResponseDto;
import com.local.blog.model.Board;
import com.local.blog.model.Reply;
import com.local.blog.model.User;
import com.local.blog.repository.ReplyRepository;
import com.local.blog.service.BoardService;
import com.local.blog.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@Slf4j
public class BoardApiController {

    @Autowired
    private BoardService boardService;

    // 글쓰기
    @PostMapping("/api/board")     // title, content
    public ResponseDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal){
        boardService.글쓰기(board,principal.getUser());
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    // 글삭제
    @DeleteMapping("/api/board/{id}")
    public ResponseDto<Integer> deleteById(@PathVariable int id){
        boardService.글삭제하기(id);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }
    // 글수정
    @PutMapping("/api/board/{id}") // 글번호, 수정된board
    public ResponseDto<Integer> update(@PathVariable int id, @RequestBody Board board){
        boardService.글수정하기(id,board);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    // 댓글작성
    // 데이터를 받을 때 컨트롤러에서 dto를 만들어서 받는게 좋다.
    // dto를 사용하지 않은 이유는
    @PostMapping("/api/board/{boardId}/reply") //@AuthenticationPrincipal PrincipalDetail principal: 누가 적었는지 알아야하기 때문
    public ResponseDto<Integer> replySave(@PathVariable int boardId, @RequestBody Reply reply, @AuthenticationPrincipal PrincipalDetail principal){
        boardService.댓글쓰기(principal.getUser(),boardId,reply);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    // 댓글삭제
    @DeleteMapping("/api/board/{boardId}/reply/{replyId}")
    public ResponseDto<Integer> replyDelete(@PathVariable int replyId){
        boardService.댓글삭제하기(replyId);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }
}
