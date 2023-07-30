package com.trivago.hotelmanagement.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Error {
    private String errorMessage;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String field;
}
