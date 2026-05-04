package com.rentacar.bean;

import com.rentacar.entity.Kullanici;
import com.rentacar.entity.Rol;
import com.rentacar.service.KullaniciService;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

import java.util.List;

@Named("kullaniciBean")
@RequestScoped
public class KullaniciBean {

    private KullaniciService kullaniciService = new KullaniciService();

    private String adSoyad;
    private String email;
    private String telefon;
    private String sifre;
    private Rol rol = Rol.MUSTERI;

    public List<Kullanici> getKullanicilar() {
        return kullaniciService.kullanicilariListele();
    }

    public String kullaniciEkle() {
        Kullanici kullanici = new Kullanici();

        kullanici.setAdSoyad(adSoyad);
        kullanici.setEmail(email);
        kullanici.setTelefon(telefon);
        kullanici.setSifre(sifre);
        kullanici.setRol(rol);

        kullaniciService.kullaniciEkle(kullanici);

        return "kullanicilar?faces-redirect=true";
    }

    public String getAdSoyad() {
        return adSoyad;
    }

    public void setAdSoyad(String adSoyad) {
        this.adSoyad = adSoyad;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
