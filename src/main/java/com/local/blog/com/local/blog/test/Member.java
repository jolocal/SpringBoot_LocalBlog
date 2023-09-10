package com.local.blog.com.local.blog.test;

import lombok.*;

@Data
@AllArgsConstructor // 전체 생성자
@NoArgsConstructor // 빈 생성자
public class Member {
    private  int id;
    private  String userName;
    private  String password;
    private  String email;
}
