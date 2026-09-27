package com.himalayan.service.impl;

import com.himalayan.entity.User;
import com.himalayan.enums.UserRole;
import com.himalayan.exception.ResourceNotFoundException;
import com.himalayan.mapper.AuthMapper;
import com.himalayan.payload.response.PageResponse;
import com.himalayan.payload.response.UserResponse;
import com.himalayan.repository.UserRepository;
import com.himalayan.service.UserService;
import com.himalayan.util.PageMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AuthMapper authMapper;
    private final PageMapper pageMapper;
    @Override
    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("User not found with email: " + email)
        );
        return authMapper.toUserResponse(user);
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User not found with id: " + id)
        );
        return authMapper.toUserResponse(user);
    }

    @Override
    public PageResponse<UserResponse> getUsers(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        Page<UserResponse> userResponses = users.map(authMapper::toUserResponse);
        return pageMapper.toPageResponse(userResponses);
    }

    @Override
    @Transactional
    public UserResponse changeRole(Long id, UserRole role) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User not found with id: " + id)
        );
        user.setRole(role);
        return authMapper.toUserResponse(user);


    }
}
