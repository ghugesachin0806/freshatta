package com.kisanbasket.freshatta.DTO.auth;

import com.kisanbasket.freshatta.enumtype.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleDTO {
    private UserType userType;
}
