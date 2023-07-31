package com.trivago.hotelmanagement.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.trivago.hotelmanagement.config.multitenancy.TenantEntity;
import com.trivago.hotelmanagement.config.validation.ItemNameConstraint;
import com.trivago.hotelmanagement.model.enumeration.ItemCategory;
import com.trivago.hotelmanagement.model.enumeration.ReputationBadge;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Filter;
import org.hibernate.validator.constraints.URL;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "item")
public class Item extends TenantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @ItemNameConstraint
    @Schema(description = "Name of the accommodation with length of atleast 5 characters. Restricted words: Free,Offer,Book,Website", example = "Accommodation name")
    private String name;
    @Min(0)
    @Max(5)
    @Digits(integer = 1, fraction = 0, message = "Decimal points not allowed")
    private BigDecimal rating;
    @Schema(enumAsRef = true)
    @Enumerated(EnumType.STRING)
    private ItemCategory category;
    @OneToOne(cascade = CascadeType.ALL)
    @Valid
    private Location location;
    @URL(message = "Image must have a valid URL")
    @Schema(description = "A valid image url.", example = "https://image.com/image.jpg")
    private String image;
    @Min(0)
    @Max(1000)
    @Digits(integer = 4, fraction = 0, message = "Decimal points not allowed")
    private BigDecimal reputation;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(enumAsRef = true)
    @Column(name = "reputationBadge")
    @Enumerated(EnumType.STRING)
    private ReputationBadge reputationBadge;
    private Integer price;
    private Integer availability;
}
