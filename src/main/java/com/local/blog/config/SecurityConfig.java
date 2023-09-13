package com.local.blog.config;

import com.local.blog.config.auth.PrincipalDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// 빈등록: 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것
@Configuration //IoC
@EnableWebSecurity // 내부적으로 스프링시큐리티필터체인이 동작하여 URL 필터 적용
public class SecurityConfig {

    // 시큐리티가 대신 로그인해주는데 password를 가로채기 하는데
    // 해당 password가 뭘로 해쉬가 되어 회원가입이 되었는지 알아야
    // 같은 해쉬로 암호화해서 DB에 있는 해쉬랑 비교할 수 있음.

    // PrincipalDetailService와 BCryptPasswordEncoder를 사용하여 시큐리티 인증을 담당
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    BCryptPasswordEncoder encode(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain configure (HttpSecurity http) throws Exception {
        return http
                .csrf().disable() //csrf 토큰 비활성화 (테스트시 걸어두는게 좋음)
                .authorizeRequests() // 요청이 들어오면
                    .antMatchers("/","/auth/**","/js/**","/css/**","/image/**","/dummy/**") // 인증을 허용하는 요청 url
                    .permitAll() // 모두 허용
                    .anyRequest() // 인증이 되지않은 요청
                    .authenticated() // 인증이 필요함
                .and()
                    .formLogin() // 로그인 폼은
                    .loginPage("/auth/loginForm") // /auth/signin
                    .loginProcessingUrl("/auth/loginProc")// 스프링 시큐리티가 해당 주소로 요청오는 로그인을 가로채서 대신 로그인해준다.
                    .defaultSuccessUrl("/") // 로그인 성공시 이동하는 url
                .and()
                .build();
    }

}
