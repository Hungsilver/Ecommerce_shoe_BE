package com.example.projectshop.repository;

import com.example.projectshop.domain.Chucvu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChucVuRepository extends JpaRepository<Chucvu, Integer> {
}
