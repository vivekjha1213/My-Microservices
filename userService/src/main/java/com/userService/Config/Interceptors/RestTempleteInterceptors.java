package com.userService.Config.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;

import java.io.IOException;

@Configuration
public class RestTempleteInterceptors implements ClientHttpRequestInterceptor {


    @Autowired
    private OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager;

    public RestTempleteInterceptors(OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager){
        this.oAuth2AuthorizedClientManager=oAuth2AuthorizedClientManager;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        String token=oAuth2AuthorizedClientManager.authorize(OAuth2AuthorizeRequest
                .withClientRegistrationId("my-internal-client")
                .principal("internal")
                .build()).getAccessToken().getTokenValue();
        System.out.println("Token is "+token);
        request.getHeaders().add("Authorization","Bearer "+token);

        return execution.execute(request,body);
    }
}
