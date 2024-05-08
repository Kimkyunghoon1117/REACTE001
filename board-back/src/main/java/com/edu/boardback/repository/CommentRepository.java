package com.edu.boardback.repository;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.edu.boardback.entity.CommentEntity;
import com.edu.boardback.repository.resultSet.GetCommentListResultSet;

import jakarta.transaction.Transactional;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer>{
    
    @Query(
        value=
        "SELECT " +
        "u.nickname as nickname, " +
        "u.profile_image as profileImage, " +
        "c.write_datetime as writeDatetime, " +
        "c.content as content " +
        "FROM comment as c " +
        "INNER JOIN user as u " +
        "ON c.user_email = u.email " +
        "WHERE c.board_number= ?1 " +
        "ORDER BY writeDatetime DESC ",
        nativeQuery = true
    )
    List<GetCommentListResultSet> getCommentList(Integer boardNumber);

    @Transactional
    void deleteByBoardNumber(Integer boardNumber);
    
}
