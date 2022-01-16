package com.pitter.api;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class Setting {
    @Value("${setting.myname}")
    String myname;

    @Value("${spring.datasource.url}")
    String datasource;

    @GetMapping("/env")
    public String setting() {
        System.out.println("====================[DEBUG]====================");
        String profile = System.getProperty("spring.profiles.active");
        System.out.println(profile);
        System.out.println(myname);
        System.out.println("dataSource : "+ datasource );
        return myname;
    }


}
