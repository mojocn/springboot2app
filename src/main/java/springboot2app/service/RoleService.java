package springboot2app.service;

import springboot2app.common.util.MenuItem;
import springboot2app.common.util.MenuListener;
import springboot2app.model.RoleEntity;
import springboot2app.repository.RoleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

@Service
public class RoleService {
    private final MenuListener menuListener;
    private final RoleRepository roleRepository;

    public RoleService(MenuListener menuListener, RoleRepository roleRepository) {
        this.menuListener = menuListener;
        this.roleRepository = roleRepository;
    }

    public Page<RoleEntity> findAll(Pageable var1) {
        return roleRepository.findAll(var1);
    }

    public Map<String, Map<String, ArrayList<MenuItem>>> getMenu() {
        return menuListener.menus;
    }

    public void remove(Integer id) {
        roleRepository.deleteById(id);
    }

    public RoleEntity update(RoleEntity role) {
        Optional<RoleEntity> re = roleRepository.findById(role.getId());
        if (re.isPresent()) {
            RoleEntity roleIns = re.get();
            roleIns.setName(role.getName());
            roleIns.setPermission(role.getPermission());
            return roleRepository.save(roleIns);
        }
        return null;
    }

    public RoleEntity create(RoleEntity role) {
        return roleRepository.save(role);
    }

    public RoleEntity createInitRow() {
        Optional<RoleEntity> someRole = roleRepository.findByName("超级管理员");
        if (someRole.isPresent()) {
            return someRole.get();
        }
        ArrayList<String> permissions = new ArrayList<>();
        menuListener.rows.forEach(e -> {
            permissions.add(e.getPath());
        });
        RoleEntity roleIns = new RoleEntity();
        roleIns.setPermission(String.join(",", permissions));
        roleIns.setName("超级管理员");
        return roleRepository.save(roleIns);
    }

    public RoleEntity findById(Integer id) {
        return roleRepository.findById(id).orElse(null);
    }
}
