package com.daktilo.daktilo_backend.repository;

import com.daktilo.daktilo_backend.constants.Role;
import com.daktilo.daktilo_backend.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    public Page<User> findAll(Pageable pageRequest);

    public Optional<User> findByUsername(String username);

    public Page<User> findAllByRole(Pageable pageRequest, Role role);

    @Query("""
            SELECT u FROM User u
            WHERE u.email = :email
            OR
            u.username = :username
            """)
    public List<User> findByEmailOrUsername(@Param("email") String email, @Param("username") String username);

    Optional<User> findByEmail(String email);
}
