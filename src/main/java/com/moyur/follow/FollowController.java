package com.moyur.follow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/follows")
public class FollowController {
    private final FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @PostMapping("/{followingUsername}/{followerUsername}")
    public ResponseEntity<Void> follow(@PathVariable String followingUsername, @PathVariable String followerUsername) {
        followService.follow(followingUsername, followerUsername);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{followingUsername}/{followerUsername}")
    public ResponseEntity<Void> unfollow(@PathVariable String followingUsername, @PathVariable String followerUsername) {
        followService.unfollow(followingUsername, followerUsername);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{followingUsername}")
    public ResponseEntity<List<FollowEntity>> getFollowers(@PathVariable String followingUsername) {
        List<FollowEntity> followers = followService.getFollowers(followingUsername);
        return ResponseEntity.ok(followers);
    }
    
    @GetMapping("/{followingUsername}/followerCount")
    public ResponseEntity<Integer> getFollowerCount(@PathVariable String followingUsername) {
        int followerCount = followService.getFollowerCount(followingUsername);
        return ResponseEntity.ok(followerCount);
    }
    @GetMapping("/{followerUsername}/followingCount")
    public ResponseEntity<Integer> getFollowingCount(@PathVariable String followerUsername) {
        int followingCount = followService.getFollowingCount(followerUsername);
        return ResponseEntity.ok(followingCount);
    }

    @GetMapping("/exists/{followingUsername}/{followerUsername}")
    public ResponseEntity<Map<String, Boolean>> checkFollowExists(
            @PathVariable String followingUsername, 
            @PathVariable String followerUsername) {
        boolean exists = followService.checkFollowExists(followingUsername, followerUsername);
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);
        return ResponseEntity.ok(response);
    }
}
