package springboot2app.service;

import springboot2app.common.error.ExceptionBiz;
import springboot2app.model.RoleEntity;
import springboot2app.model.UserEntity;
import springboot2app.repository.RoleRepository;
import springboot2app.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    public Page<UserEntity> findAll(Pageable var1) {
        return userRepository.findAll(var1);
    }


    public void createGodUser(RoleEntity role) {
        List<UserEntity> users = userRepository.findByEmail("admin@mojotv.com");
        if (users.size() < 1) {
            UserEntity userEntity = new UserEntity();
            userEntity.setName("张三");
            userEntity.setId(1);
            userEntity.setEmail("admin@mojotv.com");
            userEntity.setMobile("15812345678");
            userEntity.setPassword("123456");
            Calendar c = Calendar.getInstance();
            c.add(Calendar.YEAR, 50);
            Date newDate = c.getTime();
            userEntity.setExpireAt(new Timestamp(newDate.getTime()));
            userRepository.save(userEntity);
        }
    }

    public UserEntity findById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }


    public void remove(Integer id) {
        userRepository.deleteById(id);
    }

    public UserEntity update(UserEntity ins) throws ExceptionBiz {
        if (ins.getId() < 2) {
            throw new ExceptionBiz("用户ID 必须大于1: ");
        }
        RoleEntity roleEntity = roleRepository.findById(ins.getRoleId()).orElse(null);
        if (roleEntity == null) {
            throw new ExceptionBiz("无效role_id: " + ins.getRoleId());
        }
        return userRepository.save(ins);
    }

    public UserEntity create(UserEntity ins) throws ExceptionBiz {
        RoleEntity roleEntity = roleRepository.findById(ins.getRoleId()).orElse(null);
        if (roleEntity == null) {
            throw new ExceptionBiz("无效role_id: " + ins.getRoleId());
        }
        return userRepository.save(ins);
    }
}
