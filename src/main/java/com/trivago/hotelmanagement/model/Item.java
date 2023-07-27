package com.trivago.hotelmanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Integer rating;
    private String category;
    @OneToOne(cascade = CascadeType.ALL)
    private Location location;
    private String image;
    private Integer reputation;
    private ReputationBadge reputationBadge;
    private Integer price;
    private Integer availability;
}
