package ase.controller.user;

import ase.domain.User;
import ase.service.Service;
import ase.service.user.api.UserService;
import ase.utility.response.ResponseGenerator;
import ase.utility.response.ResponseWrapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserCommonController {

    @Autowired
    private UserService userService;

    /**
     * user findById
     * 服务api
     *
     * @param id 主键
     * @return ResponseEntity
     */
    @ApiOperation(value = "根据id查找用户", response = User.class)
    @GetMapping("/user/id")
    public ResponseEntity<?> findUserById(long id) {
        return ResponseEntity.ok(userService.findUserById(id));
    }

    /**
     * user findByFullnameAndEmail
     * 服务api
     *
     * @param fullname 对应field
     * @param email    对应field
     * @return ResponseEntity
     */
    @ApiOperation(value = "通过author信息找用户", notes = "根据fullname和email来找", response = User.class)
    @GetMapping("/user/author")
    public ResponseEntity<?> findUserByFullnameAndEmail(String fullname, String email) {
        return ResponseEntity.ok(userService.findUserByFullnameAndEmail(fullname, email));
    }

    /**
     * user findByUsername
     * 服务api
     *
     * @param username 用户名
     * @return ResponseEntity
     */
    @ApiOperation(value = "通过username找用户", response = User.class)
    @GetMapping("/user/username")
    public ResponseEntity<?> findUserByUsername(String username) {
        return ResponseEntity.ok(userService.findUserByUsername(username));
    }
}
