package com.pitter.controller.api.token;

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
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
@RequiredArgsConstructor
public class SocialSignInController {

    private final static Logger logger = LoggerFactory.getLogger(SocialSignInController.class);
    private final KakaoTokenService kakaoTokenService;

    @GetMapping("/sign_in/kakao")
    public String kakaoSignIn(HttpServletRequest request, HttpServletRequest response, @RequestParam("code") String authorization_code) throws UnsupportedEncodingException {
        KakaoSignInResponse kakaoAccessToken = kakaoTokenService.getAccessToken(authorization_code);
        KakaoUserInfoResponse kakaoUserInfoResponse = kakaoTokenService.getKakaoUserInfo(kakaoAccessToken.getAccess_token());
        String nickName = "&nickname="+kakaoUserInfoResponse.getNickname();
        String email = "&email="+kakaoUserInfoResponse.getEmail();
        String profileImageUrl = "&profileImageUrl="+kakaoUserInfoResponse.getProfile_image_url();
        String url = "accessToken=accesstoken&refreshToken=refreshtoken"+nickName + email + profileImageUrl;
        String encoded = URLEncoder.encode(url,"UTF-8");
        return "redirect:webauthcallback://success?"+encoded;
    }
}
