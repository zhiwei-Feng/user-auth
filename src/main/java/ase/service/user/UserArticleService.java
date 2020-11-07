package ase.service.user;

import ase.config.RemoteServiceConfig;
import ase.domain.*;
import ase.exception.InternalServerError;
import ase.exception.MeetingUnavaliableToOperateException;
import ase.exception.UserNamedidntExistException;
import ase.exception.user.ArticleNotFoundException;
import ase.repository.*;
import ase.request.user.ArticleRequest;
import ase.utility.ApiUtil;
import ase.utility.contract.ArticleStatus;
import ase.utility.contract.MeetingStatus;
import ase.utility.response.ResponseGenerator;
import ase.utility.response.ResponseWrapper;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class UserArticleService {
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RemoteServiceConfig remote;
    @Autowired
    private ApiUtil apiUtil;

    @Autowired
    public UserArticleService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Transactional
    @Deprecated
    public ResponseWrapper<?> getArticleDetail(String articleId, String token) {
        // Article article = articleRepository.findById(Long.parseLong(articleId));
        // 构造请求头，加入token
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("authorization", token);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("id", articleId);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, httpHeaders);
        ResponseEntity<Article> resp = restTemplate.exchange(
                apiUtil.encodeUriForGet(params, remote.getFindArticleById()),
                HttpMethod.GET,
                entity,
                Article.class);
        Article article = resp.getBody();
        // api 调用结束
        if (article == null) {
            throw new ArticleNotFoundException(articleId);
        }
        HashMap<String, Object> returnMap = ResponseGenerator.generate(
                article,
                new String[]{"id", "contributorName", "meetingName", "submitDate",
                        "title", "articleAbstract", "filePath", "status"}, null
        );
        if (returnMap == null)
            throw new InternalServerError("in Article Service, in getArticleDetail");
        Set<Author> returnAuthors = new HashSet<>();
        for (Pair<Author, Integer> p : article.getAuthors()) {
            returnAuthors.add(p.getKey());
        }
        returnMap.put("authors", returnAuthors);
        returnMap.put("topic", article.getTopic());

        HashMap<String, HashMap<String, Object>> body = new HashMap<>();
        body.put("articleDetail", returnMap);

        return new ResponseWrapper<>(200, ResponseGenerator.success, body);

    }

    @Transactional
    @Deprecated
    public ResponseWrapper<?> getAllReviews(String articleId) {
        //Article article = articleRepository.findById(Long.parseLong(articleId));
        Article article = null;
        if (article == null)
            throw new ArticleNotFoundException(articleId);

//        Set<ReviewRelation> allReviews = reviewRelationRepository.findReviewRelationsByArticleId(article.getId());
        Set<ReviewRelation> allReviews = new HashSet<>();

        HashMap<String, Set<HashMap<String, Object>>> respBody = new HashMap<>();
        Set<HashMap<String, Object>> reviews = new HashSet<>();
        for (ReviewRelation relation : allReviews) {
            HashMap<String, Object> items = new HashMap<>();
            items.put("score", relation.getScore());
            items.put("confidence", relation.getConfidence());
            items.put("review", relation.getReviews());

            reviews.add(items);
        }
        respBody.put("reviews", reviews);
        return new ResponseWrapper<>(200, ResponseGenerator.success, respBody);

    }

}