package com.trivago.hotelmanagement.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    private String city;
    private String state;
    private String country;
    @Pattern(regexp = "^[0-9]*$", message = "Only numbers are allowed in zipcode.")
    @Length(min = 5, max = 5, message = "Zip code should be of length 5")
    @Schema(description = "A zip code with a length of 5 integers", example = "12345")
    private String zipCode;
    private String address;
}
