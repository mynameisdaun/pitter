package com.pitter.config.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pitter.domain.entity.Member;
import com.pitter.domain.entity.Role;
import com.pitter.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final ObjectMapper objectMapper;
    private final TokenService tokenService;
    private final MemberService memberService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        //OAuthAttributes oAuthAttributes = (OAuthAttributes) oAuth2User.getAttributes();
        Map<String, Object> oAuthAttributes = oAuth2User.getAttributes();
        Member member;

//        try {
//            member = memberService.findByEmail((String) oAuthAttributes.get("email"));
//        } catch (Exception e) {
//            member = memberService.join(oAuthAttributes.toMember());
//        }

        Token token = tokenService.generateToken((String) oAuthAttributes.get("email"), Role.USER);
        log.debug("{}", token);

        writeTokenResponse(response, token);
    }

    private void writeTokenResponse(HttpServletResponse response, Token token) throws IOException {
        response.setContentType("text/html;charset=UTF-8");

        response.addHeader("Authorization", token.getToken());
        response.addHeader("Refresh", token.getRefreshToken());
        response.setContentType("application/json;charset=UTF-8");

        PrintWriter writer = response.getWriter();
        writer.println(objectMapper.writeValueAsString(token));
        writer.flush();
    }
}
