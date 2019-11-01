package springboot2app.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot2app.common.annotation.Menu;
import springboot2app.common.error.ExceptionBiz;
import springboot2app.common.util.Result;
import springboot2app.entity.User;
import springboot2app.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    @Menu(method = "GET", uri = "/api/v1/users", mod = "系统管理", sub = "用户管理", name = "查询")
    public ResponseEntity<Result> getAllUsers(@RequestParam Integer page, @RequestParam Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "updatedAt");
        Page<User> userPage = userService.findAll(pageable);
        Result res = Result.of(userPage, page);
        return ResponseEntity.ok(res);
    }

    @PostMapping()
    @Menu(method = "POST", uri = "/api/v1/users", mod = "系统管理", sub = "用户管理", name = "创建")
    public ResponseEntity<Result> create(@Valid @RequestBody User dto) throws ExceptionBiz {
        Result res = Result.of("user", userService.create(dto));
        return ResponseEntity.ok(res);
    }

    @PatchMapping()
    @Menu(method = "PATCH", uri = "/api/v1/users", mod = "系统管理", sub = "用户管理", name = "更新")
    public ResponseEntity<Result> update(@Valid @RequestBody User dto) throws ExceptionBiz {
        Result res = Result.of("user", userService.update(dto));
        return ResponseEntity.ok(res);
    }

    @DeleteMapping()
    @Menu(method = "DELETE", uri = "/api/v1/users", mod = "系统管理", sub = "用户管理", name = "删除")
    public ResponseEntity<Result> remove(@RequestParam(value = "id", required = true) Integer id) throws ExceptionBiz {
        if (id < 2) {
            throw new ExceptionBiz("id 不能小于2");
        }
        Result res = Result.of("id", id);
        userService.remove(id);
        return ResponseEntity.ok(res);
    }
}