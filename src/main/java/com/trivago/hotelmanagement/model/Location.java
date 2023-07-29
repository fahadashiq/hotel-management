package com.trivago.hotelmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String city;
    private String state;
    private String country;
    @Pattern(regexp = "^[0-9]*$", message = "Only numbers are allowed in zipcode.")
    @Length(min = 5, max = 5, message = "Zip code should be of length 5")
    private String zipCode;
    private String address;
}
