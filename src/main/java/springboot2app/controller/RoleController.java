package springboot2app.controller;

import springboot2app.common.annotation.Menu;
import springboot2app.common.error.ExceptionBiz;
import springboot2app.common.util.Result;
import springboot2app.model.RoleEntity;
import springboot2app.service.RoleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/roles")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping(path = "menu")
    @Menu(method = "GET", uri = "/api/v1/roles/menu", mod = "系统管理", sub = "角色管理", name = "全部权限")
    public ResponseEntity<Result> getAllPermission() {
        Result res = Result.of("menu", roleService.getMenu());
        return ResponseEntity.ok(res);
    }

    @GetMapping()
    @Menu(method = "GET", uri = "/api/v1/roles", mod = "系统管理", sub = "角色管理", name = "列表")
    public ResponseEntity<Result> all(@RequestParam Integer page, @RequestParam Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<RoleEntity> userPage = roleService.findAll(pageable);
        Result res = Result.of(userPage, page);
        return ResponseEntity.ok(res);
    }

    @PostMapping()
    @Menu(method = "POST", uri = "/api/v1/roles", mod = "系统管理", sub = "角色管理", name = "创建")
    public ResponseEntity<Result> create(@Valid @RequestBody RoleEntity roleRequest) {
        Result res = Result.of("role", roleService.create(roleRequest));
        return ResponseEntity.ok(res);
    }

    @PatchMapping()
    @Menu(method = "PATCH", uri = "/api/v1/roles", mod = "系统管理", sub = "角色管理", name = "更新")
    public ResponseEntity<Result> update(@Valid @RequestBody RoleEntity roleRequest) {
        Result res = Result.of("role", roleService.update(roleRequest));
        return ResponseEntity.ok(res);
    }

    @DeleteMapping()
    @Menu(method = "DELETE", uri = "/api/v1/roles", mod = "系统管理", sub = "角色管理", name = "删除")
    public ResponseEntity<Result> remove(@RequestParam(value = "id", required = true) Integer id) throws ExceptionBiz {
        if (id < 2) {
            throw new ExceptionBiz("id 不能小于2");
        }
        Result res = Result.of("id", id);
        roleService.remove(id);
        return ResponseEntity.ok(res);
    }
}