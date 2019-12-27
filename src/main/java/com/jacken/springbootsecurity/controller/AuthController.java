package com.jacken.springbootsecurity.controller;

import com.jacken.springbootsecurity.config.WebSecurityConfig;
import com.jacken.springbootsecurity.entity.RestResult;
import com.jacken.springbootsecurity.utils.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @PostMapping("/login")
    public RestResult<String> login(@RequestParam String username,  @RequestParam String password, HttpServletResponse response) {
        // 通过用户名和密码创建一个 Authentication 认证对象，实现类为 UsernamePasswordAuthenticationToken
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        RestResult<String> rr = new RestResult<String>();
        // //通过 AuthenticationManager（默认实现为ProviderManager）的authenticate方法验证 Authentication 对象
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenUtils.createToken(authentication);
        //将Token写入到Http头部
        response.addHeader(WebSecurityConfig.AUTHORIZATION_HEADER, "jacken" + token);
        rr.setCode(200);
        rr.setData(token);
        return rr;
    }

}
