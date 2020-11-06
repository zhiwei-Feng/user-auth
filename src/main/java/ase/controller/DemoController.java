package ase.controller;

import ase.config.RemoteServiceConfig;
import ase.domain.Article;
import ase.domain.Author;
import ase.domain.Meeting;
import ase.domain.PCMemberRelation;
import ase.utility.contract.PCmemberRelationStatus;
import ase.utility.response.ResponseGenerator;
import ase.utility.response.ResponseWrapper;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
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
        Set<String> topics1 = new HashSet<>();
        topics1.add("machine learning");
        topics1.add("deep learning");
        meeting.setTopic(topics1);

        return ResponseEntity.ok(meeting);
    }

    @GetMapping("/demo/meeting/meetingName")
    public ResponseEntity<?> findMeetingByMeetName(String meetingName) {
        Meeting meeting = new Meeting();
        meeting.setId(1L);
        meeting.setMeetingName(meetingName);
        meeting.setAcronym(meetingName);

        return ResponseEntity.ok(meeting);
    }

    @GetMapping("/demo/pcmemberrelation/pcmemberAndMeeting")
    public ResponseEntity<?> findPcmemberRelationByPcmemberIdAndMeetingId(Long pcmemberId, Long meetingId) {
        System.out.println("findPcmemberRelationByPcmemberIdAndMeetingId: " + pcmemberId + " " + meetingId);
        List<PCMemberRelation> prList = new ArrayList<>();
        PCMemberRelation pc = new PCMemberRelation();
        pc.setId(1L);
        pc.setPcmemberId(pcmemberId);
        pc.setMeetingId(meetingId);
        pc.setStatus(PCmemberRelationStatus.undealed);
        pc.setTopic(new HashSet<>());
        prList.add(pc);
        return ResponseEntity.ok(prList);
    }

    @PutMapping("/demo/pcmemberrelation")
    public ResponseEntity<?> updatePcmemberRelation(@RequestBody PCMemberRelation relation) {
        PCMemberRelation pcMemberRelation = new PCMemberRelation();
        pcMemberRelation.setId(relation.getId());
        pcMemberRelation.setPcmemberId(relation.getPcmemberId());
        pcMemberRelation.setMeetingId(relation.getMeetingId());
        pcMemberRelation.setTopic(new HashSet<>());
        pcMemberRelation.setStatus(PCmemberRelationStatus.undealed);

        if (relation.getStatus() != null) {
            pcMemberRelation.setStatus(relation.getStatus());
        }
        if (relation.getTopic() != null) {
            pcMemberRelation.setTopic(relation.getTopic());
        }
        System.out.println("updatePcmemberRelation: " + pcMemberRelation);
        return ResponseEntity.ok(pcMemberRelation);
    }

    @GetMapping("/demo/pcmemberrelation/not")
    public ResponseEntity<?> findPcmemberRelationByPcmemberIdAndStatusNot(long id, String status) {
        if (id != 1) {
            return ResponseEntity.noContent().build();
        }

        PCMemberRelation instance = new PCMemberRelation();
        instance.setId(1L);
        instance.setMeetingId(1L);
        instance.setPcmemberId(id);
        instance.setStatus("other status");
        Set<String> topics = new HashSet<>();
        topics.add("machine learning");
        topics.add("deep learning");
        instance.setTopic(topics);


        List<PCMemberRelation> resp = new ArrayList<>();
        resp.add(instance);

        return ResponseEntity.ok(resp);
    }

    @GetMapping("/demo/meeting/chair")
    public ResponseEntity<?> findMeetingByChairName(String chairName) {
        if (chairName == null) {
            return ResponseEntity.badRequest().build();
        }

        Meeting meeting1 = new Meeting();
        meeting1.setId(1L);
        meeting1.setMeetingName("test1");
        meeting1.setChairName(chairName);

        Meeting meeting2 = new Meeting();
        meeting2.setId(2L);
        meeting2.setMeetingName("test2");
        meeting2.setChairName(chairName);

        List<Meeting> meetings = new ArrayList<>();
        meetings.add(meeting1);
        meetings.add(meeting2);

        return ResponseEntity.ok(meetings);
    }

    @GetMapping("/demo/article/contributor")
    public ResponseEntity<?> findArticleByContributorName(String name) {
        if (name == null) {
            return ResponseEntity.badRequest().build();
        }

        List<Article> articles = new ArrayList<>();

        Article a1 = new Article();
        a1.setId(1L);
        a1.setContributorName(name);
        a1.setTitle("article1");
        a1.setMeetingname("test1");

        Article a2 = new Article();
        a2.setId(2L);
        a2.setContributorName(name);
        a2.setTitle("article2");
        a2.setMeetingname("test2");

        articles.add(a1);
        articles.add(a2);

        return ResponseEntity.ok(articles);
    }

    @GetMapping("/demo/meeting/chairnot")
    public ResponseEntity<?> findMeetingByStatusAndChairNameNot(String status, String chairName) {
        List<Meeting> meetings = new ArrayList<>();
        Meeting m = new Meeting();
        m.setId(1L);
        m.setStatus(status);
        m.setChairName(chairName + "not");
        m.setMeetingName("test");
        m.setAcronym("chairnot");
        meetings.add(m);

        return ResponseEntity.ok(meetings);
    }
}
