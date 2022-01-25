package com.pitter.controller.api.common;

import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CommonApiController {

    @GetMapping("/healthCheck")
    public Map<String, Object> healthCheck() {
        Map<String, Object> response = Maps.newHashMap();
        response.put("status","200");
        response.put("message","ok");
        response.put("timestamp",String.valueOf(LocalDateTime.now()));
        return response;
    }
}
