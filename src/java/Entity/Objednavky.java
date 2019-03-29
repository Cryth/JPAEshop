/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author splat
 */
@Entity
@Table(name = "objednavky")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Objednavky.findAll", query = "SELECT o FROM Objednavky o")
    , @NamedQuery(name = "Objednavky.findByIdObj", query = "SELECT o FROM Objednavky o WHERE o.idObj = :idObj")
    , @NamedQuery(name = "Objednavky.findByCisloObj", query = "SELECT o FROM Objednavky o WHERE o.cisloObj = :cisloObj")
    , @NamedQuery(name = "Objednavky.findByDatum", query = "SELECT o FROM Objednavky o WHERE o.datum = :datum")
    , @NamedQuery(name = "Objednavky.findBySuma", query = "SELECT o FROM Objednavky o WHERE o.suma = :suma")
    , @NamedQuery(name = "Objednavky.findByStav", query = "SELECT o FROM Objednavky o WHERE o.stav = :stav")})
public class Objednavky implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idObj")
    private Integer idObj;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idUser")
    private Integer idUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CisloObj")
    private int cisloObj;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Datum")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datum;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Suma")
    private double suma;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "Stav")
    private String stav;

    public Objednavky() {
    }

    public Objednavky(Integer idObj) {
        this.idObj = idObj;
    }

    public Objednavky(Integer idObj, Integer idUser, int cisloObj, Date datum, double suma, String stav) {
        this.idObj = idObj;
        this.idUser = idUser;
        this.cisloObj = cisloObj;
        this.datum = datum;
        this.suma = suma;
        this.stav = stav;
    }

    

    public Integer getIdObj() {
        return idObj;
    }

    public void setIdObj(Integer idObj) {
        this.idObj = idObj;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public int getCisloObj() {
        return cisloObj;
    }

    public void setCisloObj(int cisloObj) {
        this.cisloObj = cisloObj;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public double getSuma() {
        return suma;
    }

    public void setSuma(double suma) {
        this.suma = suma;
    }

    public String getStav() {
        return stav;
    }

    public void setStav(String stav) {
        this.stav = stav;
    }

    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idObj != null ? idObj.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Objednavky)) {
            return false;
        }
        Objednavky other = (Objednavky) object;
        if ((this.idObj == null && other.idObj != null) || (this.idObj != null && !this.idObj.equals(other.idObj))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Objednavky[ idObj=" + idObj + " ]";
    }
    
}
