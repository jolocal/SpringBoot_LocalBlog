package com.local.blog.service;

import com.local.blog.model.RoleType;
import com.local.blog.model.User;
import com.local.blog.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// 스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌. IoC 해준다.
@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;




    @Autowired
    private BCryptPasswordEncoder encoder;

    @Transactional
    public void 회원가입(User user) {

        String rawPassword = user.getPassword(); // 1234 원본
        String encPassword = encoder.encode(rawPassword); //해쉬
        user.setPassword(encPassword);
        user.setRole(RoleType.USER);
        userRepository.save(user);
        // 여기서 오류가 터지면 GlobalExceptionHandler 실행
    }

}



