package ase.service.user.api;

import ase.utility.response.ResponseWrapper;

public interface UserService {

    ResponseWrapper<?> findUserById(long id);

    ResponseWrapper<?> findUserByFullnameAndEmail(String fullname, String email);
}
