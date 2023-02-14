package com.binary.city.assignmenttest.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonPropertyOrder({"name","surname","email"})
public class ContactRequest {

    @NonNull
    @JsonProperty("name")
    private String name;


    @NonNull
    @JsonProperty("surname")
    private String surname;


    @NonNull
    @Email
    @JsonProperty("email")
    private String email;
}
