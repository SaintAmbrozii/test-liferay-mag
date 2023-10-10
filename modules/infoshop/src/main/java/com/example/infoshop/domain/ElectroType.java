package com.example.infoshop.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@ToString
@Entity(name = "electroType")
@Table(name = "electroType")
@AllArgsConstructor
@NoArgsConstructor
public class ElectroType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "не должен быть пустым")
    @Size(min = 1, max = 100, message = "Не может быть больше 100")
    @Column(name = "name", length = 100, nullable = false)
    private String name;
}
