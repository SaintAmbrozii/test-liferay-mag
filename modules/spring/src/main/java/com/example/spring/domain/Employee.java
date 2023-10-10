package com.example.spring.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.type.NumericBooleanConverter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Table(name = "employee")
@Entity(name = "employee")
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;


    @NotNull(message = "не должен быть пустым")
    @Size(min = 1, max = 100, message = "Не может быть больше 100")
    @Column(name = "firstname", length = 100, nullable = false)
    private String firstname;
    @NotNull(message = "не должен быть пустым")
    @Size(min = 1, max = 100, message = "Не может быть больше 100")
    @Column(name = "lastname", length = 100, nullable = false)
    private String lastname;
    @NotNull(message = "не должен быть пустым")
    @Size(min = 1, max = 100, message = "Не может быть больше 100")
    @Column(name = "patronymic", length = 100, nullable = false)
    private String patronymic;
    @JsonFormat(timezone = "dd.MM.yyyy")
    @NotNull(message = "не должен быть пустым")
    @Column(name = "birhtdate", nullable = false)
    private LocalDate birthdate;

    @NotNull(message = "не должен быть пустым")
    @Convert(converter = NumericBooleanConverter.class)
    @Column(name = "gender", nullable = false)
    private Boolean gender;

    @NotNull(message = "не должен быть пустым")
    @Column(name = "position", nullable = false)
    private Long position;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<ElectroType> electroTypes = new ArrayList<>();

}
