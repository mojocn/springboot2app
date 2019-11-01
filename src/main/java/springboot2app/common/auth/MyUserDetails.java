package springboot2app.common.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import springboot2app.entity.User;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class MyUserDetails implements UserDetails {

    private static final long serialVersionUID = -2336372258701871345L;

    //用户实体类
    private User user;

    public MyUserDetails(User user) {
        this.user = user;
    }

    // 提供权限信息
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("jwt"));
        return authorities;
    }

    // 提供账号名称
    @Override
    public String getUsername() {
        return getUser().getMobile();
    }

    // 提供密码
    @Override
    public String getPassword() {
        return getUser().getPassword();
    }

    // 账号是否没过期，过期的用户无法认证
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 账号是否没锁住，锁住的用户无法认证
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 密码是否没过期，密码过期的用户无法认证
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 用户是否使能，未使能的用户无法认证
    @Override
    public boolean isEnabled() {
        return true;
    }

    public User getUser() {
        return user;
    }

}