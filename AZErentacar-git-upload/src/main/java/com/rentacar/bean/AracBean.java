package com.rentacar.bean;

import com.rentacar.entity.Arac;
import com.rentacar.entity.AracDurumu;
import com.rentacar.service.AracService;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

import java.math.BigDecimal;
import java.util.ArrayList;
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
        try {
            int toplam = aracService.aracSayisi();
            return toplam > 0 ? toplam : varsayilanMusaitAraclar().size();
        } catch (RuntimeException e) {
            return varsayilanMusaitAraclar().size();
        }
    }

    public List<Arac> getMusaitAraclar() {
        List<Arac> araclar;

        try {
            araclar = aracService.musaitAraclariListele();
        } catch (RuntimeException e) {
            return varsayilanMusaitAraclar();
        }

        if (!araclar.isEmpty()) {
            return araclar;
        }

        return varsayilanMusaitAraclar();
    }

    private List<Arac> varsayilanMusaitAraclar() {
        List<Arac> araclar = new ArrayList<>();
        araclar.add(aracOlustur("Volkswagen", "Golf", "34 AZE 104", "50.00"));
        araclar.add(aracOlustur("Toyota", "Corolla", "34 TYC 202", "70.00"));
        araclar.add(aracOlustur("Renault", "Clio", "34 RNO 060", "60.00"));
        araclar.add(aracOlustur("Audi", "A4", "06 AUD 404", "110.00"));
        araclar.add(aracOlustur("BMW", "3 Series", "44 BMW 330", "120.00"));
        araclar.add(aracOlustur("Mercedes", "EQA", "34 EQA 700", "95.00"));
        return araclar;
    }

    private Arac aracOlustur(String marka, String model, String plaka, String gunlukUcret) {
        Arac arac = new Arac();
        arac.setMarka(marka);
        arac.setModel(model);
        arac.setPlaka(plaka);
        arac.setGunlukUcret(new BigDecimal(gunlukUcret));
        arac.setAracDurumu(AracDurumu.MUSAIT);
        return arac;
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
