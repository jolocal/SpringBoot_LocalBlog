package com.local.blog.repository;

import com.local.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

// DAO
// 자동으로 bean등록이 된다.
// @Repository // 생략가능
public interface UserRepository extends JpaRepository<User, Integer> {

}
    /*
    // JPA Naming 전량
    // select * from user where username = ? and password = ?;
    User findByUsernameAndPassword(String username, String password);

    // native 쿼리
    @Query(value = "select * from user where username = ? and password = ?", nativeQuery = true)
    User login(String username, String password)
     */
