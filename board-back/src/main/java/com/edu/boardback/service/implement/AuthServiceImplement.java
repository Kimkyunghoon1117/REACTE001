package com.edu.boardback.service.implement;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.edu.boardback.dto.request.auth.SignInRequestDto;
import com.edu.boardback.dto.request.auth.SignUpRequestDto;
import com.edu.boardback.dto.response.ResponseDto;
import com.edu.boardback.dto.response.auth.SignInResponseDto;
import com.edu.boardback.dto.response.auth.SignUpResponseDto;
import com.edu.boardback.entity.UserEntity;
import com.edu.boardback.provider.JwtProvider;
import com.edu.boardback.repository.UserRepository;
import com.edu.boardback.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImplement implements AuthService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto) {

        try{
            String email = dto.getEmail();
            boolean existedEmail = userRepository.existsByEmail(email);
            if(existedEmail) return SignUpResponseDto.duplicateEmail();

            String nickname = dto.getNickname();
            boolean existedNickname = userRepository.existsByNickname(nickname);
            if(existedNickname) return SignUpResponseDto.duplicateNickname();

            String telNumber = dto.getTelNumber();
            boolean existedTelNumber = userRepository.existsByTelNumber(telNumber);
            if(existedTelNumber) return SignUpResponseDto.duplicateTelNumber();

            String password = dto.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            dto.setPassword(encodedPassword);

            UserEntity userEntity = new UserEntity(dto);
            userRepository.save(userEntity);

        }catch(Exception exception){
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return SignUpResponseDto.success();
    }

    @Override
    public ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto) {
        
        String token = null;

        try {

            String email = dto.getEmail();
            UserEntity userEntity = userRepository.findByEmail(email);
            if(userEntity == null) return SignInResponseDto.signInFail();

            String password = dto.getPassword();
            String encodePassword = userEntity.getPassword();
            boolean isMatched = passwordEncoder.matches(password, encodePassword);
            if(!isMatched) return SignInResponseDto.signInFail();

            token = jwtProvider.create(email);

            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return SignInResponseDto.success(token);
    }
}
