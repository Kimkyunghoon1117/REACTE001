package com.edu.boardback.dto.object;


import java.util.*;
import com.edu.boardback.repository.resultSet.GetCommentListResultSet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentListItem {
    private String nickname;
    private String profileImage;
    private String writeDatetime;
    private String content;

    public CommentListItem(GetCommentListResultSet resultSet){
        this.nickname = resultSet.getNickname();
        this.profileImage =resultSet.getProfileImage();
        this.writeDatetime = getWriteDatetime();
        this.content = resultSet.getContent();
    }
    
    public static List<CommentListItem> copyList(List<GetCommentListResultSet> resultSets){
        List<CommentListItem> list = new ArrayList<>();
        for(GetCommentListResultSet resultSet : resultSets){
            CommentListItem commentListItem = new CommentListItem(resultSet);
            list.add(commentListItem);
        }
        return list;
    }
}
