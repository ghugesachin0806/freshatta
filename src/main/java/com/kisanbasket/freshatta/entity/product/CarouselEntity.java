package com.kisanbasket.freshatta.entity.product;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Carousel-images")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarouselEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long orderId;

    private String name;

    private String description;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn
    private ImageEntity imageEntity;
}