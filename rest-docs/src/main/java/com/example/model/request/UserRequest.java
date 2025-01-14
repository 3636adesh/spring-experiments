package com.example.model.request;

import com.example.entities.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record UserRequest(
        @NotBlank(message = "FirstName can't be blank") String firstName,
        String lastName,
        @Positive(message = "Age must be greater than 0") Integer age,
        Gender gender,
        String email,
        String phoneNumber) {

    public UserRequest(String firstName, String lastName, Integer age, Gender gender,  String phoneNumber) {
        this(firstName, lastName, age, gender, firstName+"."+lastName,phoneNumber);
    }
}
