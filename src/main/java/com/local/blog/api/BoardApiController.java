package com.local.blog.api;

import com.local.blog.config.auth.PrincipalDetail;
import com.local.blog.dto.CMRespDto;
import com.local.blog.model.Board;
import com.local.blog.model.Reply;
import com.local.blog.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class BoardApiController {

    @Autowired
    private BoardService boardService;

    // 글쓰기
    @PostMapping("/api/board")     // title, content
    public CMRespDto<?> save(
            @RequestBody Board board,
            @AuthenticationPrincipal PrincipalDetail principal){
        Board boardEntity = boardService.글쓰기(board,principal.getUser());
        return new CMRespDto<>(1,"글쓰기성공",boardEntity);
//        return new CMRespDto<Integer>(HttpStatus.OK.value(), 1);
    }

    /*// 글삭제
    @DeleteMapping("/api/board/{id}")
    public CMRespDto<Integer> deleteById(@PathVariable int id){
        boardService.글삭제하기(id);
        return new CMRespDto<Integer>(HttpStatus.OK.value(), 1);
    }
    // 글수정
    @PutMapping("/api/board/{id}") // 글번호, 수정된board
    public CMRespDto<Integer> update(@PathVariable int id, @RequestBody Board board){
        boardService.글수정하기(id,board);
        return new CMRespDto<Integer>(HttpStatus.OK.value(), 1);
    }

    // 댓글작성
    // 데이터를 받을 때 컨트롤러에서 dto를 만들어서 받는게 좋다.
    // dto를 사용하지 않은 이유는
    @PostMapping("/api/board/{boardId}/reply") //@AuthenticationPrincipal PrincipalDetail principal: 누가 적었는지 알아야하기 때문
    public CMRespDto<Integer> replySave(@PathVariable int boardId, @RequestBody Reply reply, @AuthenticationPrincipal PrincipalDetail principal){
        boardService.댓글쓰기(principal.getUser(),boardId,reply);
        return new CMRespDto<Integer>(HttpStatus.OK.value(), 1);
    }

    // 댓글삭제
    @DeleteMapping("/api/board/{boardId}/reply/{replyId}")
    public CMRespDto<Integer> replyDelete(@PathVariable int replyId){
        boardService.댓글삭제하기(replyId);
        return new CMRespDto<Integer>(HttpStatus.OK.value(), 1);
    }*/
}
