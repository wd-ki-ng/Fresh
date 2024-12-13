package com.fresh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.RequestMatchers;

import jakarta.servlet.http.HttpServletRequest;

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
                .requestMatchers("/", "/main", "/login", "/join","/board", "/checkid", "/checkUserName", "/verify-email", "/send-email", "/findid", "/findpassword", "/findPw", "/search").permitAll()           // 공개 경로 허용
                .requestMatchers("/admin/**").hasAnyRole("ADMIN")              // 管理者、専用
                .requestMatchers("/mypage/**").hasAnyRole("USER")		       // ユーザー専用
                .anyRequest().authenticated()                                  // その他、認証必要
            );

        // ログインできなくてエラーページが発生する場合、ログインページへ移動
        http.formLogin((auth) -> auth
        		.loginPage("/login")
        		.loginProcessingUrl("/login")// ログインを成功する場合、当URLにバリュー伝達     
        		.failureUrl("/login?error=1")
        		.defaultSuccessUrl("/",true)   		
        		.permitAll()
        		
        		);
        
        
        // セッションの重複ログインの許容の可否
        http .sessionManagement((auth) -> auth
                .maximumSessions(1) //多重ログインの許容の個数
                .maxSessionsPreventsLogin(true));
        // true : 超過する場合、新しいログインを遮断する
        // false : 超過する場合、既存のセッションを削除する
        

        
        // セッション固定保護、10
        http.sessionManagement((auth) -> auth
                .sessionFixation().changeSessionId());
        
        // ログアウト
        http.logout((auth) -> auth
        		.logoutUrl("/logout")
        		.logoutSuccessUrl("/")
        		.invalidateHttpSession(true));
       
        //http.csrf((auth) -> auth.disable()); //開発中には使用禁止
        
        return http.build();
    }
	
}
