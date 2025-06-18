package com.kisanbasket.freshatta.entity.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "Images")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @org.hibernate.annotations.ColumnDefault("false")
    private Boolean hasImage = false;

    @Lob
    @JdbcTypeCode(SqlTypes.BINARY)
    @Basic(fetch = FetchType.LAZY)
    private byte[] imageData;

    private String imageType;
    private String imageName;
    private Long imageSize;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private ProductEntity productEntity;
}
