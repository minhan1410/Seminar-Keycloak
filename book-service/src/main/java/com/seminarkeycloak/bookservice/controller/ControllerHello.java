package com.seminarkeycloak.bookservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ControllerHello {
    @GetMapping("/hello")
    @Operation(summary = "Không cần login cũng truy cập được")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello");
    }

    @GetMapping("/admin")
    @Operation(summary = "Chỉ có role Admin với Supper Admin mới truy cập được")
    public ResponseEntity<String> sayHelloToAdmin() {
        return ResponseEntity.ok("Hello Admin");
    }

    @GetMapping("/user")
    @Operation(summary = "Sau khi login mới truy cập được")
    public ResponseEntity<String> sayHelloToUser() {
        return ResponseEntity.ok("Hello User");
    }
}
