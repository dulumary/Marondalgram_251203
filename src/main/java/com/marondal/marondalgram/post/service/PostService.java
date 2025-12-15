package com.marondal.marondalgram.post.service;

import com.marondal.marondalgram.common.FileManager;
import com.marondal.marondalgram.post.domain.Post;
import com.marondal.marondalgram.post.repository.PostRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
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

    public List<Post> getPostList() {
        return postRepository.findAll(Sort.by("id").descending());
    }
}
