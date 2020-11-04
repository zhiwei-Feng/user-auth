package ase.controller;

import ase.config.RemoteServiceConfig;
import ase.domain.Article;
import ase.domain.Author;
import ase.domain.Meeting;
import ase.domain.PCMemberRelation;
import ase.utility.contract.PCmemberRelationStatus;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        System.out.println("check login begin, id:" + id);
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

    @GetMapping("/demo/pcmemberrelation")
    public ResponseEntity<?> findPcmemberRelationByPcmemberIdAndStatus(long id, String status) {
        if (id != 1) {
            return ResponseEntity.noContent().build();
        }

        PCMemberRelation instance = new PCMemberRelation();
        instance.setId(1L);
        instance.setMeetingId(1L);
        instance.setPcmemberId(id);
        instance.setStatus(status);
        Set<String> topics = new HashSet<>();
        topics.add("machine learning");
        topics.add("deep learning");
        instance.setTopic(topics);

        PCMemberRelation instance1 = new PCMemberRelation();
        instance1.setId(2L);
        instance1.setMeetingId(2L);
        instance1.setPcmemberId(id);
        instance1.setStatus(status);
        Set<String> topics1 = new HashSet<>();
        topics1.add("machine learning");
        topics1.add("deep learning");
        instance1.setTopic(topics1);

        List<PCMemberRelation> resp = new ArrayList<>();
        resp.add(instance);
        resp.add(instance1);

        return ResponseEntity.ok(resp);
    }

    @GetMapping("/demo/meeting/id")
    public ResponseEntity<?> findMeetingById(long id) {
        if (id != 1 && id != 2) {
            return ResponseEntity.noContent().build();
        }

        Meeting meeting = new Meeting();
        meeting.setId(id);
        meeting.setMeetingName("test meeting" + id);

        return ResponseEntity.ok(meeting);
    }
}
