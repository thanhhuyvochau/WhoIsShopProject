package org.project.usermanagement.repository;

import org.project.usermanagement.entity.User;
import org.project.usermanagement.entity.Verification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VerificationRepository extends JpaRepository<Verification, UUID> {
}
