package com.kisanbasket.freshatta.entity.auth;

import com.kisanbasket.freshatta.enumtype.AddressType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String addressLine1;
    private String addressLine2;
    private String landmark;
    private String state;
    private String city;
    private String pincode;
    @Enumerated(EnumType.STRING)
    private AddressType addressType;

    @ManyToOne
    @JoinColumn
    private UserAuthEntity userAuthEntity;
}
