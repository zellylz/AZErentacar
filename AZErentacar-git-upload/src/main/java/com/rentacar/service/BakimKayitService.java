package com.rentacar.service;

import com.rentacar.dao.BakimKayitDAO;
import com.rentacar.entity.BakimKayit;

import java.util.List;

public class BakimKayitService {

    private BakimKayitDAO bakimDAO = new BakimKayitDAO();

    public void bakimEkle(BakimKayit b) {
        bakimDAO.ekle(b);
    }

    public BakimKayit bakimBul(Long id) {
        return bakimDAO.idIleBul(id);
    }

    public List<BakimKayit> bakimlariListele() {
        return bakimDAO.tumunuListele();
    }

    public void bakimGuncelle(BakimKayit b) {
        bakimDAO.guncelle(b);
    }

    public void bakimSil(Long id) {
        bakimDAO.sil(id);
    }
}
