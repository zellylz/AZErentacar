package com.rentacar.dao;

import com.rentacar.entity.Arac;
import jakarta.persistence.*;
import java.util.List;

public class AracDAO {

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
}
