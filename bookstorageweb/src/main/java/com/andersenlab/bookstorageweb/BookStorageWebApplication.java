package com.andersenlab.bookstorageweb;

import com.andersenlab.bookstorageweb.entity.Role;
import com.andersenlab.bookstorageweb.repository.RoleRepository;
import com.andersenlab.bookstorageweb.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import java.util.Set;

@SpringBootApplication
@EnableAspectJAutoProxy
@Slf4j
public class BookStorageWebApplication {

    @Autowired
    ApplicationContext context;

    public static void main(String[] args) {
        SpringApplication.run(BookStorageWebApplication.class, args);
    }

    @EventListener
    public void handleContextRefreshEvent(ContextRefreshedEvent ctxRefreshedEvent) {
        log.info("Init db with user entities");
        RoleRepository roleRepository = context.getBean(RoleRepository.class);
        UserService userService = context.getBean(UserService.class);

        Role userRole = new Role("ROLE_USER");
        Role redactorRole = new Role("ROLE_REDACTOR");

        roleRepository.save(userRole);
        roleRepository.save(redactorRole);

        userService.createUser("username", "123", Set.of(userRole, redactorRole));
        userService.createUser("foo", "ooo", Set.of(userRole));
        userService.createUser("bar", "zzz", Set.of());
    }

}
