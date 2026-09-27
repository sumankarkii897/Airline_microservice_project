package com.himalayan.service;

import com.himalayan.enums.UserRole;
import com.himalayan.payload.response.PageResponse;
import com.himalayan.payload.response.UserResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface UserService {

UserResponse getUserByEmail( String email);

UserResponse getUserById(Long id);

PageResponse<UserResponse> getUsers(Pageable pageable);

UserResponse changeRole(Long id, UserRole role);


}
