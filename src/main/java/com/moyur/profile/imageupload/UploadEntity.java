package com.moyur.profile.imageupload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.moyur.jwt.UserEntity;

import jakarta.persistence.Column;
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
@Table(name = "images")
public class UploadEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageid;

    @ManyToOne
    @JoinColumn(name = "images_userid")
    @JsonIgnore
    private UserEntity userid;
    
    @Column(name = "imageurl")
    private String imageUrl;
    @Column(name = "likes")
    private int likes;
    @Column(name = "imagetype")
    private String imageType;
 
    
    public Long getImageid() {
		return imageid;
	}
	public void setImageid(Long imageid) {
		this.imageid = imageid;
	}
	
    public UserEntity getUserid() {
		return userid;
	}
	public void setUserid(UserEntity userid) {
		this.userid = userid;
	}
    
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	public int getLikes() {
		return likes;
	}
	public void setLikes(int likes) {
		this.likes = likes;
	}
	
	public String getImageType() {
		return imageType;
	}
	public void setImageType(String imageType) {
		this.imageType = imageType;
	}
}