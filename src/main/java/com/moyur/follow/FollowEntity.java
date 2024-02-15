package com.moyur.follow;

import com.moyur.jwt.UserEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "followers")
public class FollowEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "follower_username")
    private UserEntity follower;  // 팔로우하는 사람

    @ManyToOne
    @JoinColumn(name = "following_username")
    private UserEntity following;  // 팔로우 받는 사람

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserEntity getFollower() {
		return follower;
	}

	public void setFollower(UserEntity follower) {
		this.follower = follower;
	}

	public UserEntity getFollowing() {
		return following;
	}

	public void setFollowing(UserEntity following) {
		this.following = following;
	}
}