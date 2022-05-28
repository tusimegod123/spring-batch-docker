package com0.miu.edu.service;


import com0.miu.edu.dto.RoleDTO;
import com0.miu.edu.dto.UserDTO;
import com0.miu.edu.dto.Users;
import com0.miu.edu.dto.UtilityDTO;
import com0.miu.edu.model.Role;
import com0.miu.edu.model.User;
import com0.miu.edu.repository.RoleRepository;
import com0.miu.edu.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;


@Service
@Transactional
@Slf4j // To log everything on this class to see what is going on in the log
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(userName);
        if(user == null) {
            log.error("The user your are trying to use is not in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.info("User {} found in the database", userName);
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
            return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), authorities);
        }
    }

    @Override
    public UserDTO saveUser(User user) {
        log.info("Saving a new user {} into the database", user.getName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return UtilityDTO.getUserDTOFromUser(userRepository.save(user));
    }

    @Override
    public RoleDTO saveRole(Role role) {
        log.info("Saving a new role {} into the database", role.getName());
        return UtilityDTO.getRoleDTOfromRole(roleRepository.save(role));
    }

    @Override
    public void addRoleToUser(String userName, String roleName) {
        log.info("Adding role {} to a user {} and save it into the database", roleName, userName);
        User user = userRepository.findByUserName(userName);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role); // Thanks to transactional annotations this is saved automatically in the database
    }

    @Override
    public UserDTO getUser(String userName) {
        log.info("Fetching user {} from the database", userName);
        return UtilityDTO.getUserDTOFromUser(userRepository.findByUserName(userName));
    }

    @Override
    public Users getUsers() {
        log.info("Fetching all users from the database");
        return new Users(userRepository.findAll());
    }

    @Override
    public void removeUser(String userName) {
        log.info("Deleting user {} from the database", userName);
        userRepository.delete(userRepository.findByUserName(userName));
    }


}
