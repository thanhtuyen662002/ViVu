package com.vothanhtuyen.vivu_backend.entities;

import java.sql.Timestamp;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Locations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String region;
    private String country;
    private String imageUrl;
    private Timestamp created_at;
    private String nameNormalized;

    @OneToMany(mappedBy = "locations")
    private Set<Hotels> hotels;

    @OneToMany(mappedBy = "locations")
    private Set<Places> places;

    @OneToMany(mappedBy = "locations")
    private Set<LocalFoods> localFoods;

    @OneToMany(mappedBy = "locations")
    private Set<Eatery> eatery;
}
