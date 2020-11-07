package ase.service.user.api;

import ase.domain.User;
import ase.utility.response.ResponseWrapper;

public interface UserService {

    User findUserById(long id);

    User findUserByFullnameAndEmail(String fullname, String email);

    User findUserByUsername(String username);
}
