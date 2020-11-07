package ase.service.postmessage.api;

import ase.domain.PostMessage;
import ase.request.postmessage.PostMessageRequest;
import ase.utility.response.ResponseWrapper;

import java.util.List;

public interface PostMessageService {

    public void addPostMessage(PostMessageRequest postMessageRequest);

    public List<PostMessage> findPostMessageByArticleIdAndStatus(long articleId, String status);

    public PostMessage findPostMessageById(Long id);
}
