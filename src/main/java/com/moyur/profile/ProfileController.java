package com.moyur.profile;

import java.util.Collections;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.moyur.jwt.CustomUserDetails;

@Controller
public class ProfileController {

    private final ProfileService profileService;
    
    private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/profile/{username}")
    public String showUserProfile(@PathVariable String username, Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            String loggedUsername = customUserDetails.getUsername();
            String role = customUserDetails.getAuthorities().stream()
                                           .findFirst()
                                           .orElseThrow(() -> new IllegalStateException("권한이 없습니다."))
                                           .getAuthority();

            if (role.equals("ROLE_USER")) {
                boolean isMyProfile = loggedUsername.equals(username);
                model.addAttribute("isMyProfile", isMyProfile);

                ProfileEntity profileEntity = profileService.getProfileByUsername(username);
                model.addAttribute("username", username);
                model.addAttribute("profileimageurl", profileEntity.getProfileImageUrl());
                model.addAttribute("biography", profileEntity.getBiography()); 

                return "profile";
            } 
            return "redirect:/login";
        } 
        return "redirect:/login";
    }

    @PostMapping("/profile/upload")
    public ResponseEntity<?> uploadProfile(@RequestParam("username") String username,
                                           @RequestParam("userType") String userType,
                                           @RequestParam(value = "image", required = false) MultipartFile image) {
        try {
            logger.info("uploadProfile 시작: username = {}, userType = {}", username, userType);
            String newImageUrl = profileService.uploadProfile(username, image, userType);
            logger.info("uploadProfile 완료: newImageUrl = {}", newImageUrl);
            return new ResponseEntity<>(Collections.singletonMap("newImageUrl", newImageUrl), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("uploadProfile 실패: ", e);
            return new ResponseEntity<>(Collections.singletonMap("message", "업로드 실패" + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/profile/updateBiography")
    public ResponseEntity<?> updateBiography(@RequestBody Map<String, String> params) {
        String username = params.get("username");
        String biography = params.get("biography");
        profileService.updateBiography(username, biography);
        return ResponseEntity.ok(Collections.singletonMap("message", "바이오그래피 업데이트 성공"));
    }
}

