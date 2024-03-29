package com.moyur.jwt;

import java.util.ArrayList;
import java.util.List;

import com.moyur.follow.FollowEntity;
import com.moyur.profile.ProfileEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userid;
    
    @Column(unique = true)
    private String username;  
    
    private String password;
    private String email;
    private String role;
    
    @OneToOne(mappedBy = "userid", cascade = CascadeType.ALL)
    private ProfileEntity profileid;
    
    @OneToMany(mappedBy = "following")
    private List<FollowEntity> follower = new ArrayList<>();
    @OneToMany(mappedBy = "follower")
    private List<FollowEntity> following = new ArrayList<>();

    
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	public ProfileEntity getProfileid() {
		return profileid;
	}
	public void setProfileid(ProfileEntity profileid) {
		this.profileid = profileid;
	}
	
	public List<FollowEntity> getFollower() {
		return follower;
	}
	public void setFollower(List<FollowEntity> follower) {
		this.follower = follower;
	}
	public List<FollowEntity> getFollowing() {
		return following;
	}
	public void setFollowing(List<FollowEntity> following) {
		this.following = following;
	}
}