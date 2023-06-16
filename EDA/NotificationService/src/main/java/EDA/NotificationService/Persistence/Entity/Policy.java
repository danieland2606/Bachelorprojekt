package EDA.NotificationService.Persistence.Entity;

import event.objects.policy.PolicyEvent;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "Policy")
public class Policy implements Serializable {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    private Long pid;


    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;


    @Column(name = "end_date", nullable = true)
    private LocalDate endDate;

    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @Column(name = "coverage", nullable = false)
    private int coverage;

    @Column(name = "premium", nullable = false)
    private double premium;

    @Column(name = "display_currency", nullable = false)
    private String displayCurrency;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "object_of_insurance")
    private ObjectOfInsurance objectOfInsurance;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer")
    private Customer customer;

    public Policy(long pid, LocalDate startDate, LocalDate endDate, LocalDate dueDate, int coverage, double premium, String displayCurrency, ObjectOfInsurance objectOfInsurance, Customer customer) {
        this.pid = pid;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dueDate = dueDate;
        this.coverage = coverage;
        this.premium = premium;
        this.displayCurrency = displayCurrency;
        this.objectOfInsurance = objectOfInsurance;
        this.customer = customer;
    }

    public Policy(Policy policy) {
        this.pid = policy.getPid();
        this.startDate = policy.getStartDate();
        this.endDate = policy.getEndDate();
        this.dueDate = policy.getDueDate();
        this.coverage = policy.getCoverage();
        this.premium = policy.getPremium();
        this.displayCurrency = policy.getDisplayCurrency();
        this.objectOfInsurance = new ObjectOfInsurance(policy.getObjectOfInsurance());
        this.customer = policy.getCustomer();
    }

    public Policy() {

    }

    public Policy(PolicyEvent policyEvent, Customer customer) {
        this.pid = policyEvent.getPid();
        this.startDate = policyEvent.getStartDate();
        this.endDate = policyEvent.getEndDate();
        this.dueDate = policyEvent.getDueDate();
        this.coverage = policyEvent.getCoverage();
        this.premium = policyEvent.getPremium();
        this.displayCurrency = policyEvent.getDisplayCurrency();
        this.objectOfInsurance = new ObjectOfInsurance(policyEvent.getObjectOfInsurance());
        this.customer = customer;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
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

    public String getDisplayCurrency() {
        return displayCurrency;
    }

    public void setDisplayCurrency(String displayCurrency) {
        this.displayCurrency = displayCurrency;
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


}
