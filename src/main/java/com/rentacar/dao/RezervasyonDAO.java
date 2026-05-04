package com.rentacar.dao;

import com.rentacar.entity.Rezervasyon;
import jakarta.persistence.*;
import java.util.List;

public class RezervasyonDAO {

    private EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("rentacarPU");

    public void ekle(Rezervasyon rezervasyon) {
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        em.persist(rezervasyon);
        em.getTransaction().commit();

        em.close();
    }

    public Rezervasyon idIleBul(Long id) {
        EntityManager em = emf.createEntityManager();

        Rezervasyon rezervasyon = em.find(Rezervasyon.class, id);

        em.close();
        return rezervasyon;
    }

    public List<Rezervasyon> tumunuListele() {
        EntityManager em = emf.createEntityManager();

        List<Rezervasyon> liste =
                em.createQuery("SELECT r FROM Rezervasyon r", Rezervasyon.class)
                        .getResultList();

        em.close();
        return liste;
    }

    public void guncelle(Rezervasyon rezervasyon) {
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        em.merge(rezervasyon);
        em.getTransaction().commit();

        em.close();
    }

    public void sil(Long id) {
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        Rezervasyon r = em.find(Rezervasyon.class, id);

        if (r != null) {
            em.remove(r);
        }

        em.getTransaction().commit();
        em.close();
    }
}
