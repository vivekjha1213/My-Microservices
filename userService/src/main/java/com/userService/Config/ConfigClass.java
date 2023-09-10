package com.userService.Config;


import com.userService.Config.Interceptors.RestTempleteInterceptors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMessage;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.web.client.RestTemplate;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.ws.rs.HeaderParam;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.*;

@Configuration
public class ConfigClass {

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    @Autowired
    private OAuth2AuthorizedClientRepository auth2AuthorizedClientRepository;

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        RestTempleteInterceptors restTempleteInterceptors=
                new RestTempleteInterceptors(manager(clientRegistrationRepository,
                                                       auth2AuthorizedClientRepository));
        List<ClientHttpRequestInterceptor> interceptors=new ArrayList<>();
        interceptors.add(restTempleteInterceptors);

        RestTemplate restTemplate=new RestTemplate();
        restTemplate.setInterceptors(interceptors);

        return restTemplate;
    }

    @Bean
    public HttpHeaders httpHeaders(){

        return new HttpHeaders();
    }


    //declare the bean of OAuth2AuthorizedClient manager
    @Bean
    public OAuth2AuthorizedClientManager manager(
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository auth2AuthorizedClientRepository
    ) {
        OAuth2AuthorizedClientProvider provider = OAuth2AuthorizedClientProviderBuilder.builder().clientCredentials().build();
        DefaultOAuth2AuthorizedClientManager defaultOAuth2AuthorizedClientManager = new DefaultOAuth2AuthorizedClientManager(clientRegistrationRepository, auth2AuthorizedClientRepository);
        defaultOAuth2AuthorizedClientManager.setAuthorizedClientProvider(provider);
        return defaultOAuth2AuthorizedClientManager;

    }

    //For Looger
    @Bean
    public Logger logger(){
        return LoggerFactory.getLogger("Logger");
    }


}
