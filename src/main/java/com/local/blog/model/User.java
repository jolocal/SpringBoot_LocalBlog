package com.local.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Timestamp;

// ORM -> Java(다른언어) Object -> 테이블로 매핑해주는 기술
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // 빌더패턴
@Entity // User 클래스가 MySQL에 테이블이 생성이 된다.
//@DynamicInsert // insert시에 null인 필드를 제외시켜준다.
public class User {

    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     /*
     넘버링전략: 프로젝트에서 연결된 DB의 넘버링 전략을 따라간다.
     -> 오라클:시퀀스, MySQL:auto_increment
     */
    private int id; // 오라클:시퀀스, MySQL:auto_increment

    @Column(nullable = false, length = 30, unique = true)
    private String username; // 아이디

    @Column(nullable = false, length = 100) // 12345 -> 해쉬(비밀번호 암호화)
    private String password;

    @Column(nullable = false, length = 50)
    private String email;

    // @ColumnDefault("'user'")
    // DB는 RoleType 이라는게 없다.
    @Enumerated(EnumType.STRING)
    private RoleType role; // Enum을 쓰는게 좋다. // ADMIN,USER

    @CreationTimestamp // 시간이 자동 입력 (오라클:Sysdate,MySQL:Now)
    private Timestamp createDate;

}
