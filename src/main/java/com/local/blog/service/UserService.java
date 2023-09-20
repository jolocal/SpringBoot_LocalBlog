package com.local.blog.service;

import com.local.blog.handler.CustomValidationException;
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
@Slf4j
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
    @Transactional
    public User 회원수정(int id, User user) {
        log.info("회원수정 service start");
        log.info("id :{}, user:{}", id, user);

        // 영속화
        User userEntity = userRepository.findById(id).orElseThrow(() ->
            new CustomValidationException("찾을 수 없는 Id입니다.")
        );

        // 영속화 오브젝트 수정
        userEntity.setUsername(user.getUsername());

        // password 인코딩
        String rawPassword = user.getPassword();
        String encPassword = encoder.encode(rawPassword);
        userEntity.setPassword(encPassword);

        userEntity.setEmail(user.getEmail());

        return userEntity;
    }

    @Transactional(readOnly = true)
    public User 회원찾기(String username) {
        User user = userRepository.findByUsername(username).orElseGet(()->{
            return new User(); // 빈 객체 리턴 (null일 수 있음)
        });
        return user;
    }

}

  /*  @Transactional
    public void 회원수정(User user) {
        // 수정시에는 영속성 컨텍스트 User 오브젝트를 영속화시키고, 영속화된 User 오브젝트를 수정
        // select를 해서 User 오브젝트를 DB로 부터 가져오는 이유는 영속화를 하기 위해서
        // 영속화된 오브젝트를 변경하면 자동으로 db에서 update문을 날려주거든요.
        User persistance = userRepository.findById(user.getId())
                .orElseThrow(() ->
                        new IllegalArgumentException("회원 찾기 실패")
                );

        // validation check (카카오 로그인한 유저는 비밀번호,이메일 수정X)
        // => oauth 필드에 값이 없으면 수정 가능 (블로그 서버 유저)
        if (persistance.getOauth() == null || persistance.getOauth().equals("")){
            // password 수정
            String rawPassword = user.getPassword();
            String encPassword = encoder.encode(rawPassword);
            persistance.setPassword(encPassword);
            // 이메일 수정
            persistance.setEmail(user.getEmail());
        }

        // 회원수정 함수 종료시 = 서비스 종료 = 트랜잭션 종료 = commit 이 자동으로 됨
        // commit 자동으로 됨 -> 영속화된 persistance 객체의 변화가 감지되면 더티체킹이 되어 update문을 날려줌
    }*/


