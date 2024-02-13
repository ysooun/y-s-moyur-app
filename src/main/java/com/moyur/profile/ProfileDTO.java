package com.moyur.profile;

public class ProfileDTO {
    private String profileimageurl;
    private String username;
    private String usertype;
    private String biography;

    public ProfileDTO(String profileimageurl, String username, String usertype, String biography) {
        this.profileimageurl = profileimageurl;
        this.username = username;
        this.usertype = usertype;
        this.biography = biography;
    }

	public String getProfileimageurl() {
		return profileimageurl;
	}

	public void setProfileimageurl(String profileimageurl) {
		this.profileimageurl = profileimageurl;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public String getBiography() {
		return biography;
	}

	public void setBiography(String biography) {
		this.biography = biography;
	}

}
