package com.rentacar.bean;

import com.rentacar.entity.Kullanici;
import com.rentacar.entity.Rol;
import com.rentacar.service.KullaniciService;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
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
    private String hataMesaji;

    public List<Kullanici> getKullanicilar() {
        return kullaniciService.kullanicilariListele();
    }

    public int getToplamMusteriSayisi() {
        return kullaniciService.musteriSayisi();
    }

    public String kullaniciEkle() {
        Kullanici kullanici = new Kullanici();

        kullanici.setAdSoyad(adSoyad);
        kullanici.setEmail(email);
        kullanici.setTelefon(telefon);
        kullanici.setSifre(sifre);
        kullanici.setRol(rol);

        try {
            kullaniciService.kullaniciEkle(kullanici);

            FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getSessionMap()
                    .put("girisYapanKullanici", kullanici);

            hataMesaji = null;
            return "cars?faces-redirect=true";
        } catch (RuntimeException e) {
            hataMesaji = "Kayit islemi basarisiz. Email ve sifre bilgilerini kontrol edin.";
            return null;
        }
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

    public String getHataMesaji() {
        return hataMesaji;
    }
}
