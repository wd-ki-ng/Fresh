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
                .requestMatchers("/css/**", "/js/**", "/plugins/**").permitAll() // 정적 리소스 허용
                .requestMatchers("/", "/index", "/login").permitAll()           // 공개 경로 허용
                //.requestMatchers("/admin/**").hasAnyRole("ADMIN")              // 관리자 전용
                //.requestMatchers("/mypage/**").hasAnyRole("ADMIN", "USER")     // 사용자 전용
                .anyRequest().authenticated()                                  // 그 외 인증 필요
            );
        
        // 세션 관리 통합
        http.sessionManagement((auth) -> auth
            .maximumSessions(1).maxSessionsPreventsLogin(false)           // 기존 세션 종료 허용
            .and()
            .sessionFixation().changeSessionId()                          // 세션 고정 보호
        );
        
        // 로그인 설정
        http.formLogin((auth) -> auth
            .loginPage("/login")
            .loginProcessingUrl("/login")
            .defaultSuccessUrl("/")
            .permitAll()
        );
        
        
        // 로그아웃 설정
        http.logout((auth) -> auth
            .logoutUrl("/logout")
            .logoutSuccessUrl("/")
            .invalidateHttpSession(true)
        );        
         //http.csrf((auth) -> auth.disable()); // 개발 중에는 잠시 꺼두기
        
        return http.build();
    }
}
