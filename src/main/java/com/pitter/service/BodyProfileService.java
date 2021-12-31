package com.pitter.service;

import com.pitter.domain.repository.BodyProfileHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BodyProfileService {

    private final BodyProfileHistoryRepository bodyProfileHistoryRepository;
}
