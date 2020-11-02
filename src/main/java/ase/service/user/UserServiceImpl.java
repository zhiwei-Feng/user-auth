package ase.service.user;

import ase.domain.User;
import ase.repository.UserRepository;
import ase.service.user.api.UserService;
import ase.utility.response.ResponseGenerator;
import ase.utility.response.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseWrapper<?> findUserById(long id) {
        User user = userRepository.findById(id);
        Map<String, User> respBody = new HashMap<>();
        respBody.put("data", user);
        return new ResponseWrapper<>(200, ResponseGenerator.success, respBody);
    }

    @Override
    public ResponseWrapper<?> findUserByFullnameAndEmail(String fullname, String email) {
        User authorUser = userRepository.findByFullnameAndEmail(fullname, email);
        Map<String, User> respBody = new HashMap<>();
        respBody.put("data", authorUser);
        return new ResponseWrapper<>(200, ResponseGenerator.success, respBody);
    }
}
