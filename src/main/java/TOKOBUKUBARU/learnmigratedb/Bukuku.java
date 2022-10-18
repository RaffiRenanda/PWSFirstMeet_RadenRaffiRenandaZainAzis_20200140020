/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TOKOBUKUBARU.learnmigratedb;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author TUF GAMING
 */
@Entity
@Table(name = "bukuku")
@NamedQueries({
    @NamedQuery(name = "Bukuku.findAll", query = "SELECT b FROM Bukuku b"),
    @NamedQuery(name = "Bukuku.findByKodeBuku", query = "SELECT b FROM Bukuku b WHERE b.kodeBuku = :kodeBuku"),
    @NamedQuery(name = "Bukuku.findByIdToko", query = "SELECT b FROM Bukuku b WHERE b.idToko = :idToko"),
    @NamedQuery(name = "Bukuku.findByIdSupplier", query = "SELECT b FROM Bukuku b WHERE b.idSupplier = :idSupplier"),
    @NamedQuery(name = "Bukuku.findByJudulBuku", query = "SELECT b FROM Bukuku b WHERE b.judulBuku = :judulBuku"),
    @NamedQuery(name = "Bukuku.findByTahunTerbit", query = "SELECT b FROM Bukuku b WHERE b.tahunTerbit = :tahunTerbit"),
    @NamedQuery(name = "Bukuku.findByRakBuku", query = "SELECT b FROM Bukuku b WHERE b.rakBuku = :rakBuku"),
    @NamedQuery(name = "Bukuku.findByHargaBuku", query = "SELECT b FROM Bukuku b WHERE b.hargaBuku = :hargaBuku"),
    @NamedQuery(name = "Bukuku.findByPenulis", query = "SELECT b FROM Bukuku b WHERE b.penulis = :penulis")})
public class Bukuku implements Serializable {

    @OneToMany(mappedBy = "kodeBuku")
    private Collection<Suppliers> suppliersCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "kode_buku")
    private String kodeBuku;
    @Basic(optional = false)
    @Column(name = "id_toko")
    private String idToko;
    @Basic(optional = false)
    @Column(name = "id_supplier")
    private String idSupplier;
    @Basic(optional = false)
    @Column(name = "judul_buku")
    private String judulBuku;
    @Basic(optional = false)
    @Column(name = "tahun_terbit")
    private String tahunTerbit;
    @Basic(optional = false)
    @Column(name = "rak_buku")
    private String rakBuku;
    @Column(name = "harga_buku")
    private String hargaBuku;
    @Column(name = "penulis")
    private String penulis;

    public Bukuku() {
    }

    public Bukuku(String kodeBuku) {
        this.kodeBuku = kodeBuku;
    }

    public Bukuku(String kodeBuku, String idToko, String idSupplier, String judulBuku, String tahunTerbit, String rakBuku) {
        this.kodeBuku = kodeBuku;
        this.idToko = idToko;
        this.idSupplier = idSupplier;
        this.judulBuku = judulBuku;
        this.tahunTerbit = tahunTerbit;
        this.rakBuku = rakBuku;
    }

    public String getKodeBuku() {
        return kodeBuku;
    }

    public void setKodeBuku(String kodeBuku) {
        this.kodeBuku = kodeBuku;
    }

    public String getIdToko() {
        return idToko;
    }

    public void setIdToko(String idToko) {
        this.idToko = idToko;
    }

    public String getIdSupplier() {
        return idSupplier;
    }

    public void setIdSupplier(String idSupplier) {
        this.idSupplier = idSupplier;
    }

    public String getJudulBuku() {
        return judulBuku;
    }

    public void setJudulBuku(String judulBuku) {
        this.judulBuku = judulBuku;
    }

    public String getTahunTerbit() {
        return tahunTerbit;
    }

    public void setTahunTerbit(String tahunTerbit) {
        this.tahunTerbit = tahunTerbit;
    }

    public String getRakBuku() {
        return rakBuku;
    }

    public void setRakBuku(String rakBuku) {
        this.rakBuku = rakBuku;
    }

    public String getHargaBuku() {
        return hargaBuku;
    }

    public void setHargaBuku(String hargaBuku) {
        this.hargaBuku = hargaBuku;
    }

    public String getPenulis() {
        return penulis;
    }

    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kodeBuku != null ? kodeBuku.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bukuku)) {
            return false;
        }
        Bukuku other = (Bukuku) object;
        if ((this.kodeBuku == null && other.kodeBuku != null) || (this.kodeBuku != null && !this.kodeBuku.equals(other.kodeBuku))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TOKOBUKUBARU.learnmigratedb.Bukuku[ kodeBuku=" + kodeBuku + " ]";
    }

    public Collection<Suppliers> getSuppliersCollection() {
        return suppliersCollection;
    }

    public void setSuppliersCollection(Collection<Suppliers> suppliersCollection) {
        this.suppliersCollection = suppliersCollection;
    }
    
}
