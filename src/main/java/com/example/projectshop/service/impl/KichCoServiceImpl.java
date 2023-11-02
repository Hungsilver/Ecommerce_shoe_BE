package com.example.projectshop.service.impl;

import com.example.projectshop.domain.KichCo;
import com.example.projectshop.domain.Xuatxu;
import com.example.projectshop.dto.kichco.KichCoRequest;
import com.example.projectshop.dto.kichco.KichCoResponse;
import com.example.projectshop.repository.ChiTietSanPhamRepository;
import com.example.projectshop.repository.KichCoRepository;
import com.example.projectshop.service.IKichCoService;
import com.example.projectshop.service.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KichCoServiceImpl implements IKichCoService {
    @Autowired
    private KichCoRepository kichCoRepository;


    @Override
    public Page<KichCo> getAll(Pageable pageable) {
        return kichCoRepository.findAll(pageable);
    }

    @Override
    public Optional<KichCo> findById(Integer id) {
        return kichCoRepository.findById(id);
    }

    @Override
    public Page<KichCo> findAllByName(String size, Pageable pageable) {

        return kichCoRepository.findAllBySize("%" +size+"%",pageable);
    }

    @Override
    public KichCo create(KichCo kc) {
        return kichCoRepository.save(kc);
    }

    @Override
    public KichCo update(KichCo kc, Integer id) {
        kc.setId(id);
        return kichCoRepository.save(kc);
    }

    @Override
    public KichCo delete(Integer id) {
        Optional<KichCo> xx = kichCoRepository.findById(id);
        if (xx.isPresent()) {
            xx.get().setTrangThai(0);
            return kichCoRepository.save(xx.get());
        }
        return null;
    }
}
