package com.edu.boardback.service;

import org.springframework.http.ResponseEntity;

import com.edu.boardback.dto.request.board.GetCommentListResponseDto;
import com.edu.boardback.dto.request.board.PostBoardRequestDto;
import com.edu.boardback.dto.request.board.PostCommentRequestDto;
import com.edu.boardback.dto.response.borad.GetBoardResponseDto;
import com.edu.boardback.dto.response.borad.GetFavoriteListResponseDto;
import com.edu.boardback.dto.response.borad.IncreaseViewCountResponseDto;
import com.edu.boardback.dto.response.borad.PostBoardResponseDto;
import com.edu.boardback.dto.response.borad.PostCommentResponseDto;
import com.edu.boardback.dto.response.borad.PutFavoriteResponseDto;

public interface BoardService {
    ResponseEntity<? super GetBoardResponseDto> getBoard(Integer boardNumber);
    ResponseEntity<? super GetFavoriteListResponseDto> getFavoriteList(Integer boardNumber);
    ResponseEntity<? super GetCommentListResponseDto> getCommentList(Integer boardNumber);

    ResponseEntity<? super PostBoardResponseDto> postBorad(PostBoardRequestDto dto, String email);  
    ResponseEntity<? super PostCommentResponseDto> postComment(PostCommentRequestDto dto, Integer boardNumber, String email);

    ResponseEntity<? super PutFavoriteResponseDto> putFavorite(Integer boardNumber, String email);

    ResponseEntity<? super IncreaseViewCountResponseDto> increaseViewCount(Integer boardNumber);
}
