package com.rentacar.bean;

import com.rentacar.entity.Arac;
import com.rentacar.entity.AracDurumu;
import com.rentacar.service.AracService;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

import java.math.BigDecimal;
import java.util.List;

@Named("aracBean")
@RequestScoped
public class AracBean {

    private AracService aracService = new AracService();

    private String marka;
    private String model;
    private int yil;
    private String plaka;
    private BigDecimal gunlukUcret;
    private AracDurumu durum = AracDurumu.MUSAIT;

    public List<Arac> getAraclar() {
        return aracService.araclariListele();
    }

    public int getToplamAracSayisi() {
        return aracService.aracSayisi();
    }

    public List<Arac> getMusaitAraclar() {
        return aracService.musaitAraclariListele();
    }

    public String aracResmi(Arac arac) {
        String ad = (arac.getMarka() + " " + arac.getModel()).toLowerCase();

        if (ad.contains("toyota")) {
            return "image/toyota-2.png";
        }
        if (ad.contains("audi")) {
            return "image/audi-a4.png";
        }
        if (ad.contains("bmw")) {
            return "image/bmw-3-series.png";
        }
        if (ad.contains("mercedes") || ad.contains("eqa")) {
            return "image/car.png";
        }
        if (ad.contains("volkswagen") || ad.contains("golf")) {
            return "image/wolkswagen-golf.png";
        }
        if (ad.contains("renault") || ad.contains("clio")) {
            return "image/renault-clio.png";
        }

        return "image/car.png";
    }

    public String aracEkle() {
        Arac arac = new Arac();

        arac.setMarka(marka);
        arac.setModel(model);
        arac.setYil(yil);
        arac.setPlaka(plaka);
        arac.setGunlukUcret(gunlukUcret);
        arac.setAracDurumu(durum);

        aracService.aracEkle(arac);

        return "araclar?faces-redirect=true";
    }

    public void aracSil(Long id) {
        aracService.aracSil(id);
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYil() {
        return yil;
    }

    public void setYil(int yil) {
        this.yil = yil;
    }

    public String getPlaka() {
        return plaka;
    }

    public void setPlaka(String plaka) {
        this.plaka = plaka;
    }

    public BigDecimal getGunlukUcret() {
        return gunlukUcret;
    }

    public void setGunlukUcret(BigDecimal gunlukUcret) {
        this.gunlukUcret = gunlukUcret;
    }

    public AracDurumu getDurum() {
        return durum;
    }

    public void setDurum(AracDurumu durum) {
        this.durum = durum;
    }
}
