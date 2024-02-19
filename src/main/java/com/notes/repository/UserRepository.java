package com.notes.repository;

import com.notes.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  boolean existsByUsername(String username);

  Optional<User> findByUsername(String username);

  @Query(
      value =
          """
              SELECT exists(
                SELECT 1
                FROM users u LEFT JOIN notes n
                ON u.id = n.user_id
                WHERE u.id = :userId
                AND n.id = :noteId
              )
              """,
      nativeQuery = true)
  boolean isNoteOwner(
      @Param("userId") Long userId, @Param("noteId") Long noteId);
}
