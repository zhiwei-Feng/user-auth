package ase.controller.user;

import ase.service.Service;
import ase.utility.response.ResponseGenerator;
import ase.utility.response.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserCommonController {

    @Autowired
    private Service service;

    /**
     * user findById
     * @param id 主键
     * @return ResponseEntity
     */
    @GetMapping("/user/id")
    public ResponseEntity<?> findUserById(long id){
        // todo: 待实现
        return ResponseEntity.ok(new ResponseWrapper<>(200, ResponseGenerator.success, null));
    }

    /**
     * user findByFullnameAndEmail
     * @param fullname 对应field
     * @param email 对应field
     * @return ResponseEntity
     */
    @GetMapping("/user/author")
    public ResponseEntity<?> findUserByFullnameAndEmail(String fullname, String email){
        // todo: 待实现
        return ResponseEntity.ok(new ResponseWrapper<>(200, ResponseGenerator.success, null));
    }
}
