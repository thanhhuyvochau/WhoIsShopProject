package org.project.usermanagement.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@Table(name = "verifications")
@EntityListeners(AuditingEntityListener.class) // Enable auditing for this entity
public class Verification {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Instant expired;
    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;
    @LastModifiedDate
    private Instant modifiedAt;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User verifiedUser;
    private Boolean used;
}
