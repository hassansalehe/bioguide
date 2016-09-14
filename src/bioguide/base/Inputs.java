/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bioguide.base;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
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
import javax.persistence.Transient;

/**
 *
 * @author labuser
 */
@Entity
@Table(name = "inputs", catalog = "igem_db", schema = "")
@NamedQueries({
    @NamedQuery(name = "Inputs.findAll", query = "SELECT \u0131 FROM Inputs \u0131"),
    @NamedQuery(name = "Inputs.findByInputID", query = "SELECT \u0131 FROM Inputs \u0131 WHERE \u0131.inputID = :inputID"),
    @NamedQuery(name = "Inputs.findByPromoter", query = "SELECT \u0131 FROM Inputs \u0131 WHERE \u0131.promoter = :promoter"),
    @NamedQuery(name = "Inputs.findByActivity", query = "SELECT \u0131 FROM Inputs \u0131 WHERE \u0131.activity = :activity"),
    @NamedQuery(name = "Inputs.findByInducer", query = "SELECT \u0131 FROM Inputs \u0131 WHERE \u0131.inducer = :inducer"),
    @NamedQuery(name = "Inputs.findByActivator", query = "SELECT \u0131 FROM Inputs \u0131 WHERE \u0131.activator = :activator"),
    @NamedQuery(name = "Inputs.findByRepressor", query = "SELECT \u0131 FROM Inputs \u0131 WHERE \u0131.repressor = :repressor"),
    @NamedQuery(name = "Inputs.findByInhibitor", query = "SELECT \u0131 FROM Inputs \u0131 WHERE \u0131.inhibitor = :inhibitor"),
    @NamedQuery(name = "Inputs.findByPromoter2", query = "SELECT \u0131 FROM Inputs \u0131 WHERE \u0131.promoter2 = :promoter2"),
    @NamedQuery(name = "Inputs.findByActivity2", query = "SELECT \u0131 FROM Inputs \u0131 WHERE \u0131.activity2 = :activity2"),
    @NamedQuery(name = "Inputs.findByInducer2", query = "SELECT \u0131 FROM Inputs \u0131 WHERE \u0131.inducer2 = :inducer2"),
    @NamedQuery(name = "Inputs.findByActivator2", query = "SELECT \u0131 FROM Inputs \u0131 WHERE \u0131.activator2 = :activator2"),
    @NamedQuery(name = "Inputs.findByRepressor2", query = "SELECT \u0131 FROM Inputs \u0131 WHERE \u0131.repressor2 = :repressor2"),
    @NamedQuery(name = "Inputs.findByInhibitor2", query = "SELECT \u0131 FROM Inputs \u0131 WHERE \u0131.inhibitor2 = :inhibitor2")})
public class Inputs implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "InputID")
    private Integer inputID;
    @Column(name = "Promoter")
    private String promoter;
    @Column(name = "Activity")
    private String activity;
    @Column(name = "Inducer")
    private String inducer;
    @Column(name = "Activator")
    private String activator;
    @Column(name = "Repressor")
    private String repressor;
    @Column(name = "Inhibitor")
    private String inhibitor;
    @Column(name = "Promoter2")
    private String promoter2;
    @Column(name = "Activity2")
    private String activity2;
    @Column(name = "Inducer2")
    private String inducer2;
    @Column(name = "Activator2")
    private String activator2;
    @Column(name = "Repressor2")
    private String repressor2;
    @Column(name = "Inhibitor2")
    private String inhibitor2;

    public Inputs() {
    }

    public Inputs(Integer inputID) {
        this.inputID = inputID;
    }

    public Integer getInputID() {
        return inputID;
    }

    public void setInputID(Integer inputID) {
        Integer oldInputID = this.inputID;
        this.inputID = inputID;
        changeSupport.firePropertyChange("inputID", oldInputID, inputID);
    }

    public String getPromoter() {
        return promoter;
    }

    public void setPromoter(String promoter) {
        String oldPromoter = this.promoter;
        this.promoter = promoter;
        changeSupport.firePropertyChange("promoter", oldPromoter, promoter);
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        String oldActivity = this.activity;
        this.activity = activity;
        changeSupport.firePropertyChange("activity", oldActivity, activity);
    }

    public String getInducer() {
        return inducer;
    }

    public void setInducer(String inducer) {
        String oldInducer = this.inducer;
        this.inducer = inducer;
        changeSupport.firePropertyChange("inducer", oldInducer, inducer);
    }

    public String getActivator() {
        return activator;
    }

    public void setActivator(String activator) {
        String oldActivator = this.activator;
        this.activator = activator;
        changeSupport.firePropertyChange("activator", oldActivator, activator);
    }

    public String getRepressor() {
        return repressor;
    }

    public void setRepressor(String repressor) {
        String oldRepressor = this.repressor;
        this.repressor = repressor;
        changeSupport.firePropertyChange("repressor", oldRepressor, repressor);
    }

    public String getInhibitor() {
        return inhibitor;
    }

    public void setInhibitor(String inhibitor) {
        String oldInhibitor = this.inhibitor;
        this.inhibitor = inhibitor;
        changeSupport.firePropertyChange("inhibitor", oldInhibitor, inhibitor);
    }

    public String getPromoter2() {
        return promoter2;
    }

    public void setPromoter2(String promoter2) {
        String oldPromoter2 = this.promoter2;
        this.promoter2 = promoter2;
        changeSupport.firePropertyChange("promoter2", oldPromoter2, promoter2);
    }

    public String getActivity2() {
        return activity2;
    }

    public void setActivity2(String activity2) {
        String oldActivity2 = this.activity2;
        this.activity2 = activity2;
        changeSupport.firePropertyChange("activity2", oldActivity2, activity2);
    }

    public String getInducer2() {
        return inducer2;
    }

    public void setInducer2(String inducer2) {
        String oldInducer2 = this.inducer2;
        this.inducer2 = inducer2;
        changeSupport.firePropertyChange("inducer2", oldInducer2, inducer2);
    }

    public String getActivator2() {
        return activator2;
    }

    public void setActivator2(String activator2) {
        String oldActivator2 = this.activator2;
        this.activator2 = activator2;
        changeSupport.firePropertyChange("activator2", oldActivator2, activator2);
    }

    public String getRepressor2() {
        return repressor2;
    }

    public void setRepressor2(String repressor2) {
        String oldRepressor2 = this.repressor2;
        this.repressor2 = repressor2;
        changeSupport.firePropertyChange("repressor2", oldRepressor2, repressor2);
    }

    public String getInhibitor2() {
        return inhibitor2;
    }

    public void setInhibitor2(String inhibitor2) {
        String oldInhibitor2 = this.inhibitor2;
        this.inhibitor2 = inhibitor2;
        changeSupport.firePropertyChange("inhibitor2", oldInhibitor2, inhibitor2);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (inputID != null ? inputID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Inputs)) {
            return false;
        }
        Inputs other = (Inputs) object;
        if ((this.inputID == null && other.inputID != null) || (this.inputID != null && !this.inputID.equals(other.inputID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bioguide.Inputs[inputID=" + inputID + "]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }

}
