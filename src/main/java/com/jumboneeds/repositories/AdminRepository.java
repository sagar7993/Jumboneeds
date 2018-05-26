package com.jumboneeds.repositories;

import com.jumboneeds.entities.Admin;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AdminRepository extends CrudRepository<Admin, String> {

    @Transactional
    @Query(value = "SELECT a FROM Admin a WHERE a.email = :email AND a.password = :password")
    Admin login(@Param("email") String email, @Param("password") String password);

}