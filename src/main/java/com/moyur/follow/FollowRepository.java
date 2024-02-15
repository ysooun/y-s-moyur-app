package com.moyur.follow;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moyur.jwt.UserEntity;

public interface FollowRepository extends JpaRepository<FollowEntity, Long> {
    List<FollowEntity> findByFollowing(UserEntity following);
    List<FollowEntity> findByFollower(UserEntity follower);
    Optional<FollowEntity> findByFollowingAndFollower(UserEntity following, UserEntity follower);
    int countByFollowing_Username(String username);
    int countByFollower_Username(String username);
    boolean existsByFollowingAndFollower(UserEntity following, UserEntity follower);
}