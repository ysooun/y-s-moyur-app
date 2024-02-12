package com.moyur.profile;

import com.moyur.jwt.UserEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "profiles")
public class ProfileEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "user_profile", unique = true)
    private UserEntity user;

    @Column(name = "profileImageUrl")
    private String profileImageUrl = "기본_이미지_URL";
    
    @Column(name = "biography")
    private String biography = "기본 바이오";

	@Enumerated(EnumType.STRING)
    @Column(name = "userType")
    private UserType userType = UserType.NORMAL;
    
    private int followerCount;

    private void updateUserType() {
        if (this.followerCount >= 10000) {
            this.userType = UserType.INFLUENCER;
        } else {
            this.userType = UserType.NORMAL;
        }
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}
	
	public String getBiography() {
		return biography;
	}
	public void setBiography(String biography) {
		this.biography = biography;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public int getFollowerCount() {
		return followerCount;
	}

	public void setFollowerCount(int followerCount) {
		this.followerCount = followerCount;
	}
	public void increaseFollowerCount() {
        this.followerCount++;
        updateUserType();
    }

    public void decreaseFollowerCount() {
        this.followerCount--;
        updateUserType();
    }
}