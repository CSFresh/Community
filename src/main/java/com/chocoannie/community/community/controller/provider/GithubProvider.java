package com.chocoannie.community.community.controller.provider;

import ch.qos.logback.classic.spi.TurboFilterList;
import com.alibaba.fastjson.JSON;
import com.chocoannie.community.community.dto.AccessTokenDTO;
import com.chocoannie.community.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Component
public class GithubProvider {
    public String post(AccessTokenDTO atd){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(atd));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String res = response.body().string();
            String[] split = res.split("&");
            String[] tokens = split[0].split("=");
            return tokens[1];
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public GithubUser getUser(String accesstoken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user")
                .header("Authorization","token "+accesstoken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
