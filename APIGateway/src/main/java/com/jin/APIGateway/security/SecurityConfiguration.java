package com.jin.APIGateway.security;

//import com.okta.spring.boot.oauth.Okta;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.config.EnableWebFlux;

//@EnableWebFlux
@Configuration
public class SecurityConfiguration {

//    @Bean
//    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http){
//        http.authorizeExchange()
//                .anyExchange()
//                .authenticated()
//                .and()
//                .oauth2ResourceServer()
//                .jwt();
////        Okta.configureResourceServer401ResponseBody(http);
//        return http.build();
//    }

}
