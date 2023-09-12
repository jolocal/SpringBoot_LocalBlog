package com.local.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
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

    // 시큐리티가 대신 로그인해주는데 password를 가로채기 하는데
    // 해당 password가 뭘로 해쉬가 되어 회원가입이 되었는지 알아야
    // 같은 해쉬로 암호화해서 DB에 있는 해쉬랑 비교할 수 있음.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain configure (HttpSecurity http) throws Exception {
        http
                .csrf().disable() //csrf 토큰 비활성화 (테스트시 걸어두는게 좋음)
                .authorizeRequests() // 요청이 들어오면
                    .antMatchers("/","/auth/**","/js/**","/css/**","/image/**") // 인증을 허용하는 요청 url
                    .permitAll() // 모두 허용
                    .anyRequest() // 인증이 되지않은 요청
                    .authenticated() // 인증이 필요함
                .and()
                    .formLogin() // 로그인 폼은
                    .loginPage("/auth/loginForm") // /auth/signin
                    .loginProcessingUrl("/auth/loginProc")// 스프링 시큐리티가 해당 주소로 요청오는 로그인을 가로채서 대신 로그인해준다.
                    .defaultSuccessUrl("/"); // 로그인 성공시 이동하는 url
        return http.build();
    }

}
