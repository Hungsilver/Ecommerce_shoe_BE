package com.example.projectshop.repository;

import com.example.projectshop.domain.Kichco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KichCoRepository extends JpaRepository<Kichco, Integer> {
}
