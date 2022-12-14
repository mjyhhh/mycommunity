package com.example.community.provider;

import com.alibaba.fastjson.JSON;
import com.example.community.dto.AccessTokenDTO;
import com.example.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;
import java.io.IOException;



@Component
public class GithubProvider {
    //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
    public String getAccessToken(AccessTokenDTO a) {
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

     okhttp3.RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(a));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {

            String s = response.body().string();
            String[] split = s.split("&");
            String[] tokens = split[0].split("=");
//            System.out.println(tokens[1]);
            return tokens[1];

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://api.github.com/user")
                    .header("Authorization","token "+accessToken)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                String string = response.body().string();
                GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
                return githubUser;
            } catch (IOException e) {
                e.printStackTrace();
            }
         return null;
    }

}
