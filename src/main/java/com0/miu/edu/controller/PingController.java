package com0.miu.edu.controller;


import com0.miu.edu.dto.Users;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class PingController {
    @GetMapping("ping")
    public String ping() {
        return "v0.1";
    }

}
