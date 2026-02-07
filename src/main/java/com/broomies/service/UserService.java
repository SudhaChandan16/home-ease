package com.broomies.service;

import com.broomies.dto.UserRegistrationDto;
import com.broomies.entity.Provider;
import com.broomies.entity.Role;
import com.broomies.entity.User;
import com.broomies.enums.RoleType;
import com.broomies.repository.ProviderRepository;
import com.broomies.repository.RoleRepository;
import com.broomies.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ProviderRepository providerRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository,
            ProviderRepository providerRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.providerRepository = providerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User registerUser(UserRegistrationDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setMobile(dto.getMobile());
        user.setAddress(dto.getAddress());

        // Assign Role
        RoleType roleType = dto.isProvider() ? RoleType.ROLE_PROVIDER : RoleType.ROLE_USER;
        Role role = roleRepository.findByName(roleType)
                .orElseGet(() -> roleRepository.save(new Role(roleType)));

        user.setRoles(Collections.singleton(role));

        User savedUser = userRepository.save(user);

        // If Provider, create Provider Profile
        if (dto.isProvider()) {
            Provider provider = new Provider();
            provider.setUser(savedUser);
            provider.setCategory(dto.getCategory());
            provider.setBio(dto.getBio());
            provider.setExperienceYears(dto.getExperienceYears());
            provider.setHourlyRate(dto.getHourlyRate());

            providerRepository.save(provider);
        }

        return savedUser;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}
