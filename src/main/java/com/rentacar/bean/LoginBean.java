package com.rentacar.bean;

import com.rentacar.entity.Kullanici;
import com.rentacar.entity.Rol;
import com.rentacar.service.KullaniciService;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.faces.context.FacesContext;

import java.io.Serializable;

@Named("loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    private KullaniciService kullaniciService = new KullaniciService();

    private String email;
    private String sifre;
    private Kullanici girisYapanKullanici;

    public String girisYap() {
        Kullanici kullanici = kullaniciService.girisYap(email, sifre);

        if (kullanici != null) {
            girisYapanKullanici = kullanici;

            if (kullanici.getRol() == Rol.ADMIN) {
                return "admin?faces-redirect=true";
            } else {
                return "index?faces-redirect=true";
            }
        }

        return "login?faces-redirect=true";
    }

    public String cikisYap() {
        FacesContext.getCurrentInstance()
                .getExternalContext()
                .invalidateSession();

        return "login?faces-redirect=true";
    }

    public boolean isGirisYapildiMi() {
        return girisYapanKullanici != null;
    }

    public boolean isAdminMi() {
        return girisYapanKullanici != null &&
                girisYapanKullanici.getRol() == Rol.ADMIN;
    }

    public boolean isMusteriMi() {
        return girisYapanKullanici != null &&
                girisYapanKullanici.getRol() == Rol.MUSTERI;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    public Kullanici getGirisYapanKullanici() {
        return girisYapanKullanici;
    }

    public void setGirisYapanKullanici(Kullanici girisYapanKullanici) {
        this.girisYapanKullanici = girisYapanKullanici;
    }
}
