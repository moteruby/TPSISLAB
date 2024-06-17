package com.example.ERT.services;

import com.example.ERT.repositories.ResultRepository;
import com.example.ERT.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.*;
import org.springframework.beans.factory.annotation.Autowired;

@Service
@AllArgsConstructor
public class StatisticsService {

    private final UserRepository userRepository;
    private final ResultRepository resultRepository;

    public long getUserCount() {
        return userRepository.countUsers();
    }

    public long getResultCount() {
        return resultRepository.countResults();
    }
}

