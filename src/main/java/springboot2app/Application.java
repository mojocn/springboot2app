package springboot2app;

import springboot2app.model.RoleEntity;
import springboot2app.service.RoleService;
import springboot2app.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        System.out.println("======================");
        System.out.println("webapi service start...");
        System.out.println("======================");
    }

    @Bean
    public CommandLineRunner firstRows(UserService userService, RoleService roleService) {
        return (args) -> {
            RoleEntity role = roleService.createInitRow();
            userService.createGodUser(role);
        };
    }
}
