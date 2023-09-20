package com.local.blog.api;

import com.local.blog.config.auth.PrincipalDetail;
import com.local.blog.dto.CMRespDto;
import com.local.blog.dto.user.SignupDto;
import com.local.blog.dto.user.UserUpdateDto;
import com.local.blog.handler.CustomValidationApiException;
import com.local.blog.model.User;
import com.local.blog.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class UserApiController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/auth/joinProc")
    public CMRespDto<?> save(@RequestBody @Valid SignupDto respDto, BindingResult bindingResult){ // username, password, email
        log.info("singupRespDto : {}",respDto);
        if (bindingResult.hasErrors()){
            Map<String,String> errorMap = new HashMap<>();
            for (FieldError error: bindingResult.getFieldErrors()){
                errorMap.put(error.getField(),error.getDefaultMessage());
                log.info("error: {}" , error.getField());
                log.info("getDefaultMessage: {}" , error.getDefaultMessage());
            }
            throw new CustomValidationApiException("유효성검사에 실패했습니다",errorMap);
        } else {
            User user = respDto.toEntity();
            userService.회원가입(user);
            return new CMRespDto<>(1,"회원가입성공",user); // 자바오브젝트를 json으로 변환해서 리턴(Jackson)
        }
    }
    @PutMapping("/api/user/{id}")
    public CMRespDto<?> update(
            @PathVariable int id,
            @Valid UserUpdateDto userUpdateDto,
            BindingResult bindingResult,
            @AuthenticationPrincipal PrincipalDetail principalDetail
    ){
        log.info("userApiController start");
        if (bindingResult.hasErrors()){
            Map<String,String> errorMap = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()){
                errorMap.put(error.getField(),error.getDefaultMessage());
                log.info(error.getField(),error.getDefaultMessage());
            }
            throw new CustomValidationApiException("유효성검사실패",errorMap);
        } else {
            log.info("유효성 검사 성공");
            User userEntity = userService.회원수정(id,userUpdateDto.toEntity());
            // 여기서는 트랜잭션이 종료되기 때문에 DB에 값은 변경이 됐음
            // 하지만 세션값은 변경되지 않은 상태이기 때문에 우리가 직접 세션값을 변경해줄 것임.

            //세션 등록
            principalDetail.setUser(userEntity);
            return new CMRespDto<>(1,"회원정보수정성공",userEntity);

        }

    }


/*    @PutMapping("/user")
    public CMRespDto<?> update(@RequestBody User user){ // key=value, x-www-form-urlencoded
        userService.회원수정(user);
        // 여기서는 트랜잭션이 종료되기 때문에 DB에 값은 변경이 됐음
        // 하지만 세션값은 변경되지 않은 상태이기 때문에 우리가 직접 세션값을 변경해줄 것임.

        //세션 등록
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new CMRespDto<Integer>(HttpStatus.OK.value(), 1);
    }*/

}
