package com.pitter.controller.api.controller;

import com.pitter.controller.dto.KakaoSignInResponse;
import com.pitter.controller.dto.KakaoUserInfoResponse;
import com.pitter.service.KakaoTokenService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final static Logger logger = LoggerFactory.getLogger(LoginController.class);
    private final KakaoTokenService kakaoTokenService;


    @GetMapping("/sign_in/kakao")
    public String sign_in_kakao (HttpServletRequest request, HttpServletRequest response, @RequestParam("code") String authorization_code) {
        KakaoSignInResponse kakaoAccessToken = kakaoTokenService.getAccessToken(authorization_code);
        KakaoUserInfoResponse kakaoUserInfoResponse = kakaoTokenService.getKakaoUserInfo(kakaoAccessToken.getAccess_token());
        String nickName = "&nickname="+kakaoUserInfoResponse.getNickname();
        String email = "&email="+kakaoUserInfoResponse.getEmail();
        String profileImageUrl = "&profileImageUrl="+kakaoUserInfoResponse.getProfile_image_url();
        return "redirect:webauthcallback://success?accessToken=accesstoken&refreshToken=refreshtoken"
                +nickName + email + profileImageUrl;
    }

}
