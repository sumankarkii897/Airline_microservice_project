package com.himalayan.controller;

import com.himalayan.enums.UserRole;
import com.himalayan.payload.request.RoleUpdateRequest;
import com.himalayan.payload.response.ApiResponse;
import com.himalayan.payload.response.PageResponse;
import com.himalayan.payload.response.UserResponse;
import com.himalayan.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('ADMIN','AIRLINE_OWNER')")
    public ResponseEntity<ApiResponse<PageResponse<UserResponse>>> getAllUsers(@PageableDefault(size = 10, page = 0, sort = "fullName", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.<PageResponse<UserResponse>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .timestamp(new Date())
                        .message("Users fetched Successfully.")
                        .data(userService.getUsers(pageable))
                        .build()

        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Long id){
        return ResponseEntity.ok(
                ApiResponse.<UserResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .timestamp(new Date())
                        .message("User with id " + id)
                        .data(userService.getUserById(id))
                        .build()
        );
    }

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<UserResponse>> getUserProfile(Authentication authentication ){
        String email = authentication.getName();
        return ResponseEntity.ok(
                ApiResponse.<UserResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .timestamp(new Date())
                        .message("User fetched successfully. ")
                        .data(userService.getUserByEmail(email))
                        .build()
        );
    }
    @PatchMapping("/{id}/role")
    public ResponseEntity<ApiResponse<UserResponse>> changeRole(@PathVariable Long id, @RequestBody RoleUpdateRequest request){

        return ResponseEntity.ok(
                ApiResponse.<UserResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .timestamp(new Date())
                        .message("Role updated successfully.")
                        .data(userService.changeRole(id, request.getRole()))
                        .build()
        );
    }
}
