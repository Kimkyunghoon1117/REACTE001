package com.edu.boardback.service;

import org.springframework.http.ResponseEntity;

import com.edu.boardback.dto.request.auth.SignUpRequestDto;
import com.edu.boardback.dto.response.auth.SignUpResponseDto;

public interface AuthService {
    
    ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto);
}
