package EDA.MeowMed.Persistence.Entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import event.objects.policy.subclasses.PolicyPojo;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name="Policy")
@JsonFilter("policyFilter")
public class Policy implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", unique = true, nullable = false)
    private long id;


    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;


    @Column(name = "end_date", nullable = true)
    private LocalDate endDate;

    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @Column(name = "coverage", nullable = false)
    private int coverage;


    @Column(name = "premium", nullable = false  )
    private double premium;

    @Column(name = "active", nullable = false)
    private boolean active;

    @OneToOne(optional = false)
    @JoinColumn(name = "object_of_insurance_id")
    private ObjectOfInsurance objectOfInsurance;

    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Policy(long id, LocalDate startDate, LocalDate endDate, LocalDate dueDate, int coverage, int premium, boolean active, ObjectOfInsurance objectOfInsurance) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dueDate = dueDate;
        this.coverage = coverage;
        this.premium = premium;
        this.active = active;
        this.objectOfInsurance = objectOfInsurance;
    }

    public Policy(Policy p) {
        this.id = p.getId();
        this.startDate = p.getStartDate();
        this.endDate = p.getEndDate();
        this.dueDate = p.getDueDate();
        this.coverage = p.getCoverage();
        this.premium = p.getPremium();
        this.active = p.isActive();
        this.objectOfInsurance = new ObjectOfInsurance(p.getObjectOfInsurance());
        this.customer = p.getCustomer();
    }

    public Policy() {

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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public PolicyPojo toPojo() {
        return new PolicyPojo(
                id,
                startDate,
                endDate,
                dueDate,
                coverage,
                premium,
                objectOfInsurance.toPojo(),
                customer.toPojo()
        );
    }
}
