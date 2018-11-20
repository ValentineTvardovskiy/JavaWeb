package company.dao;

import company.model.User;

public interface UserDao {

    User addUser(User user);

    User findByEmail(String email);

    User findByToken(String token);
}