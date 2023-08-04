package com.oauth.server.common.bootStrap;

import com.oauth.server.database.model.Role;
import com.oauth.server.database.model.User;
import com.oauth.server.database.repository.RoleRepository;
import com.oauth.server.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class BootStrap {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public BootStrap(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void init(){
        User admin = new User();
        admin.setUsername("admin");
        String adminPass = passwordEncoder.encode("admin");
        admin.setPassword(adminPass);
        admin.setPhoneNumber("09166761607");
        userRepository.save(admin);

        Role adminRole = new Role();
        adminRole.setName("ROLE_ADMIN");
        adminRole.setUser(admin);
        roleRepository.save(adminRole);

        User user = new User();
        user.setUsername("user");
        String userPass = passwordEncoder.encode("user");
        user.setPassword(userPass);

        userRepository.save(user);
        Role userRole = new Role();
        userRole.setName("ROLE_USER");
        userRole.setUser(user);
        roleRepository.save(userRole);

    }
}
