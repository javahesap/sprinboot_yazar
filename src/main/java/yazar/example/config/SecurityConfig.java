package yazar.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import yazar.example.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // CSRF korumayı devre dışı bırakmak (isteğe bağlı)
            .authorizeRequests()
                .requestMatchers("/login", "/register").permitAll() // Login ve register sayfalarına herkese izin ver
                .anyRequest().authenticated() // Diğer tüm sayfalara kimlik doğrulama gerektir
            .and()
            .formLogin()
                .loginPage("/login") // Login sayfası
                .permitAll()
            .and()
            .logout()
                .permitAll();
        
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Şifreleri güvenli bir şekilde saklamak için BCrypt kullanıyoruz
    }
}
