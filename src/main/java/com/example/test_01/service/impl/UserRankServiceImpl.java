package com.example.test_01.service.impl;

import com.example.test_01.repository.UserRankRepository;
import com.example.test_01.service.UserRankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRankServiceImpl implements UserRankService {
    @Autowired
    private UserRankRepository userRankRepository;

    @Override
    public List<Object[]> getPlayerRankings() {
        return userRankRepository.getPlayerRankings();
    }

}
