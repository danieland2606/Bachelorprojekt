package event.objects.policy;

import event.objects.policy.subclasses.ObjectOfInsuranceEvent;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

public class PolicyEvent implements Serializable {

    @Serial
    private static final long serialVersionUID = 20L;

    private Long pid;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalDate dueDate;

    private int coverage;

    private double premium;

    private Long cid;
    private ObjectOfInsuranceEvent objectOfInsuranceEvent;

    public PolicyEvent(Long pid, LocalDate startDate, LocalDate endDate, LocalDate dueDate, int coverage, double premium, ObjectOfInsuranceEvent objectOfInsuranceEvent, Long cid) {
        this.pid = pid;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dueDate = dueDate;
        this.coverage = coverage;
        this.premium = premium;
        this.objectOfInsuranceEvent = objectOfInsuranceEvent;
        this.cid = cid;
    }

    public PolicyEvent() {

    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
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

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
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

    public ObjectOfInsuranceEvent getObjectOfInsurance() {
        return objectOfInsuranceEvent;
    }

    public void setObjectOfInsurance(ObjectOfInsuranceEvent objectOfInsuranceEvent) {
        this.objectOfInsuranceEvent = objectOfInsuranceEvent;
    }

    public Long getCid() {
        return this.cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }
}
