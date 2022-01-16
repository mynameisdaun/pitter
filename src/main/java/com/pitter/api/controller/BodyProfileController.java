package com.pitter.api.controller;

import com.pitter.api.dto.BodyProfileRequest;
import com.pitter.api.dto.BodyProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class BodyProfileController {


    @GetMapping("/bodyProfile")
    public BodyProfileResponse getBodyProfile(@Valid @RequestParam BodyProfileRequest bodyProfileRequest) {
        return null;
    }
}
