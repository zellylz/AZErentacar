package com.rentacar.dao;

import com.rentacar.entity.KiralamaIslemi;
import jakarta.persistence.*;
import java.util.List;

public class KiralamaIslemiDAO {

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
        EntityManager em = emf.createEntityManager();
        List<KiralamaIslemi> liste =
                em.createQuery("SELECT k FROM KiralamaIslemi k", KiralamaIslemi.class)
                        .getResultList();
        em.close();
        return liste;
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
}
