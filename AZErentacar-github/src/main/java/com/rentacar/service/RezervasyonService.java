package com.rentacar.service;

import com.rentacar.dao.RezervasyonDAO;
import com.rentacar.entity.Rezervasyon;

import java.util.List;

public class RezervasyonService {

    private RezervasyonDAO rezervasyonDAO = new RezervasyonDAO();

    public void rezervasyonEkle(Rezervasyon rezervasyon) {
        rezervasyonDAO.ekle(rezervasyon);
    }

    public Rezervasyon rezervasyonBul(Long id) {
        return rezervasyonDAO.idIleBul(id);
    }

    public List<Rezervasyon> rezervasyonlariListele() {
        return rezervasyonDAO.tumunuListele();
    }

    public void rezervasyonGuncelle(Rezervasyon rezervasyon) {
        rezervasyonDAO.guncelle(rezervasyon);
    }

    public void rezervasyonSil(Long id) {
        rezervasyonDAO.sil(id);
    }
}
