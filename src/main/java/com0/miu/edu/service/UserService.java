package com0.miu.edu.service;


import com0.miu.edu.dto.RoleDTO;
import com0.miu.edu.dto.UserDTO;
import com0.miu.edu.dto.Users;
import com0.miu.edu.model.Role;
import com0.miu.edu.model.User;

public interface UserService {
    UserDTO saveUser(User user);
    RoleDTO saveRole(Role role);
    void addRoleToUser(String userName, String roleName); // we assume here that the userName is unique. This is important
    UserDTO getUser(String userName); // we assume here that the userName is unique. This is important
    Users getUsers(); // To send a specific amount of users only
    void removeUser(String userName);
}
