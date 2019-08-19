package com.ghifar.demoku;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghifar.demoku.domain.AccountCredentials;
import com.ghifar.demoku.service.AuthenticationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;


/*TODO should be learn more about this class
*
* */
public class LoginFilter extends AbstractAuthenticationProcessingFilter {

    // idk whats this... should be investigate later about this constructor body // ini dipanggil dari security config pass configure .addFilterBefore()
    // this constructor run when build started/ compile time.
    public LoginFilter(String url, AuthenticationManager authenticationManager){
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authenticationManager);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        //this will run when /login fired at securityConfig. .addFilterBefore
        AccountCredentials credentials= new ObjectMapper().readValue(httpServletRequest.getInputStream(),AccountCredentials.class);


        //authenticate the login user
        return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(
                credentials.getUsername(), credentials.getPassword(), Collections.emptyList()
        ));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        //respond to client with token header
        AuthenticationService.addToken(response,authResult.getName());
    }
}
