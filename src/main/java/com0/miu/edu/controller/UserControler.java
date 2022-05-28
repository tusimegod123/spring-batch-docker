package com0.miu.edu.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com0.miu.edu.dto.RoleDTO;
import com0.miu.edu.dto.UserDTO;
import com0.miu.edu.dto.Users;
import com0.miu.edu.model.Role;
import com0.miu.edu.model.User;
import com0.miu.edu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;


@RestController
@RequestMapping("/api")
public class UserControler {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<?> getUsers() {
        return new ResponseEntity<Users>(userService.getUsers(), HttpStatus.OK);
    }

    @PostMapping ("/users")
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        return new ResponseEntity<UserDTO>(userService.saveUser(user), HttpStatus.CREATED);
    }

    @PostMapping ("/roles")
    public ResponseEntity<?> saveRole(@RequestBody Role role) {
        return new ResponseEntity<RoleDTO>(userService.saveRole(role), HttpStatus.CREATED);
    }

    @PostMapping ("/add-roles")
    public ResponseEntity<?> addRoleToUser(@RequestParam("username") String userName,
                                           @RequestParam("rolename") String roleName) {
        userService.addRoleToUser(userName, roleName);
        return new ResponseEntity<>( HttpStatus.CREATED);
    }

    @DeleteMapping("/users")
    public ResponseEntity<?> removeUser(@RequestParam("username") String userName) {
        userService.removeUser(userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping ("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                UserDTO user = userService.getUser(username);
                String access_token = JWT.create()
                        .withSubject(user.getUserName())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            }catch (Exception exception) {
                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                //response.sendError(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }

}
