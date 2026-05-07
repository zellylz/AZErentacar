package com.rentacar.dao;

import com.rentacar.entity.Arac;
import com.rentacar.entity.AracDurumu;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class AracDAO {
    private static final String URL = "jdbc:postgresql://localhost:5432/rentacar";
    private static final String USER = "postgres";
    private static final String PASSWORD = "130477";
    private static boolean aracTablosuKontrolEdildi;

    private EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("rentacarPU");

    public void ekle(Arac arac) {
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        em.persist(arac);
        em.getTransaction().commit();

        em.close();
    }

    public Arac idIleBul(Long id) {
        EntityManager em = emf.createEntityManager();

        Arac arac = em.find(Arac.class, id);

        em.close();
        return arac;
    }

    public List<Arac> tumunuListele() {
        EntityManager em = emf.createEntityManager();

        List<Arac> araclar =
                em.createQuery("SELECT a FROM Arac a", Arac.class)
                        .getResultList();

        em.close();
        return araclar;
    }

    public int aracSayisi() {
        aracTablosunuOlustur();

        String sql = "SELECT COUNT(*) FROM araclar";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }

            return 0;
        } catch (SQLException e) {
            throw new RuntimeException("Arac sayisi sorgulanamadi.", e);
        }
    }

    public List<Arac> musaitAraclariListele() {
        aracTablosunuOlustur();

        String sql = """
                SELECT id, marka, model, yil, plaka, gunluk_ucret, arac_durumu
                FROM araclar
                WHERE arac_durumu = 'MUSAIT'
                ORDER BY marka, model
                """;

        List<Arac> araclar = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                araclar.add(resultSetToArac(resultSet));
            }

            return araclar;
        } catch (SQLException e) {
            throw new RuntimeException("Musait araclar listelenemedi.", e);
        }
    }

    public void guncelle(Arac arac) {
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        em.merge(arac);
        em.getTransaction().commit();

        em.close();
    }

    public void sil(Long id) {
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        Arac arac = em.find(Arac.class, id);

        if (arac != null) {
            em.remove(arac);
        }

        em.getTransaction().commit();
        em.close();
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    private synchronized void aracTablosunuOlustur() {
        if (aracTablosuKontrolEdildi) {
            return;
        }

        String sql = """
                CREATE TABLE IF NOT EXISTS araclar (
                    id BIGSERIAL PRIMARY KEY,
                    marka VARCHAR(255),
                    model VARCHAR(255),
                    yil INTEGER,
                    plaka VARCHAR(255) NOT NULL UNIQUE,
                    gunluk_ucret NUMERIC(12, 2),
                    arac_durumu VARCHAR(50)
                )
                """;

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
            seedMusaitArac(statement, "Volkswagen", "Golf", 2023, "34 AZE 104", "50.00");
            seedMusaitArac(statement, "Toyota", "Corolla", 2022, "34 TYC 202", "70.00");
            seedMusaitArac(statement, "Renault", "Clio", 2021, "34 RNO 060", "60.00");
            seedMusaitArac(statement, "Audi", "A4", 2023, "06 AUD 404", "110.00");
            seedMusaitArac(statement, "BMW", "3 Series", 2024, "44 BMW 330", "120.00");
            seedMusaitArac(statement, "Mercedes", "EQA", 2024, "34 EQA 700", "95.00");
            aracTablosuKontrolEdildi = true;
        } catch (SQLException e) {
            throw new RuntimeException("Araclar tablosu olusturulamadi.", e);
        }
    }

    private void seedMusaitArac(Statement statement, String marka, String model, int yil, String plaka, String gunlukUcret) throws SQLException {
        statement.executeUpdate("""
                INSERT INTO araclar (marka, model, yil, plaka, gunluk_ucret, arac_durumu)
                VALUES ('%s', '%s', %d, '%s', %s, 'MUSAIT')
                ON CONFLICT (plaka) DO UPDATE
                SET arac_durumu = 'MUSAIT',
                    gunluk_ucret = EXCLUDED.gunluk_ucret
                """.formatted(marka, model, yil, plaka, gunlukUcret));
    }

    private Arac resultSetToArac(ResultSet resultSet) throws SQLException {
        Arac arac = new Arac();
        arac.setId(resultSet.getLong("id"));
        arac.setMarka(resultSet.getString("marka"));
        arac.setModel(resultSet.getString("model"));
        arac.setYil(resultSet.getInt("yil"));
        arac.setPlaka(resultSet.getString("plaka"));
        arac.setGunlukUcret(resultSet.getBigDecimal("gunluk_ucret"));

        String durum = resultSet.getString("arac_durumu");
        if (durum != null) {
            arac.setAracDurumu(AracDurumu.valueOf(durum));
        }

        return arac;
    }
}
