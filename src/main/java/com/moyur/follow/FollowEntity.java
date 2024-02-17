package com.moyur.follow;

import com.moyur.jwt.UserEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "follows")
public class FollowEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long followid;
    
    @ManyToOne
    @JoinColumn(name = "follower")
    private UserEntity follower;  // 팔로우하는 사람

    @ManyToOne
    @JoinColumn(name = "following")
    private UserEntity following;  // 팔로우 받는 사람

	public Long getFollowid() {
		return followid;
	}
	public void setFollowid(Long followid) {
		this.followid = followid;
	}

	public UserEntity getFollower() {
		return follower;
	}
	public void setFollower(UserEntity followerUser) {
		this.follower = followerUser;
	}
	
	public UserEntity getFollowing() {
		return following;
	}
	public void setFollowing(UserEntity followingUser) {
		this.following = followingUser;
	}
}