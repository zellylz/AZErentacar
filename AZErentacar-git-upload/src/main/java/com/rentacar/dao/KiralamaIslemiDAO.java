package com.rentacar.dao;

import com.rentacar.entity.Arac;
import com.rentacar.entity.AracDurumu;
import com.rentacar.entity.KiralamaIslemi;
import com.rentacar.entity.Kullanici;
import com.rentacar.entity.Rol;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;

public class KiralamaIslemiDAO {
    private static final String URL = "jdbc:postgresql://localhost:5432/rentacar";
    private static final String USER = "postgres";
    private static final String PASSWORD = "130477";
    private static boolean seedKontrolEdildi;

    private EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("rentacarPU");

    public void ekle(KiralamaIslemi k) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(k);
        em.getTransaction().commit();
        em.close();
    }

    public KiralamaIslemi idIleBul(Long id) {
        EntityManager em = emf.createEntityManager();
        KiralamaIslemi k = em.find(KiralamaIslemi.class, id);
        em.close();
        return k;
    }

    public List<KiralamaIslemi> tumunuListele() {
        tablolariOlusturVeOrnekVeriEkle();

        String sql = """
                SELECT
                    ki.id AS kiralama_id,
                    ki.baslangic_tarihi,
                    ki.bitis_tarihi,
                    ki.durum,
                    k.id AS kullanici_id,
                    k.adsoyad,
                    k.email,
                    k.telefon,
                    k.sifre,
                    k.rol,
                    a.id AS arac_id,
                    a.marka,
                    a.model,
                    a.yil,
                    a.plaka,
                    a.gunluk_ucret,
                    a.arac_durumu
                FROM kiralama_islemleri ki
                JOIN kullanicilar k ON k.id = ki.kullanici_id
                JOIN araclar a ON a.id = ki.arac_id
                ORDER BY ki.baslangic_tarihi ASC, ki.id ASC
                """;

        List<KiralamaIslemi> liste = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                liste.add(resultSetToKiralama(resultSet));
            }

            return liste;
        } catch (SQLException e) {
            throw new RuntimeException("Kiralamalar listelenemedi.", e);
        }
    }

    public void guncelle(KiralamaIslemi k) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(k);
        em.getTransaction().commit();
        em.close();
    }

    public void sil(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        KiralamaIslemi k = em.find(KiralamaIslemi.class, id);
        if (k != null) em.remove(k);
        em.getTransaction().commit();
        em.close();
    }

    private synchronized void tablolariOlusturVeOrnekVeriEkle() {
        if (seedKontrolEdildi) {
            return;
        }

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute("""
                    CREATE TABLE IF NOT EXISTS kullanicilar (
                        id BIGSERIAL PRIMARY KEY,
                        adsoyad VARCHAR(255),
                        email VARCHAR(255) NOT NULL UNIQUE,
                        telefon VARCHAR(255),
                        sifre VARCHAR(255),
                        rol VARCHAR(50)
                    )
                    """);

            statement.execute("""
                    CREATE TABLE IF NOT EXISTS araclar (
                        id BIGSERIAL PRIMARY KEY,
                        marka VARCHAR(255),
                        model VARCHAR(255),
                        yil INTEGER,
                        plaka VARCHAR(255) NOT NULL UNIQUE,
                        gunluk_ucret NUMERIC(12, 2),
                        arac_durumu VARCHAR(50)
                    )
                    """);

            statement.execute("""
                    CREATE TABLE IF NOT EXISTS kiralama_islemleri (
                        id BIGSERIAL PRIMARY KEY,
                        baslangic_tarihi DATE,
                        bitis_tarihi DATE,
                        durum VARCHAR(50),
                        kullanici_id BIGINT REFERENCES kullanicilar(id),
                        arac_id BIGINT REFERENCES araclar(id)
                    )
                    """);

            seedKullanici(statement, "Ayse Demir", "ayse@example.com", "0555 101 10 10");
            seedKullanici(statement, "Mehmet Kaya", "mehmet@example.com", "0555 202 20 20");
            seedKullanici(statement, "Elif Yilmaz", "elif@example.com", "0555 303 30 30");
            seedKullanici(statement, "Can Aydin", "can@example.com", "0555 404 40 40");

            seedArac(statement, "BMW", "3 Series", 2024, "34 BMW 330", "85.00", "KIRADA");
            seedArac(statement, "Audi", "A4", 2023, "06 AUD 404", "70.00", "KIRADA");
            seedArac(statement, "Toyota", "Corolla", 2022, "34 TYC 202", "45.00", "MUSAIT");
            seedArac(statement, "Mercedes", "EQA", 2024, "34 EQA 700", "95.00", "KIRADA");

            seedKiralama(statement, "ayse@example.com", "34 BMW 330", "2026-05-08", "2026-05-12");
            seedKiralama(statement, "mehmet@example.com", "06 AUD 404", "2026-05-09", "2026-05-13");
            seedKiralama(statement, "elif@example.com", "34 TYC 202", "2026-05-10", "2026-05-14");
            seedKiralama(statement, "can@example.com", "34 EQA 700", "2026-05-11", "2026-05-15");

            seedKontrolEdildi = true;
        } catch (SQLException e) {
            throw new RuntimeException("Ornek kiralama verileri hazirlanamadi.", e);
        }
    }

    private void seedKullanici(Statement statement, String adSoyad, String email, String telefon) throws SQLException {
        statement.executeUpdate("""
                INSERT INTO kullanicilar (adsoyad, email, telefon, sifre, rol)
                VALUES ('%s', '%s', '%s', '1234', 'MUSTERI')
                ON CONFLICT (email)
                DO UPDATE SET adsoyad = EXCLUDED.adsoyad, telefon = EXCLUDED.telefon, rol = 'MUSTERI'
                """.formatted(adSoyad, email, telefon));
    }

    private void seedArac(Statement statement, String marka, String model, int yil, String plaka, String gunlukUcret, String durum) throws SQLException {
        statement.executeUpdate("""
                INSERT INTO araclar (marka, model, yil, plaka, gunluk_ucret, arac_durumu)
                VALUES ('%s', '%s', %d, '%s', %s, '%s')
                ON CONFLICT (plaka)
                DO UPDATE SET marka = EXCLUDED.marka, model = EXCLUDED.model, yil = EXCLUDED.yil,
                              gunluk_ucret = EXCLUDED.gunluk_ucret
                """.formatted(marka, model, yil, plaka, gunlukUcret, durum));
    }

    private void seedKiralama(Statement statement, String email, String plaka, String baslangic, String bitis) throws SQLException {
        statement.executeUpdate("""
                INSERT INTO kiralama_islemleri (baslangic_tarihi, bitis_tarihi, durum, kullanici_id, arac_id)
                SELECT DATE '%s', DATE '%s', 'AKTIF', k.id, a.id
                FROM kullanicilar k, araclar a
                WHERE k.email = '%s'
                  AND a.plaka = '%s'
                  AND NOT EXISTS (
                      SELECT 1
                      FROM kiralama_islemleri ki
                      WHERE ki.kullanici_id = k.id
                        AND ki.arac_id = a.id
                        AND ki.baslangic_tarihi = DATE '%s'
                  )
                """.formatted(baslangic, bitis, email, plaka, baslangic));
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    private KiralamaIslemi resultSetToKiralama(ResultSet resultSet) throws SQLException {
        Kullanici kullanici = new Kullanici();
        kullanici.setId(resultSet.getLong("kullanici_id"));
        kullanici.setAdSoyad(resultSet.getString("adsoyad"));
        kullanici.setEmail(resultSet.getString("email"));
        kullanici.setTelefon(resultSet.getString("telefon"));
        kullanici.setSifre(resultSet.getString("sifre"));
        kullanici.setRol(Rol.valueOf(resultSet.getString("rol")));

        Arac arac = new Arac();
        arac.setId(resultSet.getLong("arac_id"));
        arac.setMarka(resultSet.getString("marka"));
        arac.setModel(resultSet.getString("model"));
        arac.setYil(resultSet.getInt("yil"));
        arac.setPlaka(resultSet.getString("plaka"));
        arac.setGunlukUcret(resultSet.getBigDecimal("gunluk_ucret"));
        arac.setAracDurumu(AracDurumu.valueOf(resultSet.getString("arac_durumu")));

        KiralamaIslemi kiralama = new KiralamaIslemi();
        kiralama.setId(resultSet.getLong("kiralama_id"));
        kiralama.setBaslangicTarihi(resultSet.getDate("baslangic_tarihi").toLocalDate());
        kiralama.setBitisTarihi(resultSet.getDate("bitis_tarihi").toLocalDate());
        kiralama.setDurum(resultSet.getString("durum"));
        kiralama.setKullanici(kullanici);
        kiralama.setArac(arac);

        return kiralama;
    }
}
