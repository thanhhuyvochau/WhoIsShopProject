package org.project.usermanagement.repository;

import org.project.usermanagement.entity.Address;
import org.project.usermanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
    Set<Address> findAllByUser_UserName(String username);
}
