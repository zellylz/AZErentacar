package com.rentacar.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "bakim_kayitlari")
public class BakimKayit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate bakimTarihi;

    private String aciklama;

    private Double maliyet;

    @ManyToOne
    @JoinColumn(name = "arac_id")
    private Arac arac;

    public BakimKayit() {
    }

    public Long getId() {
        return id;
    }

    public LocalDate getBakimTarihi() {
        return bakimTarihi;
    }

    public void setBakimTarihi(LocalDate bakimTarihi) {
        this.bakimTarihi = bakimTarihi;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public Double getMaliyet() {
        return maliyet;
    }

    public void setMaliyet(Double maliyet) {
        this.maliyet = maliyet;
    }

    public Arac getArac() {
        return arac;
    }

    public void setArac(Arac arac) {
        this.arac = arac;
    }
}
