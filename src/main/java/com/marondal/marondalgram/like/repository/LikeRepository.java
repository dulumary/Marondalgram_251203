package com.marondal.marondalgram.like.repository;

import com.marondal.marondalgram.like.domain.Like;
import com.marondal.marondalgram.like.domain.LikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, LikeId> {
}
