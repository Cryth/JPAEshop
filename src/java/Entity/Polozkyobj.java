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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author splat
 */
@Entity
@Table(name = "polozkyobj")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Polozkyobj.findAll", query = "SELECT p FROM Polozkyobj p")
    , @NamedQuery(name = "Polozkyobj.findByIdPO", query = "SELECT p FROM Polozkyobj p WHERE p.idPO = :idPO")
    , @NamedQuery(name = "Polozkyobj.findByCena", query = "SELECT p FROM Polozkyobj p WHERE p.cena = :cena")
    , @NamedQuery(name = "Polozkyobj.findByKusy", query = "SELECT p FROM Polozkyobj p WHERE p.kusy = :kusy")})
public class Polozkyobj implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPO")
    private Integer idPO;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idObj")
    private Integer idObj;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idTovar")
    private Integer idTovar;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Cena")
    private double cena;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Kusy")
    private int kusy;
    

    public Polozkyobj() {
    }

    public Polozkyobj(Integer idPO) {
        this.idPO = idPO;
    }

    public Polozkyobj(Integer idPO, Integer idObj, Integer idTovar, double cena, int kusy) {
        this.idPO = idPO;
        this.idObj = idObj;
        this.idTovar = idTovar;
        this.cena = cena;
        this.kusy = kusy;
    }

    

    public Integer getIdObj() {
        return idObj;
    }

    public void setIdObj(Integer idObj) {
        this.idObj = idObj;
    }

    public Integer getIdTovar() {
        return idTovar;
    }

    public void setIdTovar(Integer idTovar) {
        this.idTovar = idTovar;
    }

    

    public Integer getIdPO() {
        return idPO;
    }

    public void setIdPO(Integer idPO) {
        this.idPO = idPO;
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

    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPO != null ? idPO.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Polozkyobj)) {
            return false;
        }
        Polozkyobj other = (Polozkyobj) object;
        if ((this.idPO == null && other.idPO != null) || (this.idPO != null && !this.idPO.equals(other.idPO))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Polozkyobj[ idPO=" + idPO + " ]";
    }
    
}
