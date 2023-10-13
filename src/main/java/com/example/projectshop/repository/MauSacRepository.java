package com.example.projectshop.repository;

import com.example.projectshop.domain.Mausac;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MauSacRepository extends JpaRepository<Mausac,Integer> {

}
