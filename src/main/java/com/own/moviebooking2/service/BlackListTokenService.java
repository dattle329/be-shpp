package com.own.moviebooking2.service;

import com.own.moviebooking2.entity.BlackListToken;
import com.own.moviebooking2.repository.BlackListTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlackListTokenService {
    private final BlackListTokenRepository blackListTokenRepository;

    public void addToBlackList(String token){
        blackListTokenRepository.save(new BlackListToken(token));
    }

    public boolean isBlackListed(String token){
        return blackListTokenRepository.existsByToken(token);
    }
}
