package springboot2app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import springboot2app.common.util.Result;
import springboot2app.entity.User;
import springboot2app.request.UserLogin;
import springboot2app.service.AuthService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Result> register(@Valid @RequestBody User rUser) throws Exception {
        User u = authService.register(rUser);
        Result res = Result.of("user", u);
        return ResponseEntity.ok(res);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Result> login(@Valid @RequestBody UserLogin loginRequest) throws AuthenticationException {
        Result res = authService.login(loginRequest.getMobile(), loginRequest.getPassword());
        return ResponseEntity.ok(res);
    }

    @RequestMapping(value = "/refresh", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Result> refresh(HttpServletRequest request, @RequestParam String token) throws AuthenticationException {
        Result res = new Result(200, "ok");
        String refreshedToken = authService.refresh(token);
        if (refreshedToken == null) {
            res.setStatus(400);
            res.setMessage("无效token");
            return new ResponseEntity<Result>(res, HttpStatus.BAD_REQUEST);
        }
        res.putData("token", token);
        return ResponseEntity.ok(res);
    }

}