package com.edu.boardback.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edu.boardback.entity.CommentEntity;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer>{
    
}
