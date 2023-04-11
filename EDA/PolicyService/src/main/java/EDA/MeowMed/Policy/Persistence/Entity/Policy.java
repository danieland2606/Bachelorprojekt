package EDA.MeowMed.Policy.Persistence.Entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;


@Entity
@Table(name="Policy")
public class Policy implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;


    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;


    @Column(name = "end_date", nullable = true)
    private LocalDate endDate;

    @Column(name = "coverage", nullable = false)
    private int coverage;


    @Column(name = "premium", nullable = false  )
    private double premium;

    @OneToOne(optional = false)
    private ObjectOfInsurance objectOfInsurance;

    @ManyToOne(optional = false)
    private Customer customer;

    public Policy(long id, LocalDate startDate, LocalDate endDate, int coverage, int premium, ObjectOfInsurance objectOfInsurance) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.coverage = coverage;
        this.premium = premium;
        this.objectOfInsurance = objectOfInsurance;
    }

    public Policy() {

    }

    /**
     * Konstruktor, der die ausgewählten Attribute als Parameter akzeptiert.
     * Dieser Konstruktor wird benötigt, damit Spring Data JPA in der Lage ist, die Ergebnisse
     * der Abfrage in eine "Policy"-Instanz zu konvertieren.
     */
    public Policy(long id,double premium , ObjectOfInsurance objectOfInsurance , LocalDate startDate, LocalDate endDate, int coverage) {
        this.id = id;
        this.coverage = coverage;
        this.startDate = startDate;
        this.endDate = endDate;
        this.objectOfInsurance = objectOfInsurance;
        this.premium = premium;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getCoverage() {
        return coverage;
    }

    public void setCoverage(int coverage) {
        this.coverage = coverage;
    }

    public double getPremium() {
        return premium;
    }

    public void setPremium(double premium) {
        this.premium = premium;
    }

    public ObjectOfInsurance getObjectOfInsurance() {
        return objectOfInsurance;
    }

    public void setObjectOfInsurance(ObjectOfInsurance objectOfInsurance) {
        this.objectOfInsurance = objectOfInsurance;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
