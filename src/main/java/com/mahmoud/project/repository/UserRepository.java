package com.mahmoud.project.repository;

import com.mahmoud.project.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Override
    @Query("""
            SELECT u FROM User u
            LEFT JOIN FETCH u.profile
            LEFT JOIN FETCH u.addresses
            WHERE u.id = :id""")
    List<User> findAll();

    @Query("""
            SELECT u FROM User u
            LEFT JOIN FETCH u.profile
            LEFT JOIN FETCH u.addresses
            WHERE u.id = :id""")
    Optional<User> findByIdWithDetails(@Param("id") Long id);

}
