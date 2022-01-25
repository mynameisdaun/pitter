package com.pitter.controller.api;

import com.pitter.controller.dto.BodyProfileRequest;
import com.pitter.controller.dto.BodyProfileResponse;
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
