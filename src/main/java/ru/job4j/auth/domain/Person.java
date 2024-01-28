package ru.job4j.auth.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.job4j.auth.handlers.Operation;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @NotNull(message = "Id must be not null", groups = Operation.OnUpdate.class)
    @Min(value = 1, message = "Id must be more than 0")
    private Integer id;

    @NotBlank(message = "Login must be not empty", groups = Operation.OnCreate.class)
    @Email
    private String login;

    @NotBlank(message = "Password must be not empty", groups = {
            Operation.OnCreate.class, Operation.OnUpdate.class
    })
    @Pattern(regexp = ".{6,}", message = "Invalid password. Password length must be more than 5 characters")
    private String password;
}