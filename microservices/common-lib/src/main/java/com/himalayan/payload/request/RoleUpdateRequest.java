package com.himalayan.payload.request;

import com.himalayan.enums.UserRole;
import lombok.Data;

@Data
public class RoleUpdateRequest {
    private UserRole role;
}
