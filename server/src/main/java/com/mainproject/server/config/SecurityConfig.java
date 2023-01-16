package com.mainproject.server.config;

import com.mainproject.server.auth.filter.JwtAuthenticationFilter;
import com.mainproject.server.auth.filter.JwtVerificationFilter;
import com.mainproject.server.auth.handler.UserAccessDeniedHandler;
import com.mainproject.server.auth.handler.UserAuthenticationEntryPoint;
import com.mainproject.server.auth.handler.UserAuthenticationFailureHandler;
import com.mainproject.server.auth.handler.UserAuthenticationSuccessHandler;
import com.mainproject.server.auth.oauth2.OAuth2UserSuccessHandler;
import com.mainproject.server.auth.oauth2.provider.CustomOAuth2Provider;
import com.mainproject.server.auth.oauth2.service.CustomOAuth2UserService;
import com.mainproject.server.auth.redis.service.RefreshService;
import com.mainproject.server.auth.token.JwtAuthorityUtils;
import com.mainproject.server.auth.token.JwtTokenizer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenizer jwtTokenizer;

    private final JwtAuthorityUtils authorityUtils;

    private final RefreshService refreshService;

    private final CustomOAuth2UserService oAuth2UserService;

    private final OAuth2UserSuccessHandler oAuth2UserSuccessHandler;

    @Value("${GOOGLE_OAUTH2_MAINPROJECT_SECRETKEY}")
    private String clientSecret;

    @Value("${KAKAO_OAUTH2_MAINPROJECT_SECRETKEY}")
    private String kakaoClientSecret;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.headers().frameOptions().sameOrigin()
                .and()
                .csrf().disable()
                .cors(Customizer.withDefaults())
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .exceptionHandling()
                .authenticationEntryPoint(new UserAuthenticationEntryPoint())
                .accessDeniedHandler(new UserAccessDeniedHandler())
                .and()
                .apply(new CustomFilterConfig())
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/auth/logout")
                .and().authorizeRequests(
                        auth -> auth
                                .mvcMatchers(HttpMethod.POST,"/login").permitAll()
                                .mvcMatchers(HttpMethod.POST,"/users").permitAll()
                                .mvcMatchers(HttpMethod.GET,"/users/tutors").permitAll()
                                .mvcMatchers(HttpMethod.GET,"/users/tutors/**").permitAll()
                                .mvcMatchers(HttpMethod.GET,"/users/tutees").permitAll()
                                .mvcMatchers(HttpMethod.GET,"/users/tutees/**").permitAll()
                                .mvcMatchers(HttpMethod.GET,"/profiles/details/**").permitAll()
                                .mvcMatchers(HttpMethod.POST,"/auth/reissue-token/**").permitAll()
                                .anyRequest().hasAnyRole("USER")
//                                .anyRequest().permitAll()
                )
                .oauth2Login(oauth2 -> oauth2
                        .successHandler(oAuth2UserSuccessHandler)
                        .userInfoEndpoint().userService(oAuth2UserService)
                );


        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(
                Arrays.asList("http://localhost:3000",
                        "http://localhost:8080","*"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET","POST", "PATCH", "DELETE", "OPTIONS"));
        corsConfiguration.setMaxAge(493772L);
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addExposedHeader("Authorization");
        corsConfiguration.addExposedHeader("userId");
        corsConfiguration.addExposedHeader("userStatus");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    public class CustomFilterConfig extends AbstractHttpConfigurer<CustomFilterConfig, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager =
                    builder.getSharedObject(AuthenticationManager.class);
            JwtAuthenticationFilter jwtAuthenticationFilter =
                    new JwtAuthenticationFilter(
                            authenticationManager,
                            jwtTokenizer,
                            refreshService);

            jwtAuthenticationFilter.setAuthenticationSuccessHandler(new UserAuthenticationSuccessHandler());
            jwtAuthenticationFilter.setAuthenticationFailureHandler(new UserAuthenticationFailureHandler());
            jwtAuthenticationFilter.setRequiresAuthenticationRequestMatcher(
                    new AntPathRequestMatcher("/auth/login","POST"));

            JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtTokenizer, authorityUtils);
            builder.addFilter(jwtAuthenticationFilter)
                    .addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class);

        }
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository(
            OAuth2ClientProperties oAuth2ClientProperties
    ) {
        List<ClientRegistration> registrations = oAuth2ClientProperties
                .getRegistration().keySet().stream()
                .map(client -> clientRegistration(oAuth2ClientProperties, client))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return new InMemoryClientRegistrationRepository(registrations);
    }

    private ClientRegistration clientRegistration(
            OAuth2ClientProperties clientProperties,
            String client
    ) {
        if("google".equals(client)) {
            OAuth2ClientProperties.Registration google = clientProperties.getRegistration().get("google");
            return CustomOAuth2Provider.GOOGLE
                    .getBuilder(client)
                    .clientId(google.getClientId())
                    .clientSecret(clientSecret)
                    .build();
        }
        if("kakao".equals(client)) {
            OAuth2ClientProperties.Registration kakao = clientProperties.getRegistration().get("kakao");
            return CustomOAuth2Provider.KAKAO
                    .getBuilder(client)
                    .clientId(kakao.getClientId())
                    .clientSecret(kakaoClientSecret)
                    .build();
        }
        return null;
    }
}
