package com.kisanbasket.freshatta.DTO.auth;

import com.kisanbasket.freshatta.enumtype.AddressType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    private String addressLine1;
    private String addressLine2;
    private String landmark;
    private String state;
    private String city;
    private String pincode;
    private AddressType addressType;
}