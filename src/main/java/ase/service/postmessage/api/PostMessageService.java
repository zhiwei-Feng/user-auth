package ase.service.postmessage.api;

import ase.request.postmessage.PostMessageRequest;
import ase.utility.response.ResponseWrapper;

public interface PostMessageService {

    public ResponseWrapper<?> addPostMessage(PostMessageRequest postMessageRequest);

    public ResponseWrapper<?> findPostMessageByArticleIdAndStatus(long articleId, String status);

    public ResponseWrapper<?> findPostMessageById(Long id);
}
