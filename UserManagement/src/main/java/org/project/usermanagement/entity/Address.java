package org.project.usermanagement.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String addressLine;
    private String city;
    private String stateProvince;
    private String postalCode;
    private String countryCode;
    @ManyToOne
    private User user;
}
