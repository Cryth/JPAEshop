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
@Table(name = "kosik")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kosik.findAll", query = "SELECT k FROM Kosik k")
    , @NamedQuery(name = "Kosik.findByIdKosik", query = "SELECT k FROM Kosik k WHERE k.idKosik = :idKosik")
    , @NamedQuery(name = "Kosik.findByCena", query = "SELECT k FROM Kosik k WHERE k.cena = :cena")
    , @NamedQuery(name = "Kosik.findByKusy", query = "SELECT k FROM Kosik k WHERE k.kusy = :kusy")})
public class Kosik implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idKosik")
    private Integer idKosik; // tutututututu
    @Basic(optional = false)
    @NotNull
    @Column(name = "idUser")
    private Integer idUser;
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

    public Kosik() {
    }

    public Kosik(Integer idKosik) {
        this.idKosik = idKosik;
    }

    public Kosik(Integer idKosik, Integer idUser, Integer idTovar, double cena, int kusy) {
        this.idKosik = idKosik;
        this.idUser = idUser;
        this.idTovar = idTovar;
        this.cena = cena;
        this.kusy = kusy;
    }


    public Integer getIdKosik() {
        return idKosik;
    }

    public void setIdKosik(Integer idKosik) {
        this.idKosik = idKosik;
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

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Integer getIdTovar() {
        return idTovar;
    }

    public void setIdTovar(Integer idTovar) {
        this.idTovar = idTovar;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idKosik != null ? idKosik.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Kosik)) {
            return false;
        }
        Kosik other = (Kosik) object;
        if ((this.idKosik == null && other.idKosik != null) || (this.idKosik != null && !this.idKosik.equals(other.idKosik))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Kosik[ idKosik=" + idKosik + " ]";
    }
    
}
