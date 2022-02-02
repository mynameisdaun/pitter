package com.pitter.controller.api.token;

import com.pitter.common.utils.JwtUtils;
import com.pitter.controller.dto.KakaoSignInResponse;
import com.pitter.controller.dto.KakaoUserInfoResponse;
import com.pitter.domain.entity.member.Member;
import com.pitter.domain.entity.token.RefreshToken;
import com.pitter.service.KakaoTokenService;
import com.pitter.service.MemberService;
import com.pitter.service.RefreshTokenService;
import com.pitter.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static com.pitter.common.utils.DateUtils.now;

@RestController
@RequiredArgsConstructor
public class SocialSignInController {
    private final static Logger logger = LoggerFactory.getLogger(SocialSignInController.class);
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;
    private final KakaoTokenService kakaoTokenService;
    private final MemberService memberService;

    @GetMapping("/sign_in/kakao")
    public ResponseEntity<Void> kakaoSignIn(HttpServletRequest request, HttpServletRequest response,
                                            @RequestParam("code") String authorization_code) throws UnsupportedEncodingException {
        KakaoSignInResponse kakaoAccessToken = kakaoTokenService.getAccessToken(authorization_code);
        KakaoUserInfoResponse kakaoUserInfoResponse = kakaoTokenService.getKakaoUserInfo(kakaoAccessToken.getAccess_token());

        Member findMember = memberService.findByEmail(kakaoUserInfoResponse.getEmail())
                .orElse(memberService.join(kakaoUserInfoResponse.toMember()));
        String jwt = jwtUtils.jwtTokenBuilder(findMember.getEmail(), now());
        RefreshToken refreshToken = refreshTokenService.createToken(findMember);

        String location = setLocation(kakaoUserInfoResponse, jwt, refreshToken);
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, location)
                .build();
    }

    private String setLocation(KakaoUserInfoResponse kakaoUserInfoResponse, String jwt, RefreshToken refreshToken) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder("webauthcallback://success?");
        String location = sb.append("accessToken=")
                    .append(jwt)
                .append("&refreshToken=")
                    .append(refreshToken.getToken())
                .append("&nickName=")
                    .append(kakaoUserInfoResponse.getNickname())
                .append("&email=")
                    .append(kakaoUserInfoResponse.getEmail())
                .append("&profileImageUrl=")
                    .append(kakaoUserInfoResponse.getProfileImageUrl()).toString();
        return  URLEncoder.encode(location, "UTF-8");
    }
}
