package com.marondal.marondalgram.comment.service;

import com.marondal.marondalgram.comment.domain.Comment;
import com.marondal.marondalgram.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

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
}

