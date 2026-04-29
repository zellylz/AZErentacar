package com.rentacar.dao;

import com.rentacar.entity.Odeme;
import jakarta.persistence.*;
import java.util.List;

public class OdemeDAO {

    private EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("rentacarPU");

    public void ekle(Odeme odeme) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(odeme);
        em.getTransaction().commit();
        em.close();
    }

    public Odeme idIleBul(Long id) {
        EntityManager em = emf.createEntityManager();
        Odeme odeme = em.find(Odeme.class, id);
        em.close();
        return odeme;
    }

    public List<Odeme> tumunuListele() {
        EntityManager em = emf.createEntityManager();
        List<Odeme> liste =
                em.createQuery("SELECT o FROM Odeme o", Odeme.class)
                        .getResultList();
        em.close();
        return liste;
    }

    public void guncelle(Odeme odeme) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(odeme);
        em.getTransaction().commit();
        em.close();
    }

    public void sil(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Odeme o = em.find(Odeme.class, id);
        if (o != null) em.remove(o);
        em.getTransaction().commit();
        em.close();
    }
}
