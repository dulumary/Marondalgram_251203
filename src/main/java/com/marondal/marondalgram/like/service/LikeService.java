package com.marondal.marondalgram.like.service;

import com.marondal.marondalgram.like.domain.Like;
import com.marondal.marondalgram.like.domain.LikeId;
import com.marondal.marondalgram.like.repository.LikeRepository;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeService {

    private final LikeRepository likeRepository;

    public LikeService(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    public boolean createLike(long postId, long userId) {

        Like like = Like.builder()
                .postId(postId)
                .userId(userId)
                .build();

        try {
            likeRepository.save(like);
        } catch (DataAccessException e) {
            return false;
        }

        return true;

    }

    public int countByPostId(long postId) {
        return likeRepository.countByPostId(postId);
    }

    public boolean isLikeByPostIdAndUserId(long postId, long userId) {
        return likeRepository.existsByPostIdAndUserId(postId, userId);
    }

    public boolean deleteLike(long postId, long userId) {

        LikeId likeId = LikeId.builder()
                .postId(postId)
                .userId(userId)
                .build();

        Optional<Like> optionalLike = likeRepository.findById(likeId);

        if(optionalLike.isPresent()) {

            try {
                likeRepository.delete(optionalLike.get());
            } catch(DataAccessException e) {
                return false;
            }


        } else {
            return false;
        }

        return true;
    }

    @Transactional
    public void deleteLikeByPostId(long postId) {

        likeRepository.deleteByPostId(postId);

    }

}
