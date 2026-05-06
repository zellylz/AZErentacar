package com.rentacar.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "odemeler")
public class Odeme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal tutar;

    private LocalDateTime odemeTarihi;

    private String odemeYontemi;

    private String odemeDurumu;

    @OneToOne
    @JoinColumn(name = "rezervasyon_id")
    private Rezervasyon rezervasyon;

    @OneToOne
    @JoinColumn(name = "kiralama_islemi_id")
    private KiralamaIslemi kiralamaIslemi;

    public Odeme() {
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getTutar() {
        return tutar;
    }

    public void setTutar(BigDecimal tutar) {
        this.tutar = tutar;
    }

    public LocalDateTime getOdemeTarihi() {
        return odemeTarihi;
    }

    public void setOdemeTarihi(LocalDateTime odemeTarihi) {
        this.odemeTarihi = odemeTarihi;
    }

    public String getOdemeYontemi() {
        return odemeYontemi;
    }

    public void setOdemeYontemi(String odemeYontemi) {
        this.odemeYontemi = odemeYontemi;
    }

    public String getOdemeDurumu() {
        return odemeDurumu;
    }

    public void setOdemeDurumu(String odemeDurumu) {
        this.odemeDurumu = odemeDurumu;
    }

    public Rezervasyon getRezervasyon() {
        return rezervasyon;
    }

    public void setRezervasyon(Rezervasyon rezervasyon) {
        this.rezervasyon = rezervasyon;
    }

    public KiralamaIslemi getKiralamaIslemi() {
        return kiralamaIslemi;
    }

    public void setKiralamaIslemi(KiralamaIslemi kiralamaIslemi) {
        this.kiralamaIslemi = kiralamaIslemi;
    }
}