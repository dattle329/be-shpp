package com.own.moviebooking2.repository;

import com.own.moviebooking2.entity.BlackListToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlackListTokenRepository extends JpaRepository<BlackListToken, Long> {
    boolean existsByToken(String token);
}
