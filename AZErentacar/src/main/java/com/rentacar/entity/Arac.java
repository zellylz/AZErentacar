package com.rentacar.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "araclar")
public class Arac {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String marka;
    private String model;
    private int yil;

    @Column(unique = true, nullable = false)
    private String plaka;

    private BigDecimal gunlukUcret;

    @Enumerated(EnumType.STRING)
    private AracDurumu aracDurumu;

    @OneToMany(mappedBy = "arac")
    private List<Rezervasyon> rezervasyonlar;

    @OneToMany(mappedBy = "arac")
    private List<KiralamaIslemi> kiralamalar;

    @OneToMany(mappedBy = "arac")
    private List<BakimKayit> bakimKayitlari;

    public Arac() {
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

    public java.math.BigDecimal getGunlukUcret() {
        return gunlukUcret;
    }

    public void setGunlukUcret(java.math.BigDecimal gunlukUcret) {
        this.gunlukUcret = gunlukUcret;
    }
}
