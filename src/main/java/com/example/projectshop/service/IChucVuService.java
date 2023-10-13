package com.example.projectshop.service;

import com.example.projectshop.domain.Chucvu;

import java.util.List;

public interface IChucVuService {
    List<Chucvu> getAll();

    Chucvu add(Chucvu cv);

    Chucvu update(Chucvu cv);

    void delete(Integer id);
}
