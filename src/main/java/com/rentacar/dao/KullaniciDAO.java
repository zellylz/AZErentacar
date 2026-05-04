package com.rentacar.dao;

import com.rentacar.entity.Kullanici;
import jakarta.persistence.*;
import java.util.List;

public class KullaniciDAO {

    private EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("rentacarPU");

    public void ekle(Kullanici kullanici) {
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        em.persist(kullanici);
        em.getTransaction().commit();

        em.close();
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
        EntityManager em = emf.createEntityManager();

        try {
            Kullanici kullanici = em.createQuery(
                            "SELECT k FROM Kullanici k WHERE k.email = :email AND k.sifre = :sifre",
                            Kullanici.class
                    )
                    .setParameter("email", email)
                    .setParameter("sifre", sifre)
                    .getSingleResult();

            return kullanici;

        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
}
