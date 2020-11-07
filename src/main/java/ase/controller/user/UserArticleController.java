package ase.controller.user;

import ase.config.RemoteServiceConfig;
import ase.controller.util.UtilController;
import ase.domain.Author;
import ase.request.user.ArticleDetailRequest;
import ase.request.user.ArticleRequest;
import ase.service.Service;
import ase.service.user.UserArticleService;
import com.alibaba.fastjson.JSONArray;
import javafx.util.Pair;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class UserArticleController {

    private Service service;
    Logger logger = LoggerFactory.getLogger(UserArticleController.class);

    @Autowired
    public UserArticleController(Service service) {
        this.service = service;
    }

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RemoteServiceConfig remote;

    //get the detailed information about a article
    @GetMapping("/user/articleDetail")
    @Deprecated
    public ResponseEntity<?> getArticleDetail(String articleId, @RequestHeader("authorization") String token) {
        logger.debug("article detail get request received, article id = " + articleId);
        // 每个请求处理之前首先需要验证是否登录, 可能之后用aop来替代
        //通过RemoteServiceConfig注入user-auth服务的ip和port
        String checkApi = remote.getCheck();
        //构造请求头，加入token
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("authorization", token);
        HttpEntity<String> entity = new HttpEntity<>(null, httpHeaders);
        //发送check请求到user-auth服务中， 目前api写死的
        ResponseEntity<String> resp = restTemplate.exchange(checkApi, HttpMethod.GET, entity, String.class);
        if (resp.getStatusCode() != HttpStatus.OK) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(service.getArticleDetail(articleId, token));
    }


    @GetMapping("/user/reviews")
    @Deprecated
    public ResponseEntity<?> getAllReviewsOfArticle(String articleId) {
        return ResponseEntity.ok(service.getReviewsOfArticle(articleId));
    }

    private Set<Pair<Author, Integer>> generateAuthor(String authors) {
        List<Author> authorsList = JSONArray.parseArray(authors, Author.class);
        Set<Pair<Author, Integer>> authorArgument = new HashSet<>();
        int rank = 1;
        for (Author author : authorsList) {
            authorArgument.add(new Pair<>(author, rank++));
        }
        return authorArgument;
    }


    //post a new article
//    @PostMapping("/user/articleSubmission")

}
