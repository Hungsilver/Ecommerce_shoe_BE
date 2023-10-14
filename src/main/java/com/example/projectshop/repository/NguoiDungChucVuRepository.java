package com.example.projectshop.repository;

import com.example.projectshop.domain.Nguoidungchucvu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NguoiDungChucVuRepository extends JpaRepository<Nguoidungchucvu,Integer> {
}
