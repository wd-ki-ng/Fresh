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
		return new BCryptPasswordEncoder();						//パスワードのハッシュ化のBeanインジェクション
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		
        http.authorizeHttpRequests((auth) -> auth
                .requestMatchers("/css/**", "/plugins/**").permitAll()
                .requestMatchers("/", "/main", "/login", "/join","/board", "/checkUserName", "/verify-email",
                		"/send-email", "/findid", "/findpassword", "/findPw", "/search", "/checkid").permitAll()		//全ユーザーの接近を許容
                .requestMatchers("/admin/**").hasAnyRole("ADMIN")				// 管理者のみ許容
                .requestMatchers("/mypage/**").hasAnyRole("ADMIN","USER")				// ユーザーのみ許容
                .anyRequest().authenticated()									// その他、認証必要
            );

        
        // セッションの重複ログインの許容可否
        http .sessionManagement((auth) -> auth
                .maximumSessions(1) //多重ログインの許容の個数
                .maxSessionsPreventsLogin(true));
        // true : 超過する場合、新しいログインを遮断する
        // false : 超過する場合、既存のセッションを削除する
        
        //カスタムログイン画面の設定
        http.formLogin((auth) -> auth
        		.loginPage("/login")					 // ログイン画面に遷移
        		.loginProcessingUrl("/login")			// ログインを成功する場合、当URLにバリュー伝達
        		.defaultSuccessUrl("/",true)					// ログインに成功した場合、遷移される画面
        		.failureUrl("/login?error=1")			 // ログインに失敗した場合、遷移される画面
        		.permitAll()
        		);
        
        // セッション固定保護Level 10
        http.sessionManagement((auth) -> auth
                .sessionFixation()
                .changeSessionId());
        
        // ログアウトの遷移設定
        http.logout(auth -> auth
        		.logoutUrl("/logout")
        		.logoutSuccessUrl("/")
        		.invalidateHttpSession(true));
        
        //http.csrf((auth) -> auth.disable()); //開発中には使用禁止
        
        return http.build();		
    }
}

