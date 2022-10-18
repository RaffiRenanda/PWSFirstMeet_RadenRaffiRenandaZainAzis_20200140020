/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TOKOBUKUBARU.learnmigratedb;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author TUF GAMING
 */
@Entity
@Table(name = "suppliers")
@NamedQueries({
    @NamedQuery(name = "Suppliers.findAll", query = "SELECT s FROM Suppliers s"),
    @NamedQuery(name = "Suppliers.findByIdSupplier", query = "SELECT s FROM Suppliers s WHERE s.idSupplier = :idSupplier"),
    @NamedQuery(name = "Suppliers.findByNamaSupplier", query = "SELECT s FROM Suppliers s WHERE s.namaSupplier = :namaSupplier"),
    @NamedQuery(name = "Suppliers.findByAlamatSupplier", query = "SELECT s FROM Suppliers s WHERE s.alamatSupplier = :alamatSupplier"),
    @NamedQuery(name = "Suppliers.findByTelpSupplier", query = "SELECT s FROM Suppliers s WHERE s.telpSupplier = :telpSupplier")})
public class Suppliers implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_supplier")
    private String idSupplier;
    @Column(name = "nama_supplier")
    private String namaSupplier;
    @Column(name = "alamat_supplier")
    private String alamatSupplier;
    @Basic(optional = false)
    @Column(name = "telp_supplier")
    private String telpSupplier;
    @JoinColumn(name = "kode_buku", referencedColumnName = "kode_buku")
    @ManyToOne
    private Bukuku kodeBuku;

    public Suppliers() {
    }

    public Suppliers(String idSupplier) {
        this.idSupplier = idSupplier;
    }

    public Suppliers(String idSupplier, String telpSupplier) {
        this.idSupplier = idSupplier;
        this.telpSupplier = telpSupplier;
    }

    public String getIdSupplier() {
        return idSupplier;
    }

    public void setIdSupplier(String idSupplier) {
        this.idSupplier = idSupplier;
    }

    public String getNamaSupplier() {
        return namaSupplier;
    }

    public void setNamaSupplier(String namaSupplier) {
        this.namaSupplier = namaSupplier;
    }

    public String getAlamatSupplier() {
        return alamatSupplier;
    }

    public void setAlamatSupplier(String alamatSupplier) {
        this.alamatSupplier = alamatSupplier;
    }

    public String getTelpSupplier() {
        return telpSupplier;
    }

    public void setTelpSupplier(String telpSupplier) {
        this.telpSupplier = telpSupplier;
    }

    public Bukuku getKodeBuku() {
        return kodeBuku;
    }

    public void setKodeBuku(Bukuku kodeBuku) {
        this.kodeBuku = kodeBuku;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSupplier != null ? idSupplier.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Suppliers)) {
            return false;
        }
        Suppliers other = (Suppliers) object;
        if ((this.idSupplier == null && other.idSupplier != null) || (this.idSupplier != null && !this.idSupplier.equals(other.idSupplier))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TOKOBUKUBARU.learnmigratedb.Suppliers[ idSupplier=" + idSupplier + " ]";
    }
    
}
