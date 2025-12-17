package com.marondal.marondalgram.like.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@IdClass(LikeId.class)
@Table(name="`like`")
@Entity
public class Like {

    @Id
    private long postId;
    @Id
    private long userId;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
