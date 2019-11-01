package springboot2app.service.impl;

import springboot2app.common.auth.MyUserDetails;
import springboot2app.model.UserEntity;
import springboot2app.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 加载用户信息
    @Override
    public MyUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByMobile(username).get(0);
        if (userEntity == null) {
            throw new UsernameNotFoundException(username);
        }
        return new MyUserDetails(userEntity);
    }
}