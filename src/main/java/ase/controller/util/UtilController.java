package ase.controller.util;

import ase.request.util.LoginRequest;
import ase.request.util.RegisterRequest;
import ase.service.Service;
import ase.utility.response.ResponseGenerator;
import ase.utility.response.ResponseWrapper;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UtilController {

    Logger logger = LoggerFactory.getLogger(UtilController.class);
    private Service service;

    @Autowired
    public UtilController(Service service) {
        this.service = service;
    }

    @GetMapping("/welcome")
    public ResponseEntity<?> welcome() {
        Map<String, String> response = new HashMap<>();
        String message = "Welcome to 2020 Software Engineering Lab2";
        response.put("message", message);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        logger.debug("RegistrationForm: " + request.toString());

        return ResponseEntity.ok(service.register(request));
    }

    @ApiOperation("登录")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        logger.debug("LoginForm: " + request.toString());

        return ResponseEntity.ok(service.login(request));
    }

    @GetMapping("/user/userinfo")
    public ResponseEntity<?> getUserinfo(String username) {
        logger.debug("Get user info: " + username);
        return ResponseEntity.ok(service.getUserinfo(username));
    }

    @GetMapping("/util/users")
    public ResponseEntity<?> searchUsersbyFullname(String fullname) {
        logger.debug("Users with fullname " + fullname + " : ");
        return ResponseEntity.ok(service.searchUsersbyFullname(fullname));
    }

    @GetMapping("/check")
    public ResponseEntity<?> check() {
        return ResponseEntity.ok(new ResponseWrapper<>(200, ResponseGenerator.success, null));
    }
}
