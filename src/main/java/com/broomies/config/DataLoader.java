package com.broomies.config;

import com.broomies.entity.Role;
import com.broomies.entity.User;
import com.broomies.enums.RoleType;
import com.broomies.repository.RoleRepository;
import com.broomies.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@Configuration
public class DataLoader {

    @Bean
    public CommandLineRunner initData(RoleRepository roleRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        return args -> {
            // Initialize Roles
            roleRepository.findByName(RoleType.ROLE_USER)
                    .orElseGet(() -> roleRepository.save(new Role(RoleType.ROLE_USER)));

            roleRepository.findByName(RoleType.ROLE_PROVIDER)
                    .orElseGet(() -> roleRepository.save(new Role(RoleType.ROLE_PROVIDER)));

            Role roleAdmin = roleRepository.findByName(RoleType.ROLE_ADMIN)
                    .orElseGet(() -> roleRepository.save(new Role(RoleType.ROLE_ADMIN)));

            // Initialize Admin User if not exists
            if (!userRepository.existsByEmail("sudhachandan16@gmail.com")) {
                User admin = new User();
                admin.setName("Super Admin");
                admin.setEmail("sudhachandan16@gmail.com");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setMobile("0000000000");
                admin.setAddress("Admin HQ");
                admin.setRoles(Collections.singleton(roleAdmin));

                userRepository.save(admin);
                System.out.println("Admin User Created: sudhachandan16@gmail.com / admin123");
            }

            // Also create the generic admin user if not exists
            if (!userRepository.existsByEmail("admin@broomies.com")) {
                User genericAdmin = new User();
                genericAdmin.setName("Administrator");
                genericAdmin.setEmail("admin@broomies.com");
                genericAdmin.setPassword(passwordEncoder.encode("admin"));
                genericAdmin.setMobile("9999999999");
                genericAdmin.setAddress("Headquarters");
                genericAdmin.setRoles(Collections.singleton(roleAdmin));

                userRepository.save(genericAdmin);
                System.out.println("Generic Admin User Created: admin@broomies.com / admin");
            }
        };
    }
}
