package com.rentacar.bean;

import com.rentacar.entity.KiralamaIslemi;
import com.rentacar.service.KiralamaIslemiService;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

import java.util.List;

@Named("kiralamaIslemiBean")
@RequestScoped
public class KiralamaIslemiBean {

    private KiralamaIslemiService kiralamaService = new KiralamaIslemiService();

    public List<KiralamaIslemi> getKiralamalar() {
        return kiralamaService.kiralamalariListele();
    }
}