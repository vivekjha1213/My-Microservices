package com.apiGateway.Controller;

import com.apiGateway.Entity.LoginDetails;
import com.apiGateway.Entity.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    Logger logger= LoggerFactory.getLogger(AuthController.class);

    @GetMapping("/login")
    public ResponseEntity<Response> login(
            @RegisteredOAuth2AuthorizedClient("okta")  OAuth2AuthorizedClient client,
            @AuthenticationPrincipal OidcUser user
            ){
        try{
            LoginDetails loginDetails=new LoginDetails();
            loginDetails.setUserId(user.getEmail());
            loginDetails.setAccessToken(client.getAccessToken().getTokenValue());
            loginDetails.setRefreshToken(client.getRefreshToken().getTokenValue());
            loginDetails.setExpireAt(client.getAccessToken().getExpiresAt().getEpochSecond());

            List<String> authorities=user.getAuthorities().stream().map(
                    grantedAuthority -> grantedAuthority.getAuthority()
            ).collect(Collectors.toList());
            loginDetails.setAuthorities(authorities);

            return new ResponseEntity<>(new Response("Login is Successful",loginDetails),HttpStatus.OK);
        }catch (Exception exception){
            logger.info("Could Not Login");
            return new ResponseEntity<>(new Response("Could not Login",null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
