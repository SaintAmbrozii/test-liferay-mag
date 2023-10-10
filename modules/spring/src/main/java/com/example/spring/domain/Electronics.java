package com.example.spring.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.type.NumericBooleanConverter;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;

@Getter
@Setter
@ToString
@Entity(name = "electronics")
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "electronics")
public class Electronics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 1, max = 100)
    @Column(name = "name", length = 150, nullable = false)
    private String name;

    @Column(name = "etype", nullable = false)
    private Long etype;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "count", nullable = false)
    private Integer count;

    @Column(name = "inStock", nullable = false)
    @Convert(converter = NumericBooleanConverter.class)
    private Boolean inStock;

    @Column(name = "archive", nullable = false)
    @Convert(converter = NumericBooleanConverter.class)
    private Boolean archive;
    @Column(name = "description", length = 5000, nullable = false)
    private String description;


}
