package company.dao;

import company.model.User;

public interface UserDao {
    public User addUser(User user);
    public User findByEmail(String email);

    User findByToken(String token);
}