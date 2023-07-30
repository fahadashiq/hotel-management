package com.trivago.hotelmanagement.model.criteria;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Criteria {
    @Schema(description = "Name of the field. For inner fields use . like location.city", example = "name")
    private String key;
    @Schema(description = "4 supported operations: < > = :", example = "=")
    private String operation;
    @Schema(description = "Value we need to match against", example = "Accommodation name")
    private Object value;
}
