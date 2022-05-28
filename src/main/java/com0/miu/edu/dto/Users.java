package com0.miu.edu.dto;

import com0.miu.edu.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    private Collection<User> users;
}
