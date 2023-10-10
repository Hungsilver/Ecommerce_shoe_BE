package com.example.projectshop.repository;

import com.example.projectshop.domain.Chatlieudegiay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatLieuDeGiayRepository extends JpaRepository<Chatlieudegiay, Integer> {
}
