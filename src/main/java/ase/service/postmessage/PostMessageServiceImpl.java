package ase.service.postmessage;

import ase.domain.PostMessage;
import ase.repository.PostRepository;
import ase.repository.UserRepository;
import ase.request.postmessage.PostMessageRequest;
import ase.service.postmessage.api.PostMessageService;
import ase.utility.response.ResponseGenerator;
import ase.utility.response.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class PostMessageServiceImpl implements PostMessageService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public ResponseWrapper<?> addPostMessage(PostMessageRequest request) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        PostMessage post = new PostMessage(
                request.getPosterId(),
                request.getArticleId(),
                request.getTargetId(),
                request.getContent(),
                request.getStatus(),
                timestamp.toString()
        );
        postRepository.save(post);
        return new ResponseWrapper<>(200, ResponseGenerator.success, null);
    }

    @Override
    public ResponseWrapper<?> findPostMessageByArticleIdAndStatus(long articleId, String status) {
        List<PostMessage> postList = postRepository.findByArticleIdAndStatus(articleId, status);
        Map<String, List<PostMessage>> respBody = new HashMap<>();
        respBody.put("data", postList);
        return new ResponseWrapper<>(200, ResponseGenerator.success, respBody);
    }

    @Override
    public ResponseWrapper<?> findPostMessageById(Long id) {
        PostMessage message = postRepository.findById(id.longValue());
        Map<String, PostMessage> respBody = new HashMap<>();
        respBody.put("data", message);
        return new ResponseWrapper<>(200, ResponseGenerator.success, respBody);
    }
}
