package com.vothanhtuyen.vivu_backend.entities;

import java.security.Timestamp;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "\"user\"")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;
    private String fullname;
    @Lob
    private byte[] avatar;
    private Timestamp created_at;
    private Timestamp updated_at;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

    @OneToMany(mappedBy = "user")
    private Set<UserHotels> userHotels;

    @OneToMany(mappedBy = "user")
    private Set<UserFoods> userFoods;

    @OneToMany(mappedBy = "user")
    private Set<UserPlaces> userPlaces;

    @OneToMany(mappedBy = "user")
    private Set<SuggestCalendar> suggestCalendars;
}
