package com.pitter.config.auth;

import com.pitter.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final MemberService memberService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterAfter(new JwtAuthenticationFilter(memberService), LogoutFilter.class);

        http
            .httpBasic()
        .and()
            .csrf()
                .disable()
            .headers()
                .frameOptions()
                    .disable()
        .and()
            .authorizeRequests()
                .antMatchers("/", "/css/**", "/images/**","/js/**","/h2-console/**")
                    .permitAll()
                .antMatchers("/members")
                    .permitAll()
                .antMatchers("/token/**")
                    .permitAll()
                .anyRequest()
                    .authenticated()
        .and()
            .logout()
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID")
        .and()
            .oauth2Login()
                .successHandler(oAuth2SuccessHandler)
                .failureUrl("/loginFailure")
                .userInfoEndpoint()
                .userService(customOAuth2UserService);
    }
}
