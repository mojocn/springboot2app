package springboot2app.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import springboot2app.common.error.ExceptionBiz;
import springboot2app.entity.Role;
import springboot2app.entity.User;
import springboot2app.repository.RoleRepository;
import springboot2app.repository.UserRepository;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public Page<User> findAll(Pageable var1) {
        return userRepository.findAll(var1);
    }


    public void createGodUser(Role role) {
        List<User> users = userRepository.findByEmail("admin@mojotv.com");
        if (users.size() < 1) {
            User user = new User();
            user.setName("张三");
            user.setId(1);
            user.setEmail("admin@mojotv.com");
            user.setMobile("13312345678");
            user.setPassword("123456");
            Calendar c = Calendar.getInstance();
            c.add(Calendar.YEAR, 50);
            Date newDate = c.getTime();
            user.setRoleId(role.getId());
            user.setExpireAt(new Timestamp(newDate.getTime()));
            userRepository.save(user);
        }
    }

    public User findById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }


    public void remove(Integer id) {
        userRepository.deleteById(id);
    }

    public User update(User ins) throws ExceptionBiz {
        if (ins.getId() < 2) {
            throw new ExceptionBiz("用户ID 必须大于1: ");
        }
        Role role = roleRepository.findById(ins.getRoleId()).orElse(null);
        if (role == null) {
            throw new ExceptionBiz("无效role_id: " + ins.getRoleId());
        }
        return userRepository.save(ins);
    }

    public User create(User ins) throws ExceptionBiz {
        Role role = roleRepository.findById(ins.getRoleId()).orElse(null);
        if (role == null) {
            throw new ExceptionBiz("无效role_id: " + ins.getRoleId());
        }
        return userRepository.save(ins);
    }
}
