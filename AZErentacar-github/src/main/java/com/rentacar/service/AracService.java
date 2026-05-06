package com.rentacar.service;

import com.rentacar.dao.AracDAO;
import com.rentacar.entity.Arac;
import com.rentacar.exception.NotFoundException;
import com.rentacar.exception.ValidationException;

import java.util.List;

public class AracService {

    private AracDAO aracDAO = new AracDAO();

    public void aracEkle(Arac arac) {

        if (arac.getMarka()==null || arac.getMarka().isBlank()){
            throw new ValidationException("Arac markasi bos olamaz.");
        }
        if (arac.getModel() == null || arac.getModel().isBlank()) {
            throw new ValidationException("Arac modeli boş olamaz.");
        }

        if (arac.getPlaka() == null || arac.getPlaka().isBlank()) {
            throw new ValidationException("Araç plakası boş olamaz.");
        }

        if (arac.getGunlukUcret() == null) {
            throw new ValidationException("Günlük ücret boş olamaz.");
        }

        aracDAO.ekle(arac);
    }

    public Arac aracBul(Long id) {

        Arac arac = aracDAO.idIleBul(id);

        if (arac == null){
            throw new NotFoundException("Arac bulunamadi.");
        }

        return arac;
    }

    public List<Arac> araclariListele() {
        return aracDAO.tumunuListele();
    }

    public int aracSayisi() {
        return aracDAO.aracSayisi();
    }

    public List<Arac> musaitAraclariListele() {
        return aracDAO.musaitAraclariListele();
    }

    public void aracGuncelle(Arac arac) {

        if (arac == null || arac.getId() == null){
            throw new ValidationException("Guncellenecek arac bilgisi gecersiz.");
        }

        aracDAO.guncelle(arac);
    }

    public void aracSil(Long id) {

      Arac arac = aracDAO.idIleBul(id);

      if (arac == null ){
          throw new NotFoundException("Silinecek arac bulunamadi.");
      }

     aracDAO.sil(id);
    }
}
