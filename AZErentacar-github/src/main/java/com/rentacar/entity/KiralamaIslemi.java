package com.rentacar.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "kiralama_islemleri")
public class KiralamaIslemi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate baslangicTarihi;
    private LocalDate bitisTarihi;

    private String durum;

    @ManyToOne
    @JoinColumn(name = "kullanici_id")
    private Kullanici kullanici;

    @ManyToOne
    @JoinColumn(name = "arac_id")
    private Arac arac;

    @OneToOne(mappedBy = "kiralamaIslemi")
    private Odeme odeme;

    public KiralamaIslemi() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDurum() {
        return durum;
    }

    public void setDurum(String durum) {
        this.durum = durum;
    }

    public Kullanici getKullanici() {
        return kullanici;
    }

    public void setKullanici(Kullanici kullanici) {
        this.kullanici = kullanici;
    }

    public Arac getArac() {
        return arac;
    }

    public void setArac(Arac arac) {
        this.arac = arac;
    }

    public Odeme getOdeme() {
        return odeme;
    }

    public void setOdeme(Odeme odeme) {
        this.odeme = odeme;
    }
}
