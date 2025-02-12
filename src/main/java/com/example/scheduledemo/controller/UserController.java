package com.example.scheduledemo.controller;

import com.example.scheduledemo.entity.User;
import com.example.scheduledemo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<User> signUp(@RequestBody User user) {
        return ResponseEntity.ok(userService.signUp(user));
    }

    //    @GetMapping("/{email}")
//    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
//        return ResponseEntity.ok(userService.findByEmail(email));
//    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user, HttpServletRequest request, HttpServletResponse response) {
        boolean isAuthenticated = userService.login(user.getEmail(), user.getPassword(), request, response);
        if (isAuthenticated) {
            return ResponseEntity.ok().body("{\"message\": \"로그인 성공\"}");
        } else {
            return ResponseEntity.status(401).body("{\"message\": \"이메일 또는 비밀번호가 잘못되었습니다.\"}");
        }
    }

}
