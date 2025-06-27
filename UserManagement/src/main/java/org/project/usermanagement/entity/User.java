package org.project.usermanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.project.usermanagement.constant.Role;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.Set;

@Data
@Entity
@Table(name = "user")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class) // Enable auditing for this entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String userName;
    @Column(unique = true)
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String phone;
    private Boolean emailVerified = false;
    private Instant emailVerifiedAt;
    private Boolean phoneVerified = false;
    private Instant phoneVerifiedAt;
    private Boolean enabled = false; // Active when Email verified or Phone verified
    private Boolean locked = false;
    private Boolean isShopOwner = false;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Address> addresses;
    @OneToMany(mappedBy = "verifiedUser", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Verification> verifications;
}
