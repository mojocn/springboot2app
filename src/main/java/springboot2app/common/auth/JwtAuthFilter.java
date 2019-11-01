package springboot2app.common.auth;

import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import springboot2app.entity.Role;
import springboot2app.entity.User;
import springboot2app.service.RoleService;
import springboot2app.service.UserService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;


@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(JwtAuthFilter.class);
    final String tokenHeader = "Authorization";
    final String tokenPrefix = "Bearer";
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final RoleService roleService;

    public JwtAuthFilter(JwtUtil jwtUtil, UserService userService, RoleService roleService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain) throws ServletException, IOException {
        String method = request.getMethod();
        String uri = request.getRequestURI();
        // System.out.println(node);
        // 从http头部读取jwt
        String authHeader = request.getHeader(this.tokenHeader);
        if (authHeader != null && authHeader.startsWith(tokenPrefix)) {

            final String authToken = authHeader.substring(tokenPrefix.length() + 1); // The part after "Bearer "
            String userEmail = jwtUtil.getEmailFromToken(authToken);
            Integer userId = jwtUtil.getClaimFromToken(authToken, "uid", Integer.class);
            Integer roleId = jwtUtil.getClaimFromToken(authToken, "rid", Integer.class);
            // 从jwt中解出账号与角色信息

            User user = userService.findById(userId);
            if (user == null) {
                throw new ServletException("无效token 找不到对应用户");
            }
            Role role = roleService.findById(roleId);
            if (role == null) {
                throw new ServletException("无效token 找不到对应角色");
            }

            if (ifHasNoPermission(role, method, uri)) {
                throw new ServletException("用户没有权限访问这个接口");
            }

            // 如果jwt正确解出账号信息，说明是合法用户，设置认证信息，认证通过
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                MyUserDetails myUserDetails = new MyUserDetails(user);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        myUserDetails, null, myUserDetails.getAuthorities());

                // 把请求的信息设置到UsernamePasswordAuthenticationToken details对象里面，包括发请求的ip等
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 设置认证信息
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        // 调用下一个过滤器
        chain.doFilter(request, response);
    }

    private boolean ifHasNoPermission(Role role, String method, String uri) {
        if (role.getName().equals("超级管理员")) {
            return false;
        }
        String[] list = role.getPermission().split(",");
        String node = method + "." + uri;
        return !Arrays.asList(list).contains(node);
    }
}

