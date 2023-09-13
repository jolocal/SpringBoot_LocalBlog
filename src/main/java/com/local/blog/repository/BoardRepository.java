package com.local.blog.repository;

import com.local.blog.model.Board;
import com.local.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface BoardRepository extends JpaRepository<Board, Integer> {

}

