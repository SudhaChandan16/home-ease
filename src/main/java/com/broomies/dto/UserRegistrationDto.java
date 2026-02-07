package com.broomies.dto;

import com.broomies.enums.ProviderCategory;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserRegistrationDto {

    @NotEmpty(message = "Name is required")
    private String name;

    @NotEmpty(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotEmpty(message = "Password is required")
    private String password;

    @NotEmpty(message = "Mobile number is required")
    private String mobile;

    private String address;

    // Optional fields for Provider registration
    private boolean isProvider;
    private ProviderCategory category;
    private String bio;
    private Double hourlyRate;
    private Integer experienceYears;
}
