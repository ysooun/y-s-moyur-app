package com.moyur;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import com.moyur.aws.S3Service;
import com.moyur.jwt.UserEntity;
import com.moyur.jwt.UserRepository;
import com.moyur.profile.ProfileEntity;
import com.moyur.profile.ProfileRepository;
import com.moyur.profile.ProfileService;

public class ProfileServiceTest {
	
	@Test
	public void uploadProfileTest() {
	    // Mocking
	    UserRepository mockUserRepo = mock(UserRepository.class);
	    ProfileRepository mockProfileRepo = mock(ProfileRepository.class);
	    S3Service mockS3Service = mock(S3Service.class);

	    // Return a user when UserRepository::findByUsername is called
	    UserEntity userEntity = new UserEntity();
	    when(mockUserRepo.findByUsername(anyString())).thenReturn(Optional.of(userEntity));

	    // Return a profile when ProfileRepository::findByUserid_Username is called
	    ProfileEntity profileEntity = new ProfileEntity();
	    profileEntity.setUserid(userEntity);
	    when(mockProfileRepo.findByUserid_Username(anyString())).thenReturn(Optional.of(profileEntity));

	    // Return a URL when S3Service::s3Upload is called
	    try {
	        doReturn("http://example.com/image.jpg").when(mockS3Service).s3Upload(any());
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    // Service 생성
	    ProfileService profileService = new ProfileService(mockProfileRepo, mockUserRepo, mockS3Service);

	    // 파일 준비 (실제 경우에는 실제 MultipartFile 객체를 생성해야 함)
	    MultipartFile mockMultipartFile = mock(MultipartFile.class);

	    // 테스트 실행
	    String result = profileService.uploadProfile("testUser", mockMultipartFile, "USER");  // "USER"로 변경

	    // 결과 검증
	    assertEquals("http://example.com/image.jpg", result);

	    // 메소드 호출 검증
	    verify(mockUserRepo, times(1)).findByUsername("testUser");
	    verify(mockProfileRepo, times(1)).findByUserid_Username("testUser");
	    try {
	        verify(mockS3Service, times(1)).s3Upload(mockMultipartFile);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}


}
