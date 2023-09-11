package com.local.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// 빈등록: 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것
@Configuration //IoC
public class SecurityConfig {

    @Bean
    BCryptPasswordEncoder encode(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    SecurityFilterChain configure (HttpSecurity http) throws Exception {
        http
                .csrf().disable() //csrf 토큰 비활성화 (테스트시 걸어두는게 좋음)
                .authorizeRequests() // 요청이 들어오면
                    .antMatchers("/","/auth/**","/js/**","/css/**","/image/**") // 접근을 허용하는 url
                    .permitAll() // 모두 허용
                    .anyRequest() // 위가 아닌 나머지 주소는
                    .authenticated() // 인증이 필요함
                .and()
                    .formLogin() // 로그인 폼은
                    .loginPage("/auth/signin"); // /auth/signin
        return http.build();
    }

}
