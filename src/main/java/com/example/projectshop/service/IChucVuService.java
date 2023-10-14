package com.example.projectshop.service;

import java.util.List;

public interface IChucVuService {
    List<Chucvu> getAll();

    Chucvu add(Chucvu cv);

    Chucvu update(Chucvu cv);

    void delete(Integer id);
}
