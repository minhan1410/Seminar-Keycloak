package com.seminarkeycloak.iamService.controller;

import com.seminarkeycloak.iamService.dto.User;
import com.seminarkeycloak.iamService.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Tag(name = "User Controller", description = "Sau khi login mới truy cập được")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PutMapping
    @Operation(summary = "Update User", description = "Cập nhập user")
    public ResponseEntity<User> update(@AuthenticationPrincipal Jwt jwt, User user) {
        return ResponseEntity.ok(userService.update(jwt, user));
    }

    @GetMapping("/me")
    @Operation(summary = "Get Me", description = "Lấy thông tin user đang login")
    public ResponseEntity<User> getCurrentLogin(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(userService.getCurrentLogin(jwt));
    }
}
