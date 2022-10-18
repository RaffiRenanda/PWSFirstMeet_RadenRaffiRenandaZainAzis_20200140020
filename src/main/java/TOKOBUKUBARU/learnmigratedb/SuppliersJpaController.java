/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TOKOBUKUBARU.learnmigratedb;

import TOKOBUKUBARU.learnmigratedb.exceptions.NonexistentEntityException;
import TOKOBUKUBARU.learnmigratedb.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author TUF GAMING
 */
public class SuppliersJpaController implements Serializable {

    public SuppliersJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("TOKOBUKUBARU_learnmigratedb_jar_0.0.1-SNAPSHOTPU");

    public SuppliersJpaController() {
    }

    
    
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Suppliers suppliers) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Bukuku kodeBuku = suppliers.getKodeBuku();
            if (kodeBuku != null) {
                kodeBuku = em.getReference(kodeBuku.getClass(), kodeBuku.getKodeBuku());
                suppliers.setKodeBuku(kodeBuku);
            }
            em.persist(suppliers);
            if (kodeBuku != null) {
                kodeBuku.getSuppliersCollection().add(suppliers);
                kodeBuku = em.merge(kodeBuku);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSuppliers(suppliers.getIdSupplier()) != null) {
                throw new PreexistingEntityException("Suppliers " + suppliers + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Suppliers suppliers) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Suppliers persistentSuppliers = em.find(Suppliers.class, suppliers.getIdSupplier());
            Bukuku kodeBukuOld = persistentSuppliers.getKodeBuku();
            Bukuku kodeBukuNew = suppliers.getKodeBuku();
            if (kodeBukuNew != null) {
                kodeBukuNew = em.getReference(kodeBukuNew.getClass(), kodeBukuNew.getKodeBuku());
                suppliers.setKodeBuku(kodeBukuNew);
            }
            suppliers = em.merge(suppliers);
            if (kodeBukuOld != null && !kodeBukuOld.equals(kodeBukuNew)) {
                kodeBukuOld.getSuppliersCollection().remove(suppliers);
                kodeBukuOld = em.merge(kodeBukuOld);
            }
            if (kodeBukuNew != null && !kodeBukuNew.equals(kodeBukuOld)) {
                kodeBukuNew.getSuppliersCollection().add(suppliers);
                kodeBukuNew = em.merge(kodeBukuNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = suppliers.getIdSupplier();
                if (findSuppliers(id) == null) {
                    throw new NonexistentEntityException("The suppliers with id " + id + " no longer exists.");
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
            Suppliers suppliers;
            try {
                suppliers = em.getReference(Suppliers.class, id);
                suppliers.getIdSupplier();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The suppliers with id " + id + " no longer exists.", enfe);
            }
            Bukuku kodeBuku = suppliers.getKodeBuku();
            if (kodeBuku != null) {
                kodeBuku.getSuppliersCollection().remove(suppliers);
                kodeBuku = em.merge(kodeBuku);
            }
            em.remove(suppliers);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Suppliers> findSuppliersEntities() {
        return findSuppliersEntities(true, -1, -1);
    }

    public List<Suppliers> findSuppliersEntities(int maxResults, int firstResult) {
        return findSuppliersEntities(false, maxResults, firstResult);
    }

    private List<Suppliers> findSuppliersEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Suppliers.class));
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

    public Suppliers findSuppliers(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Suppliers.class, id);
        } finally {
            em.close();
        }
    }

    public int getSuppliersCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Suppliers> rt = cq.from(Suppliers.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
