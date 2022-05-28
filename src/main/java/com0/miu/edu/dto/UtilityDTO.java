package com0.miu.edu.dto;


import com0.miu.edu.model.Role;
import com0.miu.edu.model.User;

public class UtilityDTO {

    public static UserDTO getUserDTOFromUser(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setUserName(user.getUserName());
        userDTO.setPassword(user.getPassword());
        userDTO.setRoles(user.getRoles());
        return userDTO;
    }

    public static User getUserFromUserDTO(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setUserName(userDTO.getUserName());
        user.setPassword(userDTO.getPassword());
        user.setRoles(userDTO.getRoles());
        return user;
    }

    public static RoleDTO getRoleDTOfromRole(Role role) {
        RoleDTO roleDTO = new RoleDTO(role.getId(), role.getName());
        return roleDTO;
    }

    public static Role getRolefromRoleDTO(RoleDTO roleDTO) {
        Role role = new Role(roleDTO.getId(), roleDTO.getName());
        return role;
    }

}
