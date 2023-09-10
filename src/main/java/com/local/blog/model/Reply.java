package com.local.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 200)
    private String content;

    // 이 답변은 어느 게시글에 있는가?
    @ManyToOne // 여러개의 답변은 하나의 게시글에 존재할 수 있다.
    @JoinColumn(name = "boardId")
    private Board board;

    // 이 답변은 누가 작성했는가?
    @ManyToOne // 하나의 유저는 여러개의 답변을 작성할 수 있다.
    @JoinColumn(name = "userId")
    private User user;

    @CreationTimestamp
    private Timestamp createDate;

}
