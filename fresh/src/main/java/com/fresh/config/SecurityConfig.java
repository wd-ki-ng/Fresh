package com.fresh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http.authorizeHttpRequests((auth) -> auth
                .requestMatchers("/img/**", "/css/**", "/js/**", "/scss/**", "/plugins/**").permitAll()
                .requestMatchers("/", "/main", "/login", "/join","/board", "/checkid", "/checkUserName").permitAll()           // 공개 경로 허용
                .requestMatchers("/admin/**").hasAnyRole("ADMIN")              // 관리자 전용
                .requestMatchers("/mypage/**").hasAnyRole("ADMIN", "USER")     // 사용자 전용
                .anyRequest().authenticated()                                  // 그 외 인증 필요
            );
        // 로그인이 안되어 오류 페이지 발생 시 로그인 페이지로 이동
        http.formLogin((auth) -> auth.loginPage("/login")
        		.loginProcessingUrl("/login")// 로그인 시 해당 URL로 값 전송
        		.failureUrl("/login?error=1")
        		.defaultSuccessUrl("/",true)
        		.permitAll());
        
        // 세션 중복 로그인 허용 여부
        http .sessionManagement((auth) -> auth
                .maximumSessions(1) // 다중 로그인 허용 개수
                .maxSessionsPreventsLogin(true));
        // true : 초과 시 새로운 로그인 차단
        // false : 초과 시 기존 세션 삭제
        

        
        // 세션 고정 보호, 10강
        http.sessionManagement((auth) -> auth
                .sessionFixation().changeSessionId());
        
        // 로그아웃 하기
        http.logout((auth) -> auth
        		.logoutUrl("/logout")
        		.logoutSuccessUrl("/")
        		.invalidateHttpSession(true));
       
        //http.csrf((auth) -> auth.disable()); // 개발 중에는 잠시 꺼두기
        
        return http.build();
    }
}

