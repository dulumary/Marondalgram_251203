package com.marondal.marondalgram.post.service;

import com.marondal.marondalgram.comment.domain.Comment;
import com.marondal.marondalgram.comment.dto.CommentDetail;
import com.marondal.marondalgram.comment.service.CommentService;
import com.marondal.marondalgram.common.FileManager;
import com.marondal.marondalgram.like.service.LikeService;
import com.marondal.marondalgram.post.domain.Post;
import com.marondal.marondalgram.post.dto.PostDetail;
import com.marondal.marondalgram.post.repository.PostRepository;
import com.marondal.marondalgram.user.domain.User;
import com.marondal.marondalgram.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor  // 필수 멤버변수 를 생성자를 통해 대응
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;
    private final LikeService likeService;
    private final CommentService commentService;

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

    public List<PostDetail> getPostList(long userId) {

        List<Post> postList = postRepository.findAll(Sort.by("id").descending());

        List<PostDetail> postDetailList = new ArrayList<>();
        for(Post post:postList) {
            // Post -> PostDetail
            // 1 + N 문제  : cache
            User user = userService.getUserById(post.getUserId());

            int likeCount = likeService.countByPostId(post.getId());
            boolean isLike = likeService.isLikeByPostIdAndUserId(post.getId(), userId);

            List<CommentDetail> commentList = commentService.getCommentList(post.getId());

            PostDetail postDetail = PostDetail.builder()
                    .id(post.getId())
                    .contents(post.getContents())
                    .imagePath(post.getImagePath())
                    .userId(post.getUserId())
                    .loginId(user.getLoginId())
                    .likeCount(likeCount)
                    .isLike(isLike)
                    .commentList(commentList)
                    .build();
            postDetailList.add(postDetail);
        }

        return postDetailList;
    }


    @Transactional
    public boolean deletePost(long id, long userId) {

        Optional<Post> optionalPost = postRepository.findById(id);

        if(optionalPost.isPresent()) {
            try {

                Post post = optionalPost.get();

                if(post.getUserId() != userId) {
                    return false;
                }

                likeService.deleteLikeByPostId(post.getId());
                commentService.deleteCommentByPostId(post.getId());

                postRepository.delete(post);

                FileManager.removeFile(post.getImagePath());

            } catch (DataAccessException e) {
                return false;
            }

        } else {
            return false;
        }

        return true;
    }
}
