package com.local.blog.dto;

import com.local.blog.model.User;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class SignupDto {
    @NotBlank
    @Size(min = 2, max = 20)
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    @Email
    private String email;

    public User toEntity(){
        return User.builder()
                .username(username)
                .password(password)
                .email(email)
                .build();
    }
}
