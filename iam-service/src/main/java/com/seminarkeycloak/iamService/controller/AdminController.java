package com.seminarkeycloak.iamService.controller;

import com.seminarkeycloak.iamService.dto.Role;
import com.seminarkeycloak.iamService.dto.RoleSearch;
import com.seminarkeycloak.iamService.dto.User;
import com.seminarkeycloak.iamService.dto.UserSearch;
import com.seminarkeycloak.iamService.service.RoleService;
import com.seminarkeycloak.iamService.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.MappingsRepresentation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@Tag(name = "Admin Controller", description = "Chỉ có role Admin với Supper Admin mới truy cập được")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @GetMapping("/user")
    @Operation(summary = "Search User", description = "Tiếm kiếm user")
    public ResponseEntity<List<User>> search(UserSearch search) {
        return ResponseEntity.ok(userService.search(search));
    }

    @PostMapping("/user")
    @Operation(summary = "Create User", description = "Tạo mới user với role mặc định default-roles-seminar(bao gồm User Role,...)")
    public ResponseEntity<User> create(User user) {
        return ResponseEntity.ok(userService.create(user));
    }

    @DeleteMapping("/user/{id}")
    @Operation(summary = "Delete User", description = "Xoá user")
    public void delete(@PathVariable String id) {
        userService.delete(id);
    }

    @GetMapping(value = "/user/{userId}/roles")
    @Operation(summary = "Get Roles User", description = "Lấy danh sách các role của user")
    public ResponseEntity<MappingsRepresentation> getRolesUser(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(userService.getRolesUser(userId));
    }

    @GetMapping("/role")
    @Operation(summary = "Search Role", description = "Tiếm kiếm role")
    public ResponseEntity<List<Role>> search(RoleSearch search) {
        return ResponseEntity.ok(roleService.search(search));
    }

    @PostMapping("/role")
    @Operation(summary = "Create role", description = "Tạo mới role")
    public ResponseEntity<Role> create(Role role) {
        return ResponseEntity.ok(roleService.create(role));
    }

    @PutMapping("/role")
    @Operation(summary = "Update Role", description = "Cập nhập role")
    public ResponseEntity<Role> update(Role role) {
        return ResponseEntity.ok(roleService.update(role));
    }

    @DeleteMapping("/role/{roleName}/{isClient}")
    @Operation(summary = "Delete Role", description = "Xoá role")
    public void delete(@PathVariable String roleName, @PathVariable Boolean isClient) {
        roleService.delete(roleName, isClient);
    }

    @PostMapping(value = "/role/users/{id}/roles/{roleId}")
    @Operation(summary = "Add Role User", description = "Thêm role cho user")
    public ResponseEntity<MappingsRepresentation> addRoleUser(@PathVariable("id") String id, @PathVariable("roleId") String roleId) {
        return ResponseEntity.ok(roleService.assignRoleToUser(id, roleId));
    }

    @PostMapping(value = "/role/users/{id}/rm-roles/{roleId}")
    @Operation(summary = "Remove Role From User", description = "Xoá role cho user")
    public ResponseEntity<MappingsRepresentation> removeRoleFromUser(@PathVariable("id") String id, @PathVariable("roleId") String roleId) {
        return ResponseEntity.ok(roleService.removeRoleFromUser(id, roleId));
    }
}
