package com.rentacar.bean;

import com.rentacar.entity.BakimKayit;
import com.rentacar.service.BakimKayitService;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

import java.util.List;

@Named("bakimKayitBean")
@RequestScoped
public class BakimKayitBean {

    private BakimKayitService bakimService = new BakimKayitService();

    public List<BakimKayit> getBakimKayitlari() {
        return bakimService.bakimlariListele();
    }
}
