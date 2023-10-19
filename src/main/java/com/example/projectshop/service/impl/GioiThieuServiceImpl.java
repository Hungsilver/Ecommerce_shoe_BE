package com.example.projectshop.service.impl;

import com.example.projectshop.domain.GioiThieu;
import com.example.projectshop.domain.Thuonghieu;
import com.example.projectshop.dto.gioithieu.GioiThieuReponse;
import com.example.projectshop.dto.gioithieu.GioiThieuRequest;
import com.example.projectshop.dto.thuonghieu.ThuongHieuResponse;
import com.example.projectshop.repository.GioiThieuRepository;
import com.example.projectshop.service.IGioiThieuService;
import com.example.projectshop.service.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GioiThieuServiceImpl implements IGioiThieuService {

    @Autowired
    private GioiThieuRepository gioiThieuRepository;

    @Override
    public List<GioiThieuReponse> getall() {
        List<GioiThieu> gioiThieus = gioiThieuRepository.findAll();

        List<GioiThieuReponse> listreponse = ObjectMapperUtils.mapAll(gioiThieus, GioiThieuReponse.class);

        return listreponse;
    }

    @Override
    public GioiThieuReponse create(GioiThieuRequest gioiThieuRequest) {
        GioiThieu gth = ObjectMapperUtils.map(gioiThieuRequest, GioiThieu.class);
        gth.setTenGioiThieu(gioiThieuRequest.getTenGioiThieu());
        gth.setNoiDung(gioiThieuRequest.getNoiDung());
        gth.setLogo(gioiThieuRequest.getLogo());
        gth.setBanner(gioiThieuRequest.getBanner());
        gth.setMoTa(gioiThieuRequest.getMoTa());
        gth.setNgayTao(gioiThieuRequest.getNgayTao());
        gth.setNgayXoa(gioiThieuRequest.getNgayXoa());
        gth.setTrangThai(gioiThieuRequest.getTrangThai());
        gioiThieuRepository.save(gth);
        GioiThieuReponse reponse = ObjectMapperUtils.map(gth, GioiThieuReponse.class);
        return reponse;
    }

    @Override
    public GioiThieuReponse update(GioiThieuRequest gioiThieuRequestupdate) {
        GioiThieu gth1 = ObjectMapperUtils.map(gioiThieuRequestupdate, GioiThieu.class);
        gth1.setTenGioiThieu(gioiThieuRequestupdate.getTenGioiThieu());
        gth1.setNoiDung(gioiThieuRequestupdate.getNoiDung());
        gth1.setLogo(gioiThieuRequestupdate.getLogo());
        gth1.setBanner(gioiThieuRequestupdate.getBanner());
        gth1.setMoTa(gioiThieuRequestupdate.getMoTa());
        gth1.setNgayTao(gioiThieuRequestupdate.getNgayTao());
        gth1.setNgayXoa(gioiThieuRequestupdate.getNgayXoa());
        gth1.setTrangThai(gioiThieuRequestupdate.getTrangThai());

        gth1.setId(gioiThieuRequestupdate.getId());
        gioiThieuRepository.save(gth1);
        GioiThieuReponse reponse = ObjectMapperUtils.map(gth1, GioiThieuReponse.class);
        return reponse;
    }

    @Override
    public GioiThieuReponse detail(Integer id) {
        GioiThieu gthchitiet = gioiThieuRepository.findById(id).orElse(null);
        GioiThieuReponse response = ObjectMapperUtils.map(gthchitiet, GioiThieuReponse.class);
        return response;
    }

    @Override
    public void delete(Integer id) {
        gioiThieuRepository.deleteById(id);

    }
}
