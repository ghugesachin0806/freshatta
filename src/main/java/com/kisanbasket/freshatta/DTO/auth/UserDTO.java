package com.kisanbasket.freshatta.DTO.auth;

import com.kisanbasket.freshatta.entity.auth.Address;
import com.kisanbasket.freshatta.entity.auth.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String fullName;
    private String email;
    private String mobileNumber;
    private String password;
    private List<UserRoleDTO> userRoleList = new ArrayList<>();
    private List<AddressDTO> addressList = new ArrayList<>();
    private Boolean isVerified;
    private String loginOtp;
}