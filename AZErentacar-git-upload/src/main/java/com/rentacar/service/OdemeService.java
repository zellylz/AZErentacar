package com.rentacar.service;

import com.rentacar.dao.OdemeDAO;
import com.rentacar.entity.Odeme;

import java.util.List;

public class OdemeService {

    private OdemeDAO odemeDAO = new OdemeDAO();

    public void odemeEkle(Odeme odeme) {
        odemeDAO.ekle(odeme);
    }

    public Odeme odemeBul(Long id) {
        return odemeDAO.idIleBul(id);
    }

    public List<Odeme> odemeleriListele() {
        return odemeDAO.tumunuListele();
    }

    public void odemeGuncelle(Odeme odeme) {
        odemeDAO.guncelle(odeme);
    }

    public void odemeSil(Long id) {
        odemeDAO.sil(id);
    }
}
