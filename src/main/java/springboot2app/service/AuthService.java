package springboot2app.service;

import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import springboot2app.common.auth.JwtUtil;
import springboot2app.common.auth.MyUserDetails;
import springboot2app.common.util.Result;
import springboot2app.entity.User;
import springboot2app.repository.UserRepository;

@Service
public class AuthService {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(AuthService.class);

    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;
    private UserRepository userRepository;


    public AuthService(
            AuthenticationManager authenticationManager,
            UserRepository userRepository,
            JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    public User register(User userToAdd) {
        return userRepository.save(userToAdd);
    }

    public Result login(String username, String password) throws AuthenticationException {
        // 认证用户，认证失败抛出异常，由JwtAuthError的commence类返回401
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        final Authentication authentication = authenticationManager.authenticate(upToken);
        //may throw exception
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 如果认证通过，返回jwt
        final MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();
        final String token = jwtUtil.generateToken(user);
        Result res = Result.of("token", token);
        res.putData("user", user);
        return res;
    }

    public String refresh(String oldToken) {
        String newToken = null;
        try {
            newToken = jwtUtil.refreshToken(oldToken);
        } catch (Exception e) {
            log.debug("异常详情", e);
            log.info("无效token");
        }
        return newToken;
    }
}