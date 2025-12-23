package com.marondal.marondalgram.like.repository;

import com.marondal.marondalgram.like.domain.Like;
import com.marondal.marondalgram.like.domain.LikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, LikeId> {

//    SELECT COUNT(*) FROM `like` WHERE `post_id` = 2;
    public int countByPostId(long postId);
    // WHERE `post_id` = #{} AND `user_id` = #{}
    public boolean existsByPostIdAndUserId(long postId, long userId);

    // DELETE FROM `like` WHERE `post_id` = #{}
    public void deleteByPostId(long postId);

}
