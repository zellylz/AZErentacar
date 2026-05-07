package com.rentacar.servlet;

import com.rentacar.entity.Kullanici;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Date;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@WebServlet("/completeRental")
public class RentalCompleteServlet extends HttpServlet {
    private static final String URL = "jdbc:postgresql://localhost:5432/rentacar";
    private static final String USER = "postgres";
    private static final String PASSWORD = "130477";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String carName = request.getParameter("car");
        String pickDate = request.getParameter("pick");
        String returnDate = request.getParameter("return");

        if (carName == null || carName.isBlank()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Arac bilgisi eksik.");
            return;
        }

        if (pickDate == null || pickDate.isBlank() || returnDate == null || returnDate.isBlank()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tarih bilgisi eksik.");
            return;
        }

        HttpSession session = request.getSession(false);
        Kullanici kullanici = session == null
                ? null
                : (Kullanici) session.getAttribute("girisYapanKullanici");

        if (kullanici == null || kullanici.getId() == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Rezervasyon icin uye girisi gereklidir.");
            return;
        }

        try {
            LocalDate baslangicTarihi = LocalDate.parse(pickDate);
            LocalDate bitisTarihi = LocalDate.parse(returnDate);

            if (!bitisTarihi.isAfter(baslangicTarihi)) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Gecersiz kiralama tarihi.");
                return;
            }

            createTableIfNeeded();
            long aracId = markCarAsRented(carName);
            createRental(kullanici.getId(), aracId, baslangicTarihi, bitisTarihi);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tarih formati gecersiz.");
        } catch (SQLException e) {
            throw new ServletException("Arac durumu guncellenemedi.", e);
        }
    }

    private void createTableIfNeeded() throws SQLException {
        String userSql = """
                CREATE TABLE IF NOT EXISTS kullanicilar (
                    id BIGSERIAL PRIMARY KEY,
                    adsoyad VARCHAR(255),
                    email VARCHAR(255) NOT NULL UNIQUE,
                    telefon VARCHAR(255),
                    sifre VARCHAR(255),
                    rol VARCHAR(50)
                )
                """;
        String carSql = """
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
        String rentalSql = """
                CREATE TABLE IF NOT EXISTS kiralama_islemleri (
                    id BIGSERIAL PRIMARY KEY,
                    baslangic_tarihi DATE,
                    bitis_tarihi DATE,
                    durum VARCHAR(50),
                    kullanici_id BIGINT REFERENCES kullanicilar(id),
                    arac_id BIGINT REFERENCES araclar(id)
                )
                """;

        try (Connection connection = getConnection();
             PreparedStatement userStatement = connection.prepareStatement(userSql);
             PreparedStatement carStatement = connection.prepareStatement(carSql)) {
            userStatement.execute();
            carStatement.execute();
            try (PreparedStatement rentalStatement = connection.prepareStatement(rentalSql)) {
                rentalStatement.execute();
            }
        }
    }

    private long markCarAsRented(String carName) throws SQLException {
        CarRecord car = carRecordFor(carName);

        String sql = """
                INSERT INTO araclar (marka, model, yil, plaka, gunluk_ucret, arac_durumu)
                VALUES (?, ?, ?, ?, ?, 'KIRADA')
                ON CONFLICT (plaka)
                DO UPDATE SET arac_durumu = 'KIRADA',
                              marka = EXCLUDED.marka,
                              model = EXCLUDED.model,
                              yil = EXCLUDED.yil,
                              gunluk_ucret = EXCLUDED.gunluk_ucret
                RETURNING id
                """;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, car.marka());
            statement.setString(2, car.model());
            statement.setInt(3, car.yil());
            statement.setString(4, car.plaka());
            statement.setBigDecimal(5, car.gunlukUcretAsBigDecimal());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getLong("id");
                }
            }
        }

        throw new SQLException("Arac kaydi olusturulamadi.");
    }

    private void createRental(Long kullaniciId, long aracId, LocalDate baslangicTarihi, LocalDate bitisTarihi) throws SQLException {
        String sql = """
                INSERT INTO kiralama_islemleri (baslangic_tarihi, bitis_tarihi, durum, kullanici_id, arac_id)
                SELECT ?, ?, 'AKTIF', ?, ?
                WHERE NOT EXISTS (
                    SELECT 1
                    FROM kiralama_islemleri
                    WHERE kullanici_id = ?
                      AND arac_id = ?
                      AND baslangic_tarihi = ?
                )
                """;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            Date baslangic = Date.valueOf(baslangicTarihi);
            statement.setDate(1, baslangic);
            statement.setDate(2, Date.valueOf(bitisTarihi));
            statement.setLong(3, kullaniciId);
            statement.setLong(4, aracId);
            statement.setLong(5, kullaniciId);
            statement.setLong(6, aracId);
            statement.setDate(7, baslangic);
            statement.executeUpdate();
        }
    }

    private CarRecord carRecordFor(String carName) {
        return switch (carName) {
            case "Mercedes EQA" -> new CarRecord("Mercedes", "EQA", 2024, "34 EQA 700", "95.00");
            case "BMW 3" -> new CarRecord("BMW", "3 Series", 2024, "34 BMW 330", "120.00");
            case "Audi A4" -> new CarRecord("Audi", "A4", 2023, "06 AUD 404", "110.00");
            case "Toyota Corolla" -> new CarRecord("Toyota", "Corolla", 2022, "34 TYC 202", "70.00");
            case "Renault Clio" -> new CarRecord("Renault", "Clio", 2022, "34 RNO 060", "60.00");
            default -> new CarRecord("Volkswagen", "Golf", 2023, "34 AZE 104", "50.00");
        };
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    private record CarRecord(String marka, String model, int yil, String plaka, String gunlukUcret) {
        java.math.BigDecimal gunlukUcretAsBigDecimal() {
            return new java.math.BigDecimal(gunlukUcret);
        }
    }
}
