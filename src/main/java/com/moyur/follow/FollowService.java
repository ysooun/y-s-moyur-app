package com.moyur.follow;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.moyur.jwt.UserEntity;
import com.moyur.jwt.UserRepository;
import com.moyur.profile.ProfileEntity;
import com.moyur.profile.ProfileRepository;

import jakarta.transaction.Transactional;

@Service
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    public FollowService(FollowRepository followRepository, UserRepository userRepository, ProfileRepository profileRepository) {
        this.followRepository = followRepository;
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
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
    @Transactional
    public void updateFollowerAndFollowingCounts(String followerUsername, String followingUsername) {
        int followerCount = getFollowerCount(followingUsername);
        int followingCount = getFollowingCount(followerUsername);

        ProfileEntity profileFollower = profileRepository.findByUser_Username(followerUsername)
            .orElseThrow(() -> new IllegalArgumentException("Profile not found for user: " + followerUsername));
        ProfileEntity profileFollowing = profileRepository.findByUser_Username(followingUsername)
            .orElseThrow(() -> new IllegalArgumentException("Profile not found for user: " + followingUsername));

        // 프로필 엔티티의 일부 필드만 업데이트
        profileFollower.setFollowingCount(followingCount);
        profileFollowing.setFollowerCount(followerCount);

        // 변경된 엔티티들만 저장
        profileRepository.save(profileFollower);
        profileRepository.save(profileFollowing);
    }
    
    public boolean checkFollowExists(String targetUsername, String loggedUsername) {
    	UserEntity followingUser = userRepository.findByUsername(targetUsername)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + targetUsername));
    	UserEntity followerUser = userRepository.findByUsername(loggedUsername)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + loggedUsername));
        return followRepository.existsByFollowingAndFollower(followingUser, followerUser);
    }
}
