
package com.todaywhat.auth.controller;

import com.todaywhat.auth.dto.LoginRequest;
import com.todaywhat.auth.dto.SignUpRequest;
import com.todaywhat.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService userService;
    private final AuthService authService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {

        userService.signUp(signUpRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("회원가입 성공");
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest loginRequest) {
         authService.login(loginRequest);
         return ResponseEntity.ok("로그인 성공");
    }
}
