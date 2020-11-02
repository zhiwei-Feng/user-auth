package ase.controller.user;

import ase.service.Service;
import ase.service.user.api.UserService;
import ase.utility.response.ResponseGenerator;
import ase.utility.response.ResponseWrapper;
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
     *
     * @param id 主键
     * @return ResponseEntity
     */
    @GetMapping("/user/id")
    public ResponseEntity<?> findUserById(long id) {
        return ResponseEntity.ok(userService.findUserById(id));
    }

    /**
     * user findByFullnameAndEmail
     *
     * @param fullname 对应field
     * @param email    对应field
     * @return ResponseEntity
     */
    @GetMapping("/user/author")
    public ResponseEntity<?> findUserByFullnameAndEmail(String fullname, String email) {
        return ResponseEntity.ok(userService.findUserByFullnameAndEmail(fullname, email));
    }
}
