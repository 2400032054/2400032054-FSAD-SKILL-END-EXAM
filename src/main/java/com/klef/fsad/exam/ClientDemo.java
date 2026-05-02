package com.klef.fsad.exam;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.Date;
import java.util.List;

public class ClientDemo {
    public static void main(String[] args) {
        // Create Configuration and SessionFactory
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory sessionFactory = cfg.buildSessionFactory();

        // I. Insert records using a persistent object
        insertRecord(sessionFactory);

        // II. View all records without using a WHERE clause using HQL with positional parameters
        // Since we cannot logically use positional parameters without a WHERE clause for filtering, 
        // we'll demonstrate fetching all records, and then fetching with positional parameters as required.
        viewAllRecords(sessionFactory);
        viewRecordsWithPositionalParameters(sessionFactory);

        sessionFactory.close();
    }

    private static void insertRecord(SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        try {
            Invoice invoice1 = new Invoice();
            invoice1.setName("Laptop Purchase");
            invoice1.setDate(new Date());
            invoice1.setStatus("PAID");
            invoice1.setAmount(1200.50);

            Invoice invoice2 = new Invoice();
            invoice2.setName("Web Hosting Service");
            invoice2.setDate(new Date());
            invoice2.setStatus("PENDING");
            invoice2.setAmount(150.00);

            session.save(invoice1);
            session.save(invoice2);

            tx.commit();
            System.out.println("Records inserted successfully!");
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    private static void viewAllRecords(SessionFactory sessionFactory) {
        System.out.println("\n--- Viewing all records (No WHERE clause) ---");
        Session session = sessionFactory.openSession();
        try {
            String hql = "FROM Invoice";
            Query<Invoice> query = session.createQuery(hql, Invoice.class);
            List<Invoice> invoices = query.list();

            for (Invoice inv : invoices) {
                System.out.println("ID: " + inv.getId() + ", Name: " + inv.getName() + 
                                   ", Status: " + inv.getStatus() + ", Amount: $" + inv.getAmount());
            }
        } finally {
            session.close();
        }
    }
    
    private static void viewRecordsWithPositionalParameters(SessionFactory sessionFactory) {
        System.out.println("\n--- Viewing records using HQL with positional parameters ---");
        Session session = sessionFactory.openSession();
        try {
            // Using JPA positional parameters (?1)
            String hql = "FROM Invoice WHERE status = ?1";
            Query<Invoice> query = session.createQuery(hql, Invoice.class);
            query.setParameter(1, "PAID");
            
            List<Invoice> invoices = query.list();

            for (Invoice inv : invoices) {
                System.out.println("ID: " + inv.getId() + ", Name: " + inv.getName() + 
                                   ", Status: " + inv.getStatus() + ", Amount: $" + inv.getAmount());
            }
        } finally {
            session.close();
        }
    }
}
