package ru.job4j.auth.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class UserDto {
    @NotNull(message = "Id must be non null")
    private Integer id;

    @NotBlank(message = "Password must be not empty")
    @Pattern(regexp = ".{6,}", message = "Invalid password. Password length must be more than 5 characters.")
    private String password;
}
