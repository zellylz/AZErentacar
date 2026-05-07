package com.rentacar.bean;

import com.rentacar.entity.Odeme;
import com.rentacar.service.OdemeService;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

import java.util.List;

@Named("odemeBean")
@RequestScoped
public class OdemeBean {

    private OdemeService odemeService = new OdemeService();

    public List<Odeme> getOdemeler() {
        return odemeService.odemeleriListele();
    }
}
