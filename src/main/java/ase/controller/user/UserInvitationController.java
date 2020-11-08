package ase.controller.user;

import ase.request.user.InvitationRepoRequest;
import ase.service.Service;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@RestController
public class UserInvitationController {
    Logger logger = LoggerFactory.getLogger(UserInvitationController.class);
    private Service service;

    @Autowired
    UserInvitationController(Service service) {
        this.service = service;
    }

    @ApiOperation(value = "根据username来找用户下的")
    @GetMapping("/user/undealedNotifications")
    public ResponseEntity<?> undealedNotifications(String username) {
        logger.debug("Get undealedNotifications info : " + username);
        return ResponseEntity.ok(service.undealedNotifications(username));
    }

    @PostMapping("/user/invitationRepo")
    public ResponseEntity<?> invitationRepo(@RequestBody JSONObject request) {
        logger.info("Post invitationRepo info : " + request.toString());
        // Set<String> 并不能直接接受JSONArray对象，需要通过List来中转
        JSONArray temp = request.getJSONArray("topic");
        List<String> tempList = temp.toJavaList(String.class);
        InvitationRepoRequest req = request.toJavaObject(InvitationRepoRequest.class);
        req.setTopics(new HashSet<>(tempList));
        logger.info("req: " + req);
        return ResponseEntity.ok(service.invitationRepo(req));
    }

    @GetMapping("/user/undealedNotificationsNum")
    public ResponseEntity<?> undealedNotificationsNum(String username) {
        logger.debug("Get undealedNotificationsNum info : " + username);
        return ResponseEntity.ok(service.undealedNotificationsNum(username));
    }

    @GetMapping("/user/alreadyDealedNotifications")
    public ResponseEntity<?> alreadyDealedNotifications(String username) {
        logger.debug("Get alreadyDealedNotifications info : " + username);
        return ResponseEntity.ok(service.alreadyDealedNotifications(username));
    }

}
