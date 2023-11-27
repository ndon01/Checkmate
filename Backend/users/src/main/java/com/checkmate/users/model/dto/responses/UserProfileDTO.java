package com.checkmate.users.model.dto.responses;

import lombok.Data;

@Data
public class UserProfileDTO {
    private long userId;
    private String username;
    private String displayName;
    private String biography;

    private String friendCount;
    private String followingCount;
    private String followerCount;

    private boolean isFriends; // user is friends with the profile
    private boolean isFriendRequestSent; // user has sent a friend request to the profile
    private boolean isRequestingFriendship; // user has received a friend request from the profile

    private boolean isFollowing; // user is following the profile
    private boolean isFollower;  // user is followed by the profile
}
