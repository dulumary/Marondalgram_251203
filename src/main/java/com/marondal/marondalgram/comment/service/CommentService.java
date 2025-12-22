package com.marondal.marondalgram.comment.service;

import com.marondal.marondalgram.comment.domain.Comment;
import com.marondal.marondalgram.comment.dto.CommentDetail;
import com.marondal.marondalgram.comment.repository.CommentRepository;
import com.marondal.marondalgram.user.domain.User;
import com.marondal.marondalgram.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;

    public boolean createComment(long postId, long userId, String contents) {

        Comment comment = Comment.builder()
                .postId(postId)
                .userId(userId)
                .contents(contents)
                .build();

        try {
            commentRepository.save(comment);
        } catch(DataAccessException e) {
            return false;
        }

        return true;

    }


    public List<CommentDetail> getCommentList(long postId) {

        List<Comment> commentList = commentRepository.findByPostId(postId);

        List<CommentDetail> commentDetailList = new ArrayList<>();
        for(Comment comment:commentList) {

            User user = userService.getUserById(comment.getUserId());

            CommentDetail commentDetail = CommentDetail.builder()
                    .id(comment.getId())
                    .userId(comment.getUserId())
                    .loginId(user.getLoginId())
                    .contents(comment.getContents())
                    .build();

            commentDetailList.add(commentDetail);
        }

        return commentDetailList;
    }
}

