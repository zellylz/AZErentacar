package com.rentacar.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "kullanicilar")
public class Kullanici implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String adSoyad;

    @Column(unique = true, nullable = false)
    private String email;

    private String telefon;

    private String sifre;

    // Kullanıcının rezervasyonları
    @OneToMany(mappedBy = "kullanici")
    private List<Rezervasyon> rezervasyonlar;

    @Enumerated(EnumType.STRING)
    private Rol rol;
    // Kullanıcının kiralamaları
    @OneToMany(mappedBy = "kullanici")
    private List<KiralamaIslemi> kiralamalar;

    public Kullanici() {
    }

    public String getAdSoyad() {
        return adSoyad;
    }

    public void setAdSoyad(String adSoyad) {
        this.adSoyad = adSoyad;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
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

    public Long getId(){
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
