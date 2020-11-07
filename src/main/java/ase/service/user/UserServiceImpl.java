package ase.service.user;

import ase.domain.User;
import ase.repository.UserRepository;
import ase.service.user.api.UserService;
import ase.utility.response.ResponseGenerator;
import ase.utility.response.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findUserById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public User findUserByFullnameAndEmail(String fullname, String email) {
        return userRepository.findByFullnameAndEmail(fullname, email);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
