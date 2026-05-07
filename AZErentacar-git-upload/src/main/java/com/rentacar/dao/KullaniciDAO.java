package com.rentacar.dao;

import com.rentacar.entity.Kullanici;
import com.rentacar.entity.Rol;
import jakarta.persistence.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class KullaniciDAO {
    private static final String URL = "jdbc:postgresql://localhost:5432/rentacar";
    private static final String USER = "postgres";
    private static final String PASSWORD = "130477";
    private static boolean kullaniciTablosuKontrolEdildi;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("PostgreSQL driver bulunamadi.", e);
        }
    }

    private EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("rentacarPU");

    public void ekle(Kullanici kullanici) {
        kullaniciTablosunuOlustur();

        String sql = "INSERT INTO kullanicilar (adsoyad, email, telefon, sifre, rol) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, kullanici.getAdSoyad());
            statement.setString(2, kullanici.getEmail());
            statement.setString(3, kullanici.getTelefon());
            statement.setString(4, kullanici.getSifre());
            statement.setString(5, kullanici.getRol().name());
            statement.executeUpdate();

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    kullanici.setId(keys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Kullanici kaydedilemedi.", e);
        }
    }

    public Kullanici idIleBul(Long id) {
        EntityManager em = emf.createEntityManager();

        Kullanici kullanici = em.find(Kullanici.class, id);

        em.close();
        return kullanici;
    }

    public List<Kullanici> tumunuListele() {
        EntityManager em = emf.createEntityManager();

        List<Kullanici> kullanicilar =
                em.createQuery("SELECT k FROM Kullanici k", Kullanici.class)
                        .getResultList();

        em.close();
        return kullanicilar;
    }

    public int musteriSayisi() {
        kullaniciTablosunuOlustur();

        String sql = "SELECT COUNT(*) FROM kullanicilar WHERE rol = 'MUSTERI'";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }

            return 0;
        } catch (SQLException e) {
            throw new RuntimeException("Musteri sayisi sorgulanamadi.", e);
        }
    }

    public void guncelle(Kullanici kullanici) {
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        em.merge(kullanici);
        em.getTransaction().commit();

        em.close();
    }

    public void sil(Long id) {
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        Kullanici kullanici = em.find(Kullanici.class, id);

        if (kullanici != null) {
            em.remove(kullanici);
        }

        em.getTransaction().commit();
        em.close();
    }
    public Kullanici emailVeSifreIleBul(String email, String sifre) {
        kullaniciTablosunuOlustur();
        varsayilanAdminOlustur();

        String sql = "SELECT id, adsoyad, email, telefon, sifre, rol FROM kullanicilar WHERE email = ? AND sifre = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);
            statement.setString(2, sifre);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSetToKullanici(resultSet);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Kullanici sorgulanamadi.", e);
        }
    }

    public Kullanici emailIleBul(String email) {
        kullaniciTablosunuOlustur();

        String sql = "SELECT id, adsoyad, email, telefon, sifre, rol FROM kullanicilar WHERE email = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSetToKullanici(resultSet);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Kullanici sorgulanamadi.", e);
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    private synchronized void kullaniciTablosunuOlustur() {
        if (kullaniciTablosuKontrolEdildi) {
            return;
        }

        String sql = """
                CREATE TABLE IF NOT EXISTS kullanicilar (
                    id BIGSERIAL PRIMARY KEY,
                    adsoyad VARCHAR(255),
                    email VARCHAR(255) NOT NULL UNIQUE,
                    telefon VARCHAR(255),
                    sifre VARCHAR(255),
                    rol VARCHAR(50)
                )
                """;

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
            kullaniciTablosuKontrolEdildi = true;
        } catch (SQLException e) {
            throw new RuntimeException("Kullanicilar tablosu olusturulamadi.", e);
        }
    }

    private void varsayilanAdminOlustur() {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("""
                    UPDATE kullanicilar
                    SET email = 'admin@admin.com', adsoyad = 'Admin', sifre = 'admin', rol = 'ADMIN'
                    WHERE email = 'admin'
                    """);

            String sql = """
                    INSERT INTO kullanicilar (adsoyad, email, telefon, sifre, rol)
                    VALUES ('Admin', 'admin@admin.com', NULL, 'admin', 'ADMIN')
                    ON CONFLICT (email)
                    DO UPDATE SET adsoyad = 'Admin', sifre = 'admin', rol = 'ADMIN'
                    """;
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Varsayilan admin kullanicisi olusturulamadi.", e);
        }
    }

    private Kullanici resultSetToKullanici(ResultSet resultSet) throws SQLException {
        Kullanici kullanici = new Kullanici();

        kullanici.setId(resultSet.getLong("id"));
        kullanici.setAdSoyad(resultSet.getString("adsoyad"));
        kullanici.setEmail(resultSet.getString("email"));
        kullanici.setTelefon(resultSet.getString("telefon"));
        kullanici.setSifre(resultSet.getString("sifre"));

        String rol = resultSet.getString("rol");
        if (rol != null) {
            kullanici.setRol(Rol.valueOf(rol));
        }

        return kullanici;
    }
}
