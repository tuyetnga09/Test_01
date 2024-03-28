package com.example.test_01.repository;

import com.example.test_01.model.UserRank;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRankRepository extends JpaRepository<UserRank, Integer> {
    @Query(value = "SELECT\n" +
            "    user_ranking.user_id,\n" +
            "    user_ranking.top_name,\n" +
            "    CASE\n" +
            "        WHEN user_ranking.top_name = 'Top 1-10' THEN '[2,100,1,1000]'\n" +
            "        WHEN user_ranking.top_name = 'Top 11-20' THEN '[2,80,1,900]'\n" +
            "        WHEN user_ranking.top_name = 'Top 31-100' THEN '[2,50]'\n" +
            "        WHEN user_ranking.top_name = 'Top 101 - …' THEN '[1,500]'\n" +
            "    END AS bonus_representation\n" +
            "FROM (\n" +
            "         SELECT\n" +
            "             CASE\n" +
            "                 WHEN r.point >= 1000000000 THEN 'Top 1-10'\n" +
            "                 WHEN r.point >= 500000000 THEN 'Top 11-20'\n" +
            "                 WHEN r.point >= 100000000 THEN 'Top 31-100'\n" +
            "                 ELSE 'Top 101 - …'\n" +
            "             END AS top_name,\n" +
            "             u.id AS user_id\n" +
            "         FROM\n" +
            "             user u\n" +
            "                 INNER JOIN\n" +
            "             user_rank r ON u.id = r.user_id\n" +
            "         ORDER BY\n" +
            "             r.point DESC\n" +
            "     ) AS user_ranking;", nativeQuery = true)
    List<Object[]> getPlayerRankings();



}
