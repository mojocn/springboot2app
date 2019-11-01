package springboot2app.service.impl;

import springboot2app.common.auth.JwtUtil;
import springboot2app.common.auth.MyUserDetails;
import springboot2app.common.util.Result;
import springboot2app.model.UserEntity;
import springboot2app.repository.UserRepository;
import springboot2app.service.AuthService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

    private AuthenticationManager authenticationManager;
    private MyUserDetailsService myUserDetailsService;
    private JwtUtil jwtUtil;
    private UserRepository userRepository;


    @Autowired
    public AuthServiceImpl(
            AuthenticationManager authenticationManager,
            MyUserDetailsService myUserDetailsService,
            UserRepository userRepository,
            JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.myUserDetailsService = myUserDetailsService;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity register(UserEntity userEntityToAdd) {
        return userRepository.save(userEntityToAdd);
    }

    @Override
    public Result login(String username, String password) {
        // 认证用户，认证失败抛出异常，由JwtAuthError的commence类返回401
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        final Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 如果认证通过，返回jwt
        final MyUserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
        UserEntity userEntity = userDetails.getUserEntity();
        final String token = jwtUtil.generateToken(userEntity);
        Result res = Result.of("token", token);
        res.putData("user", userEntity);
        return res;
    }

    @Override
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