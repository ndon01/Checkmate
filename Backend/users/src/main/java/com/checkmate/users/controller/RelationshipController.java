package com.checkmate.users.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.checkmate.users.model.dto.requests.ChangeDisplayNameRequestDTO;
import com.checkmate.users.model.dto.responses.UserContextResponseDTO;
import com.checkmate.users.model.entity.Friendship;
import com.checkmate.users.model.entity.User;
import com.checkmate.users.repository.FriendshipRepository;
import com.checkmate.users.repository.UserRepository;
import com.checkmate.users.security.PermissionRequired;
import com.checkmate.users.security.RequiresJWT;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class RelationshipController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FriendshipRepository friendshipRepository;

    @PostMapping("/sendFriendRequest")
    @RequiresJWT
    @Transactional
    public ResponseEntity<?> sendFriendRequest(@RequestParam("userId") long userId, HttpServletRequest request) {
        DecodedJWT decodedJWT = (DecodedJWT) request.getAttribute("decodedJWT");
        Long requesterId = decodedJWT.getClaim("userId").asLong();

        if (decodedJWT == null) {
            return ResponseEntity.status(401).build();
        }

        Optional<User> optionalUser = userRepository.findByCredentialId(decodedJWT.getClaim("userId").asLong());

        if (optionalUser.isEmpty()) {
            return  ResponseEntity.status(401).build();
        }

        User userToRequest = optionalUser.get();

        // Check if user is already friends with the user
        Friendship requesterToRequested = friendshipRepository.findByUserId1AndUserId2(requesterId, userId);
        Friendship requestedToRequester = friendshipRepository.findByUserId1AndUserId2(userId, requesterId);

        // Friendship request exists from requested to requester, check if it's pending
        if (requestedToRequester != null) {
            if (requestedToRequester.getStatus() == Friendship.FriendshipStatus.PENDING) {
                // Friendship request is pending, accept the request
                requestedToRequester.setStatus(Friendship.FriendshipStatus.FRIENDS);

                friendshipRepository.save(requestedToRequester);

                return ResponseEntity.ok().body("Friend request accepted");
            } else {
                return ResponseEntity.status(400).body("You are already friends with this user");
            }
        }

        // Friendship request exists from requester to requested, check if it's pending
        if (requesterToRequested != null) {
            if (requesterToRequested.getStatus() == Friendship.FriendshipStatus.PENDING) {
                return ResponseEntity.status(400).body("Friend request already sent");
            } else {
                return ResponseEntity.status(400).body("You are already friends with this user");
            }
        }

        // Friendship request does not exist, create a new one

        Friendship friendship = new Friendship();

        friendship.setRequesterId(requesterId);
        friendship.setReceiverId(userId);
        friendship.setStatus(Friendship.FriendshipStatus.PENDING);

        friendshipRepository.save(friendship);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/cancelFriendRequest")
    @RequiresJWT
    public ResponseEntity<?> cancelFriendRequest(@RequestParam("userId") long userId, HttpServletRequest request) {
        DecodedJWT decodedJWT = (DecodedJWT) request.getAttribute("decodedJWT");

        if (decodedJWT == null) {
            return ResponseEntity.status(401).build();
        }

        Optional<User> optionalUser = userRepository.findByCredentialId(decodedJWT.getClaim("userId").asLong());

        if (optionalUser.isEmpty()) {
            return  ResponseEntity.status(401).build();
        }

        User user = optionalUser.get();

        return ResponseEntity.ok().build();
    }

    @PostMapping("/acceptFriendRequest")
    @RequiresJWT
    public ResponseEntity<?> acceptFriendRequest(@RequestParam("userId") long userId, HttpServletRequest request) {
        DecodedJWT decodedJWT = (DecodedJWT) request.getAttribute("decodedJWT");

        if (decodedJWT == null) {
            return ResponseEntity.status(401).build();
        }

        Optional<User> optionalUser = userRepository.findByCredentialId(decodedJWT.getClaim("userId").asLong());

        if (optionalUser.isEmpty()) {
            return  ResponseEntity.status(401).build();
        }

        User user = optionalUser.get();

        return ResponseEntity.ok().build();
    }

    @PostMapping("/denyFriendRequest")
    @RequiresJWT
    public ResponseEntity<?> denyFriendRequest(@RequestParam("userId") long userId, HttpServletRequest request) {
        DecodedJWT decodedJWT = (DecodedJWT) request.getAttribute("decodedJWT");

        if (decodedJWT == null) {
            return ResponseEntity.status(401).build();
        }

        Optional<User> optionalUser = userRepository.findByCredentialId(decodedJWT.getClaim("userId").asLong());

        if (optionalUser.isEmpty()) {
            return  ResponseEntity.status(401).build();
        }

        User user = optionalUser.get();

        return ResponseEntity.ok().build();
    }

    @PostMapping("/unfriendUser")
    @RequiresJWT
    public ResponseEntity<?> unfriendUser(@RequestParam("userId") long userId, HttpServletRequest request) {
        DecodedJWT decodedJWT = (DecodedJWT) request.getAttribute("decodedJWT");

        if (decodedJWT == null) {
            return ResponseEntity.status(401).build();
        }

        Optional<User> optionalUser = userRepository.findByCredentialId(decodedJWT.getClaim("userId").asLong());

        if (optionalUser.isEmpty()) {
            return  ResponseEntity.status(401).build();
        }

        User user = optionalUser.get();

        return ResponseEntity.ok().build();
    }

    @PostMapping("/followUser")
    @RequiresJWT
    public ResponseEntity<?> followUser(@RequestParam("userId") long userId, HttpServletRequest request) {
        DecodedJWT decodedJWT = (DecodedJWT) request.getAttribute("decodedJWT");

        if (decodedJWT == null) {
            return ResponseEntity.status(401).build();
        }

        Optional<User> optionalUser = userRepository.findByCredentialId(decodedJWT.getClaim("userId").asLong());

        if (optionalUser.isEmpty()) {
            return  ResponseEntity.status(401).build();
        }

        User user = optionalUser.get();

        return ResponseEntity.ok().build();
    }

    @PostMapping("/unfollowUser")
    @RequiresJWT
    public ResponseEntity<?> unfollowUser(@RequestParam("userId") long userId, HttpServletRequest request) {
        DecodedJWT decodedJWT = (DecodedJWT) request.getAttribute("decodedJWT");

        if (decodedJWT == null) {
            return ResponseEntity.status(401).build();
        }

        Optional<User> optionalUser = userRepository.findByCredentialId(decodedJWT.getClaim("userId").asLong());

        if (optionalUser.isEmpty()) {
            return  ResponseEntity.status(401).build();
        }

        User followedUser = optionalUser.get();

        // Check if user is already following the user



        // If not, return 400

        // If so, unfollow the user



        return ResponseEntity.ok().build();
    }


}
