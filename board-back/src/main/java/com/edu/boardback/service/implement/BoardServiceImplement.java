package com.edu.boardback.service.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.edu.boardback.dto.request.board.PostBoardRequestDto;
import com.edu.boardback.dto.response.ResponseDto;
import com.edu.boardback.dto.response.borad.GetBoardResponseDto;
import com.edu.boardback.dto.response.borad.PostBoardResponseDto;
import com.edu.boardback.entity.BoardEntity;
import com.edu.boardback.entity.ImageEntity;
import com.edu.boardback.repository.BoardRepository;
import com.edu.boardback.repository.ImageRepository;
import com.edu.boardback.repository.UserRepository;
import com.edu.boardback.repository.resultSet.GetBoardResultSet;
import com.edu.boardback.service.BoardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImplement implements BoardService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final ImageRepository imageRepository;

    @Override
    public ResponseEntity<? super GetBoardResponseDto> getBoard(Integer boardNumber) {
        
        GetBoardResultSet resultSet = null;
        List<ImageEntity> imageEntities = new ArrayList<>();
        try {

            resultSet = boardRepository.getBoard(boardNumber);
            if(resultSet == null) return GetBoardResponseDto.noExistBoard();

            imageEntities = imageRepository.findByBoardNumber(boardNumber);

            BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
            boardEntity.increaseViewCount();
            boardRepository.save(boardEntity);
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetBoardResponseDto.sucess(resultSet, imageEntities);
    }

    @Override
    public ResponseEntity<? super PostBoardResponseDto> postBorad(PostBoardRequestDto dto, String email) {
       
        try {
            boolean existedEmail = userRepository.existsByEmail(email);
            if(!existedEmail) return PostBoardResponseDto.notExistUser();

            BoardEntity boardEntity = new BoardEntity(dto, email);
            boardRepository.save(boardEntity);

            int boardNumber = boardEntity.getBoardNumber();
            List<String> boardImageList = dto.getBoardImageList();
            List<ImageEntity> imageEntities = new ArrayList<>();

            for(String image: boardImageList){
                ImageEntity imageEntity = new ImageEntity(boardNumber, image);
                imageEntities.add(imageEntity);
            }
            imageRepository.saveAll(imageEntities);

       } catch (Exception exception) {
        exception.printStackTrace();
        return ResponseDto.databaseError();
       }
       return PostBoardResponseDto.success();
    }
}
