package com.mashup.poten.config;

import com.mashup.poten.common.jwt.JwtAuthenticationFilter;
import com.mashup.poten.common.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtProvider jwtProvider;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers("/user/login", "/user/sign-up" ,"/h2-console/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 요청이 들어온 URL들은 기본적으로 인증이 되어야한다.(인증 == 로그)
        http.authorizeRequests().anyRequest().authenticated();
        // Http Basic 을 비활성화 하겠다.
        http.httpBasic().disable();
        // csrf 비활성화
        http.csrf().disable();
        // 이 서비스는 세션이 없다.
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // 로그인 말고 JWT 로 사용자 인증하기
        http.addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);


    }
}
