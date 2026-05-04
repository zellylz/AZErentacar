package com.rentacar.service;

import com.rentacar.dao.AracDAO;
import com.rentacar.entity.Arac;

import java.util.List;

public class AracService {

    private AracDAO aracDAO = new AracDAO();

    public void aracEkle(Arac arac) {
        aracDAO.ekle(arac);
    }

    public Arac aracBul(Long id) {
        return aracDAO.idIleBul(id);
    }

    public List<Arac> araclariListele() {
        return aracDAO.tumunuListele();
    }

    public void aracGuncelle(Arac arac) {
        aracDAO.guncelle(arac);
    }

    public void aracSil(Long id) {
        aracDAO.sil(id);
    }
}
