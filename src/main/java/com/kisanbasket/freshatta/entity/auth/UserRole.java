package com.kisanbasket.freshatta.entity.auth;


import com.kisanbasket.freshatta.enumtype.UserType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_role")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @ManyToOne
    @JoinColumn
    private UserAuthEntity userAuthEntity;
}
