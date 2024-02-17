package com.moyur.profile;

import java.io.IOException;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.moyur.aws.S3Service;
import com.moyur.jwt.UserEntity;
import com.moyur.jwt.UserRepository;

@Service
public class ProfileService {
	
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final S3Service s3Service;
    
    public ProfileService(ProfileRepository profileRepository, UserRepository userRepository, S3Service s3Service) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
        this.s3Service = s3Service;
    }
    
    public ProfileEntity getProfileByUsername(String username) {
        return profileRepository.findByUserid_Username(username)
            .orElseThrow(() -> new UsernameNotFoundException("해당 사용자의 프로필을 찾을 수 없습니다."));
    }
    
    @Transactional
    public void createProfileIfNotExist(String username) {
        if (!profileRepository.existsByUserid_Username(username)) {
            UserEntity userEntity = userRepository.findByUsername(username)
                .orElseGet(() -> {
                    UserEntity newUser = new UserEntity();
                    newUser.setUsername(username);
                    return userRepository.save(newUser);
                });

            ProfileEntity profileEntity = new ProfileEntity();
            profileEntity.setUserid(userEntity);
            profileRepository.save(profileEntity);
        }
    }
    
    @Transactional
    public String uploadProfile(String username, MultipartFile profileImageFile, String userType) {
        UserEntity userEntity = userRepository.findByUsername(username)
                                              .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        // 프로필 찾거나 생성
        ProfileEntity profileEntity = profileRepository.findByUserid_Username(username)
                                                       .orElseGet(() -> {
                                                           ProfileEntity newProfile = new ProfileEntity();
                                                           newProfile.setUserid(userEntity);
                                                           return profileRepository.save(newProfile);
                                                       });

        String profileImageUrl = null;
        
        // 이미지 파일이 제공된 경우 S3에 업로드
        if (profileImageFile != null) {
            try {
                profileImageUrl = s3Service.s3Upload(profileImageFile);
                profileEntity.setProfileImageUrl(profileImageUrl);
            } catch (IOException e) {
                throw new RuntimeException("업로드 실패", e);
            }
        }

        // 사용자 유형 정보가 제공된 경우 업데이트
        if (userType != null) {
            UserType userTypeEnum = UserType.fromString(userType);  
            profileEntity.setUserType(userTypeEnum);  
        }

        // 프로필 저장
        profileRepository.save(profileEntity);

        return profileImageUrl;
    }


    @Transactional
    public void updateBiography(String username, String biography) {
        // 프로필 찾기
        ProfileEntity profileEntity = profileRepository.findByUserid_Username(username)
                                                       .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        // 자기소개 정보가 제공된 경우 업데이트
        if (biography != null) {
            profileEntity.setBiography(biography);
        }

        // 프로필 저장
        profileRepository.save(profileEntity);
    }
}
