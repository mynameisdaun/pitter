package com.pitter.config.auth;

import com.pitter.domain.entity.member.Member;
import com.pitter.domain.entity.token.InternalApiRequestToken;
import com.pitter.service.MemberService;
import com.pitter.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final MemberService memberService;
    private final TokenService tokenService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = ((HttpServletRequest)request).getHeader("Authorization");
//        String token =
//        tokenService.validate(new InternalApiRequestToken());
/*        if(token != null && tokenService.verifyToken(token)) {
            String email = tokenService.getEmail(token);
            Member member = memberService.findByEmail(email);

            Authxentication authentication = getAuthentication(member);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }*/
        chain.doFilter(request, response);
    }

    //TODO: 확실히 이상하다
    public Authentication getAuthentication(Member member) {
        return new UsernamePasswordAuthenticationToken(member, "",
                Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
