package com.kisanbasket.freshatta.entity.auth;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "registered_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    @Column(unique = true)
    private String email;

    @Column(unique = true, nullable = false)
    private String mobileNumber;

    private String password;

    @OneToMany(mappedBy = "userAuthEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserRole> userRoleList = new ArrayList<>();

    @OneToMany(mappedBy = "userAuthEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addressList = new ArrayList<>();

    private Boolean isVerified;

    private String loginOtp;

    private int attemptsToVerify = 0;

    private Date loginAttemptTimeStamp;

    private Boolean hasDeleted;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userRoleList.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getUserType().name()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return loginOtp;
    }

    @Override
    public String getUsername() {
        return mobileNumber;
    }
}