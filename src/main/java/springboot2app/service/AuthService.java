package springboot2app.service;

import springboot2app.common.util.Result;
import springboot2app.entity.User;

public interface AuthService {
    User register(User userToAdd);

    Result login(String username, String password);

    String refresh(String oldToken);
}