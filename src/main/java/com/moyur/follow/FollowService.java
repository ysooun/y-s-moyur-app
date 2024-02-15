package com.moyur.follow;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.moyur.jwt.UserEntity;
import com.moyur.jwt.UserRepository;

@Service
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    public FollowService(FollowRepository followRepository, UserRepository userRepository) {
        this.followRepository = followRepository;
        this.userRepository = userRepository;
    }

    public void follow(String followingUsername, String followerUsername) {
        UserEntity followingUser = userRepository.findByUsername(followingUsername)
            .orElseThrow(() -> new IllegalArgumentException("User not found: " + followingUsername));
        UserEntity followerUser = userRepository.findByUsername(followerUsername)
            .orElseThrow(() -> new IllegalArgumentException("Follower not found: " + followerUsername));

        FollowEntity followEntity = new FollowEntity();
        followEntity.setFollowing(followingUser);
        followEntity.setFollower(followerUser);
        followRepository.save(followEntity);
    }

    public void unfollow(String followingUsername, String followerUsername) {
        UserEntity followingUser = userRepository.findByUsername(followingUsername)
            .orElseThrow(() -> new IllegalArgumentException("User not found: " + followingUsername));
        UserEntity followerUser = userRepository.findByUsername(followerUsername)
            .orElseThrow(() -> new IllegalArgumentException("Follower not found: " + followerUsername));

        FollowEntity followEntity = followRepository.findByFollowingAndFollower(followingUser, followerUser)
            .orElseThrow(() -> new IllegalArgumentException("Follow not found"));
        followRepository.delete(followEntity);
    }

    public List<FollowEntity> getFollowers(String followingUsername) {
        UserEntity followingUser = userRepository.findByUsername(followingUsername)
            .orElseThrow(() -> new IllegalArgumentException("User not found: " + followingUsername));
        return followRepository.findByFollowing(followingUser);
    }

    public List<FollowEntity> getFollowings(String followerUsername) {
        UserEntity followerUser = userRepository.findByUsername(followerUsername)
            .orElseThrow(() -> new IllegalArgumentException("User not found: " + followerUsername));
        return followRepository.findByFollower(followerUser);
    }
    
    public int getFollowerCount(String followingUsername) {
        return followRepository.countByFollowing_Username(followingUsername);
    }
    
    public int getFollowingCount(String followerUsername) {
        return followRepository.countByFollower_Username(followerUsername);
    }
    
    public boolean checkFollowExists(String targetUsername, String loggedInUsername) {
        UserEntity followingUser = userRepository.findByUsername(targetUsername)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + targetUsername));
        UserEntity followerUser = userRepository.findByUsername(loggedInUsername)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + loggedInUsername));
        return followRepository.existsByFollowingAndFollower(followingUser, followerUser);
    }
}
