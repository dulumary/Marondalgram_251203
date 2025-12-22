package com.marondal.marondalgram.post.dto;

import com.marondal.marondalgram.comment.domain.Comment;
import com.marondal.marondalgram.comment.dto.CommentDetail;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class PostDetail {

    private long id;

    private String contents;
    private String imagePath;

    private long userId;
    private String loginId;

    private int likeCount;
    private boolean isLike;

    List<CommentDetail> commentList;

}
