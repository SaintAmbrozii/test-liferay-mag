package com.example.infoshop.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity(name = "purchase")
@Table(name = "purchase")
@AllArgsConstructor
@NoArgsConstructor
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "не должен быть пустым")
    @Column(name = "electroId", nullable = false)
    private Long electroId;

    @NotNull(message = "не должен быть пустым")
    @Column(name = "employeeId", nullable = false)
    private Long employeeId;

    @NotNull(message = "не должен быть пустым")
    @JsonFormat(timezone = "dd.MM.yyyy HH:mm")
    @Column(name = "purchaseDate", nullable = false)
    private LocalDateTime purchaseDate;

    @NotNull(message = "не должен быть пустым")
    @Column(name = "type", nullable = false)
    private Long type;
}
