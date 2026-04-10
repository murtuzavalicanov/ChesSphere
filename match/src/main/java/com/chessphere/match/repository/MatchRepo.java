package com.chessphere.match.repository;

import com.chessphere.match.entity.MatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MatchRepo extends JpaRepository<MatchEntity, UUID> {

    @Query("SELECT m FROM MatchEntity m WHERE m.whitePlayerId = :userId OR m.blackPlayerId = :userId")
    List<MatchEntity> findAllMatchByUserId(@Param("userId") UUID userId);
}
