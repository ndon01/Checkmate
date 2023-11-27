package com.checkmate.users.repository;

import com.checkmate.users.model.entity.Friendship;
import com.checkmate.users.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    // Existing methods
    List<Friendship> findFriendshipsByUserOne(User userOne);
    List<Friendship> findFriendshipsByUserTwo(User userTwo);


    // Get if a friendship exists between two users
    Friendship findByUserId1AndUserId2(Long userId1, Long userId2);

    // Get if a user1 sent a friend request to user2
    Friendship findByUserId1AndUserId2AndStatus(Long userId1, Long userId2, Friendship.FriendshipStatus status);

    // Get all sent friend requests for a user
    List<Friendship> findByRequesterIdAndStatus(Long userId, Friendship.FriendshipStatus status);

    // Get all pending friend requests for a user
    List<Friendship> findByReceiverIdAndStatus(Long userId, Friendship.FriendshipStatus status);

    // Get all friends related to a user
    List<Friendship> findByRequesterIdOrReceiverIdAndStatus(Long userId, Long userId2, Friendship.FriendshipStatus status);
}
