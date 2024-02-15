package com.moyur.profile.imageupload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadDTO {
	private String username;
    private String uploadimage;
    private int likes;
    private String imagetype;
    
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUploadimage() {
		return uploadimage;
	}
	public void setUploadimage(String uploadimage) {
		this.uploadimage = uploadimage;
	}
	public int getLikes() {
		return likes;
	}
	public void setLikes(int likes) {
		this.likes = likes;
	}
	public String getImagetype() {
		return imagetype;
	}
	public void setImagetype(String imagetype) {
		this.imagetype = imagetype;
	}
    
	
}
