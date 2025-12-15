package com.marondal.marondalgram.post.service;

import com.marondal.marondalgram.common.FileManager;
import com.marondal.marondalgram.post.domain.Post;
import com.marondal.marondalgram.post.dto.PostDetail;
import com.marondal.marondalgram.post.repository.PostRepository;
import com.marondal.marondalgram.user.domain.User;
import com.marondal.marondalgram.user.service.UserService;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    private final UserService userService;

    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    public boolean createPost(
            long userId
            , String contents
            , MultipartFile imageFile) {

        String imagePath = FileManager.saveFile(userId, imageFile);

        Post post = Post.builder()
                .userId(userId)
                .contents(contents)
                .imagePath(imagePath)
                .build();

        try {
            postRepository.save(post);
        } catch (DataAccessException e) {
            return false;
        }

        return true;


    }

    public List<PostDetail> getPostList() {

        List<Post> postList = postRepository.findAll(Sort.by("id").descending());

        List<PostDetail> postDetailList = new ArrayList<>();
        for(Post post:postList) {
            // Post -> PostDetail
            // 1 + N 문제  : cache
            User user = userService.getUserById(post.getUserId());

            PostDetail postDetail = PostDetail.builder()
                    .id(post.getId())
                    .contents(post.getContents())
                    .imagePath(post.getImagePath())
                    .userId(post.getUserId())
                    .loginId(user.getLoginId())
                    .build();
            postDetailList.add(postDetail);
        }

        return postDetailList;
    }
}
