package com.pitter.controller.api.token;

import com.pitter.common.utils.JwtUtils;
import com.pitter.controller.dto.KakaoToken;
import com.pitter.controller.dto.KakaoUserInfo;
import com.pitter.domain.entity.member.Member;
import com.pitter.domain.entity.token.RefreshToken;
import com.pitter.service.KakaoTokenService;
import com.pitter.service.MemberService;
import com.pitter.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static com.pitter.common.utils.DateUtils.now;

@RestController
@RequestMapping("/sign_in")
@RequiredArgsConstructor
public class SocialSignInController {
    private final static Logger logger = LoggerFactory.getLogger(SocialSignInController.class);
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;
    private final KakaoTokenService kakaoTokenService;
    private final MemberService memberService;

    @GetMapping("/kakao")
    public ResponseEntity<Void> kakaoSignIn(HttpServletRequest request, HttpServletRequest response,
                                            @RequestParam("code") String authorization_code) throws UnsupportedEncodingException {
        KakaoToken kakaoToken = kakaoTokenService.getAccessToken(authorization_code);
        KakaoUserInfo kakaoUserInfo = kakaoTokenService.getKakaoUserInfo(kakaoToken.getAccess_token());

        Member findMember = memberService.findByEmail(kakaoUserInfo.getEmail())
                .orElseGet( ()-> memberService.join(kakaoUserInfo.toMember()));

        findMember.setProfileImageUrl(kakaoUserInfo.getProfileImageUrl());

        String accessToken = jwtUtils.jwtTokenBuilder(findMember.getEmail(), now());
        RefreshToken refreshToken = refreshTokenService.createToken(findMember);

        String location = setLocation(findMember, accessToken, refreshToken);
        logger.info("{}:{}","location",location);
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, location)
                .build();
    }

    private String setLocation(Member findMember, String accessToken, RefreshToken refreshToken) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        String location =
                sb.append("id=")
                        .append(findMember.getId())
                .append("&accessToken=")//
                    .append(accessToken)
                .append("&refreshToken=")
                    .append(refreshToken.getToken())
                .append("&nickName=")
                    .append(findMember.getNickName().getNickName())
                .append("&email=")
                    .append(findMember.getEmail().getEmail())
                .append("&profileImageUrl=")//
                    .append(findMember.getProfileImageUrl()).toString();
        String encodedUrl = URLEncoder.encode(location, "UTF-8");
        return "webauthcallback://success?"+encodedUrl;
    }
}
