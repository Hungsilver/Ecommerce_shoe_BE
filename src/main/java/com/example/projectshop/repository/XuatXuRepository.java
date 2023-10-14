package com.example.projectshop.repository;

import com.example.projectshop.domain.Xuatxu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface XuatXuRepository extends JpaRepository<Xuatxu, Integer> {
}
