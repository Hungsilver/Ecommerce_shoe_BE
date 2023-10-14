package com.example.projectshop.repository;

import com.example.projectshop.domain.Nguoidung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NguoiDungRepository extends JpaRepository<Nguoidung,Integer> {
}
