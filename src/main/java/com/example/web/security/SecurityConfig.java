//package com.example.web.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
////    private CustomUserDetailsService userDetailsService;
////
////    @Autowired
////    public SecurityConfig(CustomUserDetailsService userDetailsService) {
////        this.userDetailsService = userDetailsService;
////    }
//
//    @Bean
//    public static PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
////    @Bean
////    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////        http
////                .authorizeRequests()
////                .anyRequest().permitAll();  // Разрешает все запросы без аутентификации
////
////        return http.build();
////    }
//
////    @Bean
////    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
////        http.csrf().disable()
////                .authorizeRequests()
////                .antMatchers("/login", "/register", "/", "/css/**", "/js/**", "/bookmark")
////                .permitAll()
//////                .antMatchers("/mc").hasAuthority("ADMIN")
////                .antMatchers("/products/new", "/products/{productId}", "/uploadAudio").hasAnyAuthority("Admin", "Artist")
////                .and()
////                .formLogin(form -> form
////                        .loginPage("/login")
////                        .defaultSuccessUrl("/")
////                        .loginProcessingUrl("/login")
////                        .failureUrl("/login?error=true")
////                        .permitAll()
////                ).logout(
////                        logout -> logout
////                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll()
////                );
////
////        return http.build();
////    }
//
////    public void configure(AuthenticationManagerBuilder builder) throws Exception {
////        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
////    }
//}
