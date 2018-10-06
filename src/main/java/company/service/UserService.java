package company.service;

import company.model.User;

public interface UserService {
    public User addUser(User user);
    public User findByEmail(String email);
    boolean validatePassword(User user, String password);

}
