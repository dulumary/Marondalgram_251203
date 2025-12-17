package com.marondal.marondalgram.post.dto;

import lombok.Builder;
import lombok.Getter;

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

}
