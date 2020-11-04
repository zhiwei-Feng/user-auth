package ase.controller.postmessage;

import ase.request.postmessage.PostMessageRequest;
import ase.service.Service;
import ase.service.postmessage.api.PostMessageService;
import ase.utility.response.ResponseGenerator;
import ase.utility.response.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostMessageController {

    @Autowired
    private Service service;
    @Autowired
    private PostMessageService postMessageService;

    /**
     * 新增PostMessage
     * 服务api
     *
     * @param postMessageRequest 请求体
     * @return ResponseEntity
     */
    @PostMapping("/postmessage")
    public ResponseEntity<?> addPostMessage(@RequestBody PostMessageRequest postMessageRequest) {
        return ResponseEntity.ok(postMessageService.addPostMessage(postMessageRequest));
    }

    /**
     * 查找对应article下的属于status状态的PostMessage
     * 服务api
     *
     * @param articleId field
     * @param status    field
     * @return ResponseEntity
     */
    @GetMapping("/postmessage/article")
    public ResponseEntity<?> findPostMessageByArticleIdAndStatus(long articleId, String status) {
        return ResponseEntity.ok(postMessageService.findPostMessageByArticleIdAndStatus(articleId, status));
    }

    /**
     * 根据id查找PostMessage
     * 服务api
     *
     * @param id 主键
     * @return ResponseEntity
     */
    @GetMapping("/postmessage/id")
    public ResponseEntity<?> findPostMessageById(Long id) {
        return ResponseEntity.ok(postMessageService.findPostMessageById(id));
    }
}
