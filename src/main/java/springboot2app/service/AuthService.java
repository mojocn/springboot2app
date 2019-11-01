package springboot2app.service;

import springboot2app.common.util.Result;
import springboot2app.model.UserEntity;

public interface AuthService {
    UserEntity register(UserEntity userEntityToAdd);

    Result login(String username, String password);

    String refresh(String oldToken);
}