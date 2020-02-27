package com.andersenlab.bookstorageweb.dev;

import com.andersenlab.bookstorageweb.entity.Role;
import com.andersenlab.bookstorageweb.repository.RoleRepository;
import com.andersenlab.bookstorageweb.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Slf4j
@Profile("dev")
class DbInitFiller {

    final ApplicationContext context;

    public DbInitFiller(ApplicationContext context) {
        this.context = context;
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
