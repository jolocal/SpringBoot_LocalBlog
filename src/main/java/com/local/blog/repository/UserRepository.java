package com.local.blog.repository;

import com.local.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// DAO
// 자동으로 bean등록이 된다.
// @Repository // 생략가능
public interface UserRepository extends JpaRepository<User, Integer> {
}
