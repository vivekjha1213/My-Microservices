package com.userService.Config.Interceptors;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class FeignClientInterceptors implements RequestInterceptor {

    @Autowired
    OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager;

    @Override
    public void apply(RequestTemplate requestTemplate) {

        String token=oAuth2AuthorizedClientManager.authorize(OAuth2AuthorizeRequest
                                                .withClientRegistrationId("my-internal-client")
                                                 .principal("internal")
                                                 .build()).getAccessToken().getTokenValue();

        System.out.println("Token is "+token);
        requestTemplate.header("Authorization","Bearer " + token);

    }
}
