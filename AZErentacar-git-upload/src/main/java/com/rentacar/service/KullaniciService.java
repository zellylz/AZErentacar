package com.rentacar.service;

import com.rentacar.dao.KullaniciDAO;
import com.rentacar.entity.Kullanici;
import com.rentacar.entity.Rol;
import com.rentacar.exception.ValidationException;
import com.rentacar.exception.NotFoundException;

import java.util.List;

public class KullaniciService {

    private KullaniciDAO kullaniciDAO = new KullaniciDAO();

    public void kullaniciEkle(Kullanici kullanici) {

        if (kullanici.getAdSoyad() == null || kullanici.getAdSoyad().isBlank()) {
            throw new ValidationException("Ad soyad bos olamaz.");
        }

        if (kullanici.getEmail() == null || kullanici.getEmail().isBlank()) {
            throw new ValidationException("Email bos olamaz.");
        }

        if (!kullanici.getEmail().contains("@")) {
            throw new ValidationException("Gecerli bir email giriniz.");
        }

        if (kullanici.getSifre() == null || kullanici.getSifre().isBlank()) {
            throw new ValidationException("Sifre bos olamaz.");
        }

        if (kullanici.getSifre().length() < 4) {
            throw new ValidationException("Sifre en az 4 karakter olmalidir.");
        }

        if (kullaniciDAO.emailIleBul(kullanici.getEmail()) != null) {
            throw new ValidationException("Bu email zaten kayitli.");
        }

        if (kullanici.getRol() == null) {
            kullanici.setRol(Rol.MUSTERI);
        }

        kullaniciDAO.ekle(kullanici);
    }

    public Kullanici kullaniciBul(Long id) {
        Kullanici kullanici = kullaniciDAO.idIleBul(id);

        if (kullanici == null) {
            throw new NotFoundException("Kullanici bulunamadi.");
        }

        return kullanici;
    }

    public List<Kullanici> kullanicilariListele() {
        return kullaniciDAO.tumunuListele();
    }

    public int musteriSayisi() {
        return kullaniciDAO.musteriSayisi();
    }

    public void kullaniciGuncelle(Kullanici kullanici) {
        if (kullanici == null || kullanici.getId() == null) {
            throw new ValidationException("Guncellenecek kullanici bilgisi gecersiz.");
        }

        kullaniciDAO.guncelle(kullanici);
    }

    public void kullaniciSil(Long id) {
        Kullanici kullanici = kullaniciDAO.idIleBul(id);

        if (kullanici == null) {
            throw new NotFoundException("Silinecek kullanici bulunamadi.");
        }
        kullaniciDAO.sil(id);
    }

    public Kullanici girisYap(String email, String sifre) {

        if (email == null || email.isBlank()) {
            throw new ValidationException("Email bos olamaz.");
        }

        if (sifre == null || sifre.isBlank()) {
            throw new ValidationException("Sifre bos olamaz.");
        }

        Kullanici kullanici = kullaniciDAO.emailVeSifreIleBul(email, sifre);

        if (kullanici == null) {
            throw new NotFoundException("Email veya sifre hatali.");
        }

        return kullanici;
    }
}
