package com.binary.city.assignmenttest.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonPropertyOrder({"name","code"})
public class ClientRequest {


    @NonNull
    @JsonProperty("code")
    private String code;

    @NonNull
    @JsonProperty("name")
    private String Name;




}
