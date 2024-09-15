package com.seminarkeycloak.iamService.controller;

import com.seminarkeycloak.iamService.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
@Tag(name = "Public Controller", description = "Không cần login cũng truy cập được")
@RequiredArgsConstructor
public class PublicController {
    private final UserService userService;

    @GetMapping("/forgot-password/{username}")
    @Operation(summary = "Forgot Password", description = "Quên mật khẩu")
    public void forgotPassword(@PathVariable String username) {
        userService.forgotPassword(username);
    }

}
