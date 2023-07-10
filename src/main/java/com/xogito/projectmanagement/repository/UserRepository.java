package com.xogito.projectmanagement.repository;

import com.xogito.projectmanagement.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findUserByNameAndEmailIgnoreCase(String name, String email, Pageable pageable);
    Optional<User> findUserByEmailIgnoreCase(String email);

}
