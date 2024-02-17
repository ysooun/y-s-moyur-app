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
    @JoinColumn(name = "profiles_users", unique = true)
    private UserEntity user;

    @Column(name = "profileimageurl", nullable = false)
    private String profileImageUrl = "default_profile_image";

    @Column(name = "biography", nullable = true)
    private String biography;

    @Enumerated(EnumType.STRING)
    @Column(name = "usertype", nullable = false)
    private UserType userType = UserType.NORMAL;

    @Column(name = "followercount", nullable = false)
    private int followerCount;
    @Column(name = "followingcount", nullable = false)
    private int followingCount;
    
    
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
	    if (biography != null) {
	        this.biography = biography;
	    }
	}
	
	public UserType getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
	    if (userType != null && !userType.equals("undefined")) {
	        this.userType = UserType.fromString(userType);
	    }
	}
	
	public int getFollowerCount() {
		return followerCount;
	}
    public void setFollowerCount(int followerCount) {
        this.followerCount = followerCount;
    } 
	public int getFollowingCount() {
		return followingCount;
	}
	public void setFollowingCount(int followingCount) {
		this.followingCount = followingCount;
	}
}