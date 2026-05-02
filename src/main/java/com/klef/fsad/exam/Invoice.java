package com.klef.fsad.exam;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "invoice_table")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "invoice_name")
    private String name;
    
    @Column(name = "invoice_date")
    @Temporal(TemporalType.DATE)
    private Date date;
    
    @Column(name = "status")
    private String status;
    
    @Column(name = "amount")
    private double amount;

    // Default constructor
    public Invoice() {}

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
}
