package com.chocoannie.community.community.controller;

import com.chocoannie.community.community.controller.provider.GithubProvider;
import com.chocoannie.community.community.dto.AccessTokenDTO;
import com.chocoannie.community.community.dto.GithubUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OAuthController {
    @Autowired
    private GithubProvider githubProvider;
    @GetMapping("/callback")
    public String callback(@RequestParam("code") String code, @RequestParam(name = "state") String state){
        AccessTokenDTO atd = new AccessTokenDTO();
        atd.setCode(code);
        atd.setState(state);
        atd.setRedirect_url("http://localhost:8887/callback");
        atd.setClient_id("bb7cfec069d423bcb31e");
        atd.setClient_secret("cce56ffd1c16e5d55e3731babe4d9553e2426eab");
        String access_token = githubProvider.post(atd);
        GithubUser user = githubProvider.getUser(access_token);
        System.out.println(user);
        return "index";
    }
}
