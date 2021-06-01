package com.ecomcph.inc.Models;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
// Denne klasse nedarver funktioner fra springframework repository.
// Denne funktion er smart, da det gør det muligt for os, at få fat på brugeren via email fra vores repository.
public interface UserRepository extends JpaRepository<User, Long>{
    User findByEmail(String email);
}
