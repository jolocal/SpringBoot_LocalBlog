package com.local.blog.service;

import com.local.blog.model.Board;
import com.local.blog.model.Reply;
import com.local.blog.model.RoleType;
import com.local.blog.model.User;
import com.local.blog.repository.BoardRepository;
import com.local.blog.repository.ReplyRepository;
import com.local.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// 스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌. IoC 해준다.
@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private ReplyRepository replyRepository;

    @Transactional
    public Board 글쓰기(Board board, User user) { //title, content
        board.setCount(0); // 조회수
        board.setUser(user); // 작성자
        return boardRepository.save(board);
    }

    //    public List<Board> 글목록(Pageable pageable) {
    @Transactional(readOnly = true) //select
    public Page<Board> 글목록(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    @Transactional(readOnly = true) //select
    public Board 글상세보기(int id) {
        return boardRepository.findById(id)
                .orElseThrow(()
                        -> new IllegalArgumentException("글 상세보기 실패: 아이디를 찾을 수 없습니다.")
                );
    }

    @Transactional
    public void 글삭제하기(int id) {
        boardRepository.deleteById(id);
    }

    @Transactional
    public void 글수정하기(int id, Board requestBoard) {
        Board board = boardRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("글 찾기 실패 : 아이디를 찾을 수 없습니다."));
        // 영속화 완료
        board.setTitle(requestBoard.getTitle());
        board.setContent(requestBoard.getContent());
        // 해당 함수로 종료시(Service가 종료될 때) 트랜잭션이 종료된다. 이때 더티체킹 -> 자동 업데이트가 db.flush
    }

    @Transactional
    public void 댓글쓰기(User user, int boardId, Reply requestReply) {
        Board board = boardRepository.findById(boardId).orElseThrow(()->
                new IllegalArgumentException("댓글 쓰기 실패: 게시글 id를 찾을 수 없습니다.")
        ); //영속화 완료
        requestReply.setUser(user);
        requestReply.setBoard(board);

        replyRepository.save(requestReply);
    }

    // 댓글 삭제하기
    @Transactional
    public void 댓글삭제하기(int replyId) {
        replyRepository.deleteById(replyId);
    }
}



