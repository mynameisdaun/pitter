package com.pitter.controller.api.token;

import com.pitter.common.utils.JwtUtils;
import com.pitter.controller.dto.KakaoSignInResponse;
import com.pitter.controller.dto.KakaoUserInfoResponse;
import com.pitter.domain.entity.member.Member;
import com.pitter.service.KakaoTokenService;
import com.pitter.service.MemberService;
import com.pitter.service.RefreshTokenService;
import com.pitter.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static com.pitter.common.utils.DateUtils.now;

@Controller
@RequiredArgsConstructor
public class SocialSignInController {
    private final static Logger logger = LoggerFactory.getLogger(SocialSignInController.class);
    //private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;
    private final TokenService tokenService;
    private final KakaoTokenService kakaoTokenService;
    private final MemberService memberService;

    @GetMapping("/sign_in/kakao")
    public String kakaoSignIn(HttpServletRequest request, HttpServletRequest response, @RequestParam("code") String authorization_code) throws UnsupportedEncodingException {
        KakaoSignInResponse kakaoAccessToken = kakaoTokenService.getAccessToken(authorization_code);
        KakaoUserInfoResponse kakaoUserInfoResponse = kakaoTokenService.getKakaoUserInfo(kakaoAccessToken.getAccess_token());

        Member findMember = memberService.findByEmail(kakaoUserInfoResponse.getEmail())
                .orElse(memberService.join(kakaoUserInfoResponse.toMember()));

        String jwt = jwtUtils.jwtTokenBuilder(findMember.getEmail(), now());





        String _nickName = "&nickname="+kakaoUserInfoResponse.getNickname();
        String _email = "&email="+kakaoUserInfoResponse.getEmail();
        String _profileImageUrl = "&profileImageUrl="+kakaoUserInfoResponse.getProfile_image_url();
        String url = "accessToken=accesstoken&refreshToken=refreshtoken"+ _nickName + _email + _profileImageUrl;
        String encoded = URLEncoder.encode(url,"UTF-8");
        return "redirect:webauthcallback://success?"+encoded;
    }
}
