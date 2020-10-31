package ase.controller;

import ase.config.RemoteServiceConfig;
import ase.domain.Article;
import ase.domain.Author;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.List;

@RestController
public class DemoController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RemoteServiceConfig remote;


    /*
    模拟findArticleById 接口
     */
    @GetMapping("/demo/article")
    public ResponseEntity<Article> findArticleById(Long id, @RequestHeader("authorization") String token) {
        //通过RemoteServiceConfig注入user-auth服务的ip和port
        System.out.println("check login begin");
        String checkApi = remote.getCheck();
        //构造请求头，加入token
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("authorization", token);
        HttpEntity<String> entity = new HttpEntity<>(null, httpHeaders);
        //发送check请求到user-auth服务中
        ResponseEntity<String> resp = restTemplate.exchange(checkApi, HttpMethod.GET, entity, String.class);
        System.out.println("check login end");
        //请求结果处理
        if (resp.getStatusCode() == HttpStatus.OK) {
            Article fakeArticle = new Article(id);
            fakeArticle.setTopic(new HashSet<>());
            fakeArticle.setAuthors(new HashSet<>());
            return ResponseEntity.ok(fakeArticle);
        } else
            return ResponseEntity.badRequest().build();
    }
}
