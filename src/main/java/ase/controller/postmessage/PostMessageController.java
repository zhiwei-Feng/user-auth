package ase.controller.postmessage;

import ase.request.postmessage.PostMessageRequest;
import ase.service.Service;
import ase.utility.response.ResponseGenerator;
import ase.utility.response.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostMessageController {

    @Autowired
    private Service service;

    /**
     * 新增PostMessage
     *
     * @param postMessageRequest 请求体
     * @return ResponseEntity
     */
    @PostMapping("/postmessage")
    public ResponseEntity<?> addPostMessage(@RequestBody PostMessageRequest postMessageRequest) {
        // todo: 待实现
        return ResponseEntity.ok(new ResponseWrapper<>(200, ResponseGenerator.success, null));
    }

    /**
     * 查找对应article下的属于status状态的PostMessage
     *
     * @param articleId field
     * @param status    field
     * @return ResponseEntity
     */
    @PostMapping("/postmessage/article")
    public ResponseEntity<?> findPostMessageByArticleIdAndStatus(long articleId, String status) {
        // todo: 待实现
        return ResponseEntity.ok(new ResponseWrapper<>(200, ResponseGenerator.success, null));
    }

    /**
     * 根据id查找PostMessage
     *
     * @param id 主键
     * @return ResponseEntity
     */
    @PostMapping("/postmessage/id")
    public ResponseEntity<?> findPostMessageById(Long id) {
        // todo: 待实现
        return ResponseEntity.ok(new ResponseWrapper<>(200, ResponseGenerator.success, null));
    }
}
