package com.trivago.hotelmanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.trivago.hotelmanagement.config.validation.ItemNameConstraint;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @ItemNameConstraint
    private String name;
    @Min(0)
    @Max(5)
    @Digits(integer = 1, fraction = 0, message = "Decimal points not allowed")
    private BigDecimal rating;
    private ItemCategory category;
    @OneToOne(cascade = CascadeType.ALL)
    @Valid
    private Location location;
    @URL(message = "Image must have a valid URL")
    private String image;
    @Min(0)
    @Max(1000)
    @Digits(integer = 4, fraction = 0, message = "Decimal points not allowed")
    private BigDecimal reputation;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private ReputationBadge reputationBadge;
    private Integer price;
    private Integer availability;
}
