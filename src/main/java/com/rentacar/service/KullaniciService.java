package com.rentacar.service;

import com.rentacar.dao.KullaniciDAO;
import com.rentacar.entity.Kullanici;

import java.util.List;

public class KullaniciService {

    private KullaniciDAO kullaniciDAO = new KullaniciDAO();

    public void kullaniciEkle(Kullanici kullanici) {
        kullaniciDAO.ekle(kullanici);
    }

    public Kullanici kullaniciBul(Long id) {
        return kullaniciDAO.idIleBul(id);
    }

    public List<Kullanici> kullanicilariListele() {
        return kullaniciDAO.tumunuListele();
    }

    public void kullaniciGuncelle(Kullanici kullanici) {
        kullaniciDAO.guncelle(kullanici);
    }

    public void kullaniciSil(Long id) {
        kullaniciDAO.sil(id);
    }
    public Kullanici girisYap(String email, String sifre) {
        return kullaniciDAO.emailVeSifreIleBul(email, sifre);
    }
}
