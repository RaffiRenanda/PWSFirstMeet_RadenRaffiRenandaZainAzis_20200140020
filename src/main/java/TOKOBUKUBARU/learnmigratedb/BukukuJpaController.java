/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TOKOBUKUBARU.learnmigratedb;

import TOKOBUKUBARU.learnmigratedb.exceptions.NonexistentEntityException;
import TOKOBUKUBARU.learnmigratedb.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author TUF GAMING
 */
public class BukukuJpaController implements Serializable {

    public BukukuJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Bukuku bukuku) throws PreexistingEntityException, Exception {
        if (bukuku.getSuppliersCollection() == null) {
            bukuku.setSuppliersCollection(new ArrayList<Suppliers>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Suppliers> attachedSuppliersCollection = new ArrayList<Suppliers>();
            for (Suppliers suppliersCollectionSuppliersToAttach : bukuku.getSuppliersCollection()) {
                suppliersCollectionSuppliersToAttach = em.getReference(suppliersCollectionSuppliersToAttach.getClass(), suppliersCollectionSuppliersToAttach.getIdSupplier());
                attachedSuppliersCollection.add(suppliersCollectionSuppliersToAttach);
            }
            bukuku.setSuppliersCollection(attachedSuppliersCollection);
            em.persist(bukuku);
            for (Suppliers suppliersCollectionSuppliers : bukuku.getSuppliersCollection()) {
                Bukuku oldKodeBukuOfSuppliersCollectionSuppliers = suppliersCollectionSuppliers.getKodeBuku();
                suppliersCollectionSuppliers.setKodeBuku(bukuku);
                suppliersCollectionSuppliers = em.merge(suppliersCollectionSuppliers);
                if (oldKodeBukuOfSuppliersCollectionSuppliers != null) {
                    oldKodeBukuOfSuppliersCollectionSuppliers.getSuppliersCollection().remove(suppliersCollectionSuppliers);
                    oldKodeBukuOfSuppliersCollectionSuppliers = em.merge(oldKodeBukuOfSuppliersCollectionSuppliers);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findBukuku(bukuku.getKodeBuku()) != null) {
                throw new PreexistingEntityException("Bukuku " + bukuku + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Bukuku bukuku) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Bukuku persistentBukuku = em.find(Bukuku.class, bukuku.getKodeBuku());
            Collection<Suppliers> suppliersCollectionOld = persistentBukuku.getSuppliersCollection();
            Collection<Suppliers> suppliersCollectionNew = bukuku.getSuppliersCollection();
            Collection<Suppliers> attachedSuppliersCollectionNew = new ArrayList<Suppliers>();
            for (Suppliers suppliersCollectionNewSuppliersToAttach : suppliersCollectionNew) {
                suppliersCollectionNewSuppliersToAttach = em.getReference(suppliersCollectionNewSuppliersToAttach.getClass(), suppliersCollectionNewSuppliersToAttach.getIdSupplier());
                attachedSuppliersCollectionNew.add(suppliersCollectionNewSuppliersToAttach);
            }
            suppliersCollectionNew = attachedSuppliersCollectionNew;
            bukuku.setSuppliersCollection(suppliersCollectionNew);
            bukuku = em.merge(bukuku);
            for (Suppliers suppliersCollectionOldSuppliers : suppliersCollectionOld) {
                if (!suppliersCollectionNew.contains(suppliersCollectionOldSuppliers)) {
                    suppliersCollectionOldSuppliers.setKodeBuku(null);
                    suppliersCollectionOldSuppliers = em.merge(suppliersCollectionOldSuppliers);
                }
            }
            for (Suppliers suppliersCollectionNewSuppliers : suppliersCollectionNew) {
                if (!suppliersCollectionOld.contains(suppliersCollectionNewSuppliers)) {
                    Bukuku oldKodeBukuOfSuppliersCollectionNewSuppliers = suppliersCollectionNewSuppliers.getKodeBuku();
                    suppliersCollectionNewSuppliers.setKodeBuku(bukuku);
                    suppliersCollectionNewSuppliers = em.merge(suppliersCollectionNewSuppliers);
                    if (oldKodeBukuOfSuppliersCollectionNewSuppliers != null && !oldKodeBukuOfSuppliersCollectionNewSuppliers.equals(bukuku)) {
                        oldKodeBukuOfSuppliersCollectionNewSuppliers.getSuppliersCollection().remove(suppliersCollectionNewSuppliers);
                        oldKodeBukuOfSuppliersCollectionNewSuppliers = em.merge(oldKodeBukuOfSuppliersCollectionNewSuppliers);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = bukuku.getKodeBuku();
                if (findBukuku(id) == null) {
                    throw new NonexistentEntityException("The bukuku with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Bukuku bukuku;
            try {
                bukuku = em.getReference(Bukuku.class, id);
                bukuku.getKodeBuku();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bukuku with id " + id + " no longer exists.", enfe);
            }
            Collection<Suppliers> suppliersCollection = bukuku.getSuppliersCollection();
            for (Suppliers suppliersCollectionSuppliers : suppliersCollection) {
                suppliersCollectionSuppliers.setKodeBuku(null);
                suppliersCollectionSuppliers = em.merge(suppliersCollectionSuppliers);
            }
            em.remove(bukuku);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Bukuku> findBukukuEntities() {
        return findBukukuEntities(true, -1, -1);
    }

    public List<Bukuku> findBukukuEntities(int maxResults, int firstResult) {
        return findBukukuEntities(false, maxResults, firstResult);
    }

    private List<Bukuku> findBukukuEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Bukuku.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Bukuku findBukuku(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Bukuku.class, id);
        } finally {
            em.close();
        }
    }

    public int getBukukuCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Bukuku> rt = cq.from(Bukuku.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
