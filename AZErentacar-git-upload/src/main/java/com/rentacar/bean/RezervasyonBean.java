package com.rentacar.bean;

import com.rentacar.entity.Arac;
import com.rentacar.entity.Kullanici;
import com.rentacar.entity.Rezervasyon;
import com.rentacar.service.AracService;
import com.rentacar.service.KullaniciService;
import com.rentacar.service.RezervasyonService;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

import java.time.LocalDate;
import java.util.List;

@Named("rezervasyonBean")
@RequestScoped
public class RezervasyonBean {

    private RezervasyonService rezervasyonService = new RezervasyonService();
    private KullaniciService kullaniciService = new KullaniciService();
    private AracService aracService = new AracService();

    private Long kullaniciId;
    private Long aracId;
    private LocalDate baslangicTarihi;
    private LocalDate bitisTarihi;

    public List<Rezervasyon> getRezervasyonlar() {
        return rezervasyonService.rezervasyonlariListele();
    }

    public String rezervasyonOlustur() {
        Kullanici kullanici = kullaniciService.kullaniciBul(kullaniciId);
        Arac arac = aracService.aracBul(aracId);

        Rezervasyon rezervasyon = new Rezervasyon();
        rezervasyon.setKullanici(kullanici);
        rezervasyon.setArac(arac);
        rezervasyon.setBaslangicTarihi(baslangicTarihi);
        rezervasyon.setBitisTarihi(bitisTarihi);
        rezervasyon.setDurum("AKTIF");

        rezervasyonService.rezervasyonEkle(rezervasyon);

        return "rezervasyonlar?faces-redirect=true";
    }

    public Long getKullaniciId() {
        return kullaniciId;
    }

    public void setKullaniciId(Long kullaniciId) {
        this.kullaniciId = kullaniciId;
    }

    public Long getAracId() {
        return aracId;
    }

    public void setAracId(Long aracId) {
        this.aracId = aracId;
    }

    public LocalDate getBaslangicTarihi() {
        return baslangicTarihi;
    }

    public void setBaslangicTarihi(LocalDate baslangicTarihi) {
        this.baslangicTarihi = baslangicTarihi;
    }

    public LocalDate getBitisTarihi() {
        return bitisTarihi;
    }

    public void setBitisTarihi(LocalDate bitisTarihi) {
        this.bitisTarihi = bitisTarihi;
    }
}
