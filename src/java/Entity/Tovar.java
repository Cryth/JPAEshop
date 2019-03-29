/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author splat
 */
@Entity
@Table(name = "tovar")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tovar.findAll", query = "SELECT t FROM Tovar t")
    , @NamedQuery(name = "Tovar.findByIdTovar", query = "SELECT t FROM Tovar t WHERE t.idTovar = :idTovar")
    , @NamedQuery(name = "Tovar.findByNazov", query = "SELECT t FROM Tovar t WHERE t.nazov = :nazov")
    , @NamedQuery(name = "Tovar.findByCena", query = "SELECT t FROM Tovar t WHERE t.cena = :cena")
    , @NamedQuery(name = "Tovar.findByKusy", query = "SELECT t FROM Tovar t WHERE t.kusy = :kusy")
    , @NamedQuery(name = "Tovar.findByHref", query = "SELECT t FROM Tovar t WHERE t.href = :href")})
public class Tovar implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTovar")
    private Integer idTovar;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Nazov")
    private String nazov;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Cena")
    private double cena;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Kusy")
    private int kusy;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "href")
    private String href;

    public Tovar() {
    }

    public Tovar(Integer idTovar) {
        this.idTovar = idTovar;
    }

    public Tovar(Integer idTovar, String nazov, double cena, int kusy, String href) {
        this.idTovar = idTovar;
        this.nazov = nazov;
        this.cena = cena;
        this.kusy = kusy;
        this.href = href;
    }

    public Integer getIdTovar() {
        return idTovar;
    }

    public void setIdTovar(Integer idTovar) {
        this.idTovar = idTovar;
    }

    public String getNazov() {
        return nazov;
    }

    public void setNazov(String nazov) {
        this.nazov = nazov;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public int getKusy() {
        return kusy;
    }

    public void setKusy(int kusy) {
        this.kusy = kusy;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTovar != null ? idTovar.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tovar)) {
            return false;
        }
        Tovar other = (Tovar) object;
        if ((this.idTovar == null && other.idTovar != null) || (this.idTovar != null && !this.idTovar.equals(other.idTovar))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Tovar[ idTovar=" + idTovar + " ]";
    }
    
}
