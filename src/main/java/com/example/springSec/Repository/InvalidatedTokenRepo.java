package com.example.springSec.Repository;

import com.example.springSec.Entity.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvalidatedTokenRepo extends JpaRepository<InvalidatedToken, String> {
}
