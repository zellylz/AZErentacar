package com.rentacar;

import com.rentacar.dao.KullaniciDAO;
import com.rentacar.dao.AracDAO;

public class TestMain {

    public static void main(String[] args) {

        System.out.println("BAĞLANTI BAŞARILI 💥");

        KullaniciDAO kullaniciDAO = new KullaniciDAO();
        AracDAO aracDAO = new AracDAO();

        // SADECE OKUMA (SELECT)
        System.out.println("\nKULLANICILAR:");
        kullaniciDAO.tumunuListele()
                .forEach(k -> System.out.println(k.getAdSoyad()));

        System.out.println("\nARACLAR:");
        aracDAO.tumunuListele()
                .forEach(a -> System.out.println(a.getMarka() + " " + a.getModel()));

        System.out.println("\nSİSTEM ÇALIŞIYOR ✔");
    }
}
