package com.local.blog.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.local.blog.model.KakaoProfile;
import com.local.blog.model.OAuthToken;
import com.local.blog.model.User;
import com.local.blog.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

// 인증이 안된 사용자들이 출입할 수 있는 경로를 /auth/** 허용
// 그냥 주소가 / 이면 index.jsp 허용
// static 이하에 있는 /js/**, /css/**, /image/**
@Controller
@Slf4j
public class UserController {

    @Value("${local.key}")
    private String localKey;

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/auth/loginForm")
    public String loginForm() {
        return "user/loginForm";
    }

    @GetMapping("/auth/joinForm")
    public String joinForm() {
        return "user/joinForm";
    }

    @GetMapping("/user/updateForm")
    public String updateForm() {
        return "user/updateForm";
    }

    // kakao 로그인
    @GetMapping("/auth/kakao/callback")
    public String kakaoCallback(String code) { // 데이터를 return 해주는 컨트롤러 함수
        // AccessToken 부여

        // POST 방식으로 Key=value 데이터를 카카오쪽으로 요청
        // Retrofit2 ( 안드로이드에서 많이 사용)
        // OkHttp
        // Rest Template

        // HttpHeader 오브젝트 생성
        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded"); //key-value 형태

        // HttpBody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "c61f08f62473c3801b979b56fa3028de");
        params.add("redirect_uri", "http://localhost:8000/auth/kakao/callback");
        params.add("code", code);

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        // Http 요청하기 - POST 방식으로 - 그리고 response 변수의 응답 받음
        ResponseEntity<String> response = rt.exchange( // exchange함수는 HttpEntity를 받음
                "https://kauth.kakao.com/oauth/token", // kakao에서 제공한 token 부여 주소
                HttpMethod.POST, // httpMethod
                kakaoTokenRequest, // data: head+body
                String.class // 응답받을 타입
        );

        // Gson, Json Simple, ObjectMapper
        // JSON 데이터를 자바 오브젝트로 바꾸기
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        OAuthToken oauthToken = null;
        try {
            oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // 카카오에 토큰을 통해 사용자 정보 요청하기
        // HttpHeader 오브젝트 생성
        RestTemplate rt2 = new RestTemplate();
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer " + oauthToken.getAccess_token());
        headers2.add("Content-Type", "application/x-www-form-urlencoded"); //key-value 형태

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 = new HttpEntity<>(headers2);

        // Http 요청하기 - POST 방식으로 - 그리고 response 변수의 응답 받음
        ResponseEntity<String> response2 = rt2.exchange( // exchange함수는 HttpEntity를 받음
                "https://kapi.kakao.com/v2/user/me", // kakao에서 제공한 token 부여 주소
                HttpMethod.POST, // httpMethod
                kakaoProfileRequest2, // data: head+body
                String.class // 응답받을 타입
        );

        ObjectMapper objectMapper2 = new ObjectMapper();
        objectMapper2.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE); //snake->camel
        KakaoProfile kakaoProfile = null;
        try {
            kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // kakao로 로그인한 유저 블로그 서버에 회원가입시키기
        // User 오브젝트: username, password, email (id(시퀀스),role(user), createDate(자동))
        log.info("카카오 아이디(번호): {}", kakaoProfile.getId());
        log.info("카카오 이메일: {}", kakaoProfile.getKakaoAccount().getEmail());

        log.info("블로그서버 유저네임: {}_{}", kakaoProfile.getKakaoAccount().getEmail(), kakaoProfile.getId());
        log.info("블로그서버 이메일: {}", kakaoProfile.getKakaoAccount().getEmail());
        log.info("블로그서버 패스워드: {}", localKey);
       /* UUID garbagePassword = UUID.randomUUID();
        // UUID란 -> 중복되지 않은 어떤 특정 값을 만들어내는 알고리즘*/

        User kakaoUser = User.builder()
                .username(kakaoProfile.getKakaoAccount().getEmail() + "_" + kakaoProfile.getId())
                .password(localKey)
                .email(kakaoProfile.getKakaoAccount().getEmail())
                .oauth("kakao")
                .build();

        // 블로그 가입자 혹은 비가입자 체크해서 처리
        User originUser = userService.회원찾기(kakaoUser.getUsername());

        // 블로그 비가입자면 로그인처리
        if (originUser.getUsername() == null) {
            log.info("기존 회원이 아니기에 자동 회원가입을 진행합니다.");
            userService.회원가입(kakaoUser);
        }

        log.info("자동 로그인을 진행합니다.");
        // 로그인처리(세션등록)
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), localKey));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "redirect:/";
    }
}
