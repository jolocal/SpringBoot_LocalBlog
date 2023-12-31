package com.local.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // 빌더패턴
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private int id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob // 대용량 데이터 사용시
    private String content; // 섬머노트 라이브러리 <html>태그가 섞여서 디자인이 됨.

    private int count; // 조회수

    /*
    @ManyToOne // Many = Board, One = User ( 한명의 유저는 여러개의 게시글을 작성할 수 있다.)
    @OneToOne // 한명의 유저는 하나의 게시글을 작성할 수 있다.
    @OneToMany // Many = User, One = Board ( 하나의 게시글은 여러명의 유저가 사용할 수 있다.)
    */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId") // 필드값
    private User user; //DB는 오브젝트를 저장할 수 없다. FK, 자바는 오브젝트를 저장할 수 있다.

    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE) // mappedBy 연관관계의 주인이 아니다( 난 FK가 아니예요) DB에 컬럼을 만들지 마세요.
    @JsonIgnoreProperties({"board"})
    @OrderBy("id desc")
    private List<Reply> replys;

    @CreationTimestamp // 데이터가 insert시 자동으로 현재시간이 들어감
    private Timestamp createDate;

}
