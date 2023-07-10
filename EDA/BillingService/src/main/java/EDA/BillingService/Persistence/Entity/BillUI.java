package EDA.BillingService.Persistence.Entity;

import java.time.LocalDate;

public class BillUI {
    private long id;
    private LocalDate dueDate;
    private double amount;
    private String details;
    public BillUI() {
    }
    public BillUI(long id, LocalDate dueDate, double amount, String details) {
        this.id = id;
        this.dueDate = dueDate;
        this.amount = amount;
        this.details = details;
    }
    public BillUI(Bill bill){
        id = bill.getId();
        dueDate = bill.getDueDate();
        amount = bill.getChargedAmount();
        details = bill.getReason();
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public LocalDate getDueDate() {
        return dueDate;
    }
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public String getDetails() {
        return details;
    }
    public void setDetails(String details) {
        this.details = details;
    }
}
