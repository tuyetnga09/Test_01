package com.example.test_01.repository;

import com.example.test_01.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    @Modifying
    @Transactional
    @Query("UPDATE user u SET u.gold = u.gold + :gold, u.gem = u.gem + :gem WHERE u.id = :userId")
    void updateGoldAndGems(@Param("userId") Integer userId, @Param("gold") long gold, @Param("gem") long gem);


}
