package com0.miu.edu.dto;


import com0.miu.edu.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String name;
    private String userName;
    private String password;
    private Collection<Role> roles = new ArrayList<>();

}
