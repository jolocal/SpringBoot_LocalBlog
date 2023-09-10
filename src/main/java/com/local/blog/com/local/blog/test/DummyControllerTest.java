package com.local.blog.com.local.blog.test;

import com.local.blog.model.RoleType;
import com.local.blog.model.User;
import com.local.blog.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController // html 파일이 아니라 data를 리턴해주는 controller
@Slf4j
public class DummyControllerTest {

    @Autowired // 의존성 주입(DI)
    private UserRepository userRepository;

    @DeleteMapping("/dummy/user/{id}")
    public String delete(@PathVariable int id){

        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            return "삭제에 실패하였습니다. 해당 id는 DB에 없습니다.";
        }

        return "삭제되었습니다. id"+id;
    }


    /*
    save 함수는 id를 전달하지 않으면 insert 해주고
    save 함수는 id를 전달하면 해당 id에 대한 데이터가 있으면 update 해주고
    save 함수는 id를 전달하면 해당 id에 대한 데이터가 없으면 insert 한다.
    */
    // email, password 수정
    @Transactional // 함수 종료시에 자동 commit됨
    @PutMapping("/dummy/user/{id}")
    public User updateUser(@PathVariable int id, @RequestBody User requestUser){ // json 데이터를 요청 -> 스프링이 Java Object(MessageConverter의 Jackson 라이브러리가 변환해서 받아줌)
        log.info("id : {}",requestUser.getId());
        log.info("password : {}",requestUser.getPassword());
        log.info("Email : {}",requestUser.getEmail());

/*
        // save 함수를 사용한 update
        User user = userRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("수정에 실패하였습니다.");
        });
        user.setPassword(requestUser.getPassword());
        user.setEmail(requestUser.getEmail());

        userRepository.save(user);
*/

        // transactional를 사용한 update
        User user = userRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("수정에 실패하였습니다.");
        });
        user.setPassword(requestUser.getPassword());
        user.setEmail(requestUser.getEmail());

        // 더티 체킹
        return user;
    }


    // http://localhost:8000/dummy/user
    @GetMapping("/dummy/users")
    public List<User> list(){
        return userRepository.findAll();
    }

    // 한 페이지당 2건에 데이터를 리턴받아 볼 예정
    @GetMapping("/dummy/user")
    public List<User> pageList(
        @PageableDefault
       (
            size = 2,
            sort = "id",
            direction = Sort.Direction.DESC
       )
            Pageable pageable
    ){
        Page<User> pagingUser = userRepository.findAll(pageable);

        /* if(pagingUser.isFirst()) {
            ...
        } */

        List<User> users = pagingUser.getContent();
        return users;
    }


    //{id} 주소로 파라미터를 전달 받을 수 있음.
    // http://localhost:8000/dummy/user/3
    @GetMapping("/dummy/user/{id}")
    public User detail(@PathVariable int id){
        // user/4 을 찾으면 내가 데이터베이스에서 못찾아오게 되면 user가 null이 될것 아냐?
        // 그럼 return 할때 null 이 되잖아.. 그럼 프로그램에 문제가 있지 않겠니?
        // Optional로 너의 User 객체를 감싸서 가져올테니 null인지 아닌지 판단해서 return해!
        // User user = userRepository.findById(id);

        // 1. User user = userRepository.findById(id).get(); // 무조건 null이 아닐때 바로 꺼내서 사용
        /* 2. User user = userRepository.findById(id).orElseGet(new Supplier<User>() {
            @Override
            public User get() {
                return new User(); //빈 객체 리턴
            }
        });*/

/*       User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
            @Override
            public IllegalArgumentException get() {
                return new IllegalArgumentException("해당 유저는 없습니다. id :"+id);
            }
        });*/

/*        User user = userRepository.findById(id).orElseGet(new Supplier<User>() {
            @Override
            public User get() {
                return new User("해당 유저는 없습니다. id: "+id);
            }
        })*/

        // 람다식
        User user = userRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("해당 유저는 없습니다. id: " + id);
        });

        // 요청: 웹브라우저
        // user 객체 = 자바 오브젝트
        // 변환 ( 웹브라우저가 이해할 수 있는 데이터) -> json(Gson 라이브러리)
        // 스프링부트 = MessageConverter 라는 애가 응답시에 자동 작동
        // 만약 자바 오브젝트를 리턴하게 되면 MessageConverter가 Jackson 라이브러리를 호출해서
        // user 오브젝트를 json 변환해서 브라우저에게 던져준다.
        return user;
    }


    // http://localhost:8000/blog/dummy/join (요청)
    // http의 body에 username, password, email 데이터를 가지고 (요청)
    @PostMapping("/dummy/join")
    public String join(User user){ // key-value
        log.info("id : {}",user.getId());
        log.info("username : {}",user.getUsername());
        log.info("password : {}",user.getPassword());
        log.info("email : {}",user.getEmail());
        log.info("Role : {}",user.getRole());
        log.info("CreateDate : {}",user.getCreateDate());

        user.setRole(RoleType.USER);
        userRepository.save(user);
        return "회원가입이 완료되었습니다.";
    }
}
