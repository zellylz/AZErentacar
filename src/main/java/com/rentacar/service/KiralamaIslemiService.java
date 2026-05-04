package com.rentacar.service;

import com.rentacar.dao.KiralamaIslemiDAO;
import com.rentacar.entity.KiralamaIslemi;

import java.util.List;

public class KiralamaIslemiService {

    private KiralamaIslemiDAO kiralamaDAO = new KiralamaIslemiDAO();

    public void kiralamaEkle(KiralamaIslemi k) {
        kiralamaDAO.ekle(k);
    }

    public KiralamaIslemi kiralamaBul(Long id) {
        return kiralamaDAO.idIleBul(id);
    }

    public List<KiralamaIslemi> kiralamalariListele() {
        return kiralamaDAO.tumunuListele();
    }

    public void kiralamaGuncelle(KiralamaIslemi k) {
        kiralamaDAO.guncelle(k);
    }

    public void kiralamaSil(Long id) {
        kiralamaDAO.sil(id);
    }
}
