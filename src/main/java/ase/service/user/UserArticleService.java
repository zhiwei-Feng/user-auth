package ase.service.user;

import ase.config.RemoteServiceConfig;
import ase.domain.*;
import ase.exception.InternalServerError;
import ase.exception.MeetingUnavaliableToOperateException;
import ase.exception.UserNamedidntExistException;
import ase.exception.user.ArticleNotFoundException;
import ase.repository.*;
import ase.request.user.ArticleRequest;
import ase.utility.contract.ArticleStatus;
import ase.utility.contract.MeetingStatus;
import ase.utility.response.ResponseGenerator;
import ase.utility.response.ResponseWrapper;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

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
    public UserArticleService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public ResponseWrapper<?> getArticleDetail(String articleId, String token) {
        // todo 调用article api，根据articleId获取对应article信息,set null temporarily
        // Article article = articleRepository.findById(Long.parseLong(articleId));
        String articleApi = remote.getFindArticleById();
        // 构造请求头，加入token
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("authorization", token);
        Map<String, String> params = new HashMap<>();
        params.put("id", articleId);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(params, httpHeaders);
        ResponseEntity<Article> resp = restTemplate.exchange(articleApi, HttpMethod.GET, entity, Article.class);
        Article article = resp.getBody();
        // api 调用结束
        if (article == null) {
            throw new ArticleNotFoundException(articleId);
        }
        HashMap<String, Object> returnMap = ResponseGenerator.generate(
                article,
                new String[]{"contributorName", "meetingName", "submitDate",
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
    public ResponseWrapper<?> uploadNewArticle(ArticleRequest request, String targetRootDir) {
        String meetingName = request.getMeetingName();
        String username = request.getUsername();

        // todo 调用meeting api根据meeting name 获取对应Meeting信息， set null temporarily
        // Meeting meeting = meetingRepository.findByMeetingName(meetingName);
        Meeting meeting = null;
        User articleUploader = userRepository.findByUsername(username);

        //guarantee that this operation is valid
        authenticateArticle(meeting, articleUploader);

        MultipartFile pdfFile = request.getFile();
        //save the file, if exceptions happens, throw new InternalServerError
        String internalFilePath = targetRootDir +
                articleUploader.getUsername() + File.separator +
                request.getSubmitDate() + File.separator;
        try {
            saveFileToServer(pdfFile, internalFilePath);
        } catch (IOException ex) {
            throw new InternalServerError("UserArticleService.uploadNewArticle(): error occurred when saving the article pdf");
        }

        Set<String> clearTopics = getClearedTopics(request.getTopics());

        Article newArticle = new Article(
                request.getUsername(),
                request.getMeetingName(),
                request.getSubmitDate(),
                request.getEssayTitle(),
                request.getEssayAbstract(),
                internalFilePath + pdfFile.getOriginalFilename(),
                ArticleStatus.queuing,
                clearTopics,
                request.getAuthors()
        );
        // todo 调用article api去插入一个新的article，当前简单将其注释
        //articleRepository.save(newArticle);

        return new ResponseWrapper<>(200, ResponseGenerator.success, new HashMap<>());
    }

    @Transactional
    public ResponseWrapper<?> updateExistedArticle(String articleId, ArticleRequest request, String targetRootDir) {
        // todo 调用article和meeting的api，查询对应的信息，暂时赋值为null
//        Article article = articleRepository.findById(Long.parseLong(articleId));
//        if (article == null)
//            throw new ArticleNotFoundException(articleId);
//
//        Meeting meeting = meetingRepository.findByMeetingName(request.getMeetingName());
        Article article = null;
        Meeting meeting = null;
        User user = userRepository.findByUsername(request.getUsername());

        authenticateArticle(meeting, user);
        if (request.getFile() != null) {

            //delete the previous pdf file
            String previousPdfPath = article.getFilePath();
            File file = new File(previousPdfPath);
            if (file.exists()) {
                if (!file.delete())
                    throw new InternalServerError("UserArticleService.updateExistedArticle(): file delete failed");
            } else {
                throw new InternalServerError("UserArticleService.updateExistedArticle(): previous pdf doesn't exist");
            }
            String internalFilePath = targetRootDir +
                    user.getUsername() + File.separator +
                    request.getSubmitDate() + File.separator;

            MultipartFile pdfFile = request.getFile();

            try {
                saveFileToServer(pdfFile, internalFilePath);
            } catch (IOException ex) {
                throw new InternalServerError("UserArticleService.uploadNewArticle(): error occurred when saving the article pdf");
            }
            article.setFilePath(internalFilePath + pdfFile.getOriginalFilename());
        }
        Set<String> clearTopics = getClearedTopics(request.getTopics());


        //then update all the information of the previous article
        article.setMeetingname(request.getMeetingName());
        article.setContributorName(request.getUsername());
        article.setTitle(request.getEssayTitle());
        article.setArticleAbstract(request.getEssayAbstract());
        article.setSubmitDate(request.getSubmitDate());

        article.setTopic(clearTopics);
        article.setAuthors(request.getAuthors());
        // todo 调用article api去插入一个新的article，当前简单将其注释
        //articleRepository.save(article);
        //after all the update, return the success message

        return new ResponseWrapper<>(200, ResponseGenerator.success, new HashMap<>());
    }

    @Transactional
    public ResponseWrapper<?> getAllReviews(String articleId) {
        // todo article api
        //Article article = articleRepository.findById(Long.parseLong(articleId));
        Article article = null;
        if (article == null)
            throw new ArticleNotFoundException(articleId);

        //todo 调用reviewRelation api获取对应的信息，暂时置空
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

    //Before use this internal method
    //please Guarantee that file is savable (not null)
    private void saveFileToServer(MultipartFile file, String rootDirPath)
            throws IOException {

        byte[] fileBytes = file.getBytes();
        Path restorePath = Paths.get(rootDirPath + file.getOriginalFilename());

        //如果没有rootDirPath文件夹，则创建
        if (!Files.isWritable(restorePath)) {
            Files.createDirectories(Paths.get(rootDirPath));
        }

        Files.write(restorePath, fileBytes);
    }

    //this function is used to guarantee a article can be upload or update
    private void authenticateArticle(Meeting meeting, User user) {
        if (meeting == null)
            throw new MeetingUnavaliableToOperateException("Not created");
        if (user == null)
            throw new UserNamedidntExistException("not a valid user");

        if (!meeting.getStatus().equals(MeetingStatus.submissionAvaliable))
            throw new MeetingUnavaliableToOperateException("update or upload articles");
    }

    private Set<String> getClearedTopics(Set<String> topic) {
        Set<String> clearTopics = new HashSet<>();

        for (String t : topic) {
            t = t.replaceAll("\"", "");
            t = t.replace("[", "");
            t = t.replace("]", "");
            clearTopics.add(t);
        }

        return clearTopics;

    }
}