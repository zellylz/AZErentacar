package com.rentacar.dao;

import com.rentacar.entity.BakimKayit;
import jakarta.persistence.*;
import java.util.List;

public class BakimKayitDAO {

    private EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("rentacarPU");

    public void ekle(BakimKayit b) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(b);
        em.getTransaction().commit();
        em.close();
    }

    public BakimKayit idIleBul(Long id) {
        EntityManager em = emf.createEntityManager();
        BakimKayit b = em.find(BakimKayit.class, id);
        em.close();
        return b;
    }

    public List<BakimKayit> tumunuListele() {
        EntityManager em = emf.createEntityManager();
        List<BakimKayit> liste =
                em.createQuery("SELECT b FROM BakimKayit b", BakimKayit.class)
                        .getResultList();
        em.close();
        return liste;
    }

    public void guncelle(BakimKayit b) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(b);
        em.getTransaction().commit();
        em.close();
    }

    public void sil(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        BakimKayit b = em.find(BakimKayit.class, id);
        if (b != null) em.remove(b);
        em.getTransaction().commit();
        em.close();
    }
}
