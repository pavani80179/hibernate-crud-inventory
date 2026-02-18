package com.pavani.hibernate_crud_inventory;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class MainApp {

    public static void main(String[] args) {

        // ---------------- INSERT ----------------
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        Product p1 = new Product("Laptop", "Gaming Laptop", 75000, 5);
        Product p2 = new Product("Mouse", "Wireless Mouse", 800, 50);

        session.persist(p1);
        session.persist(p2);

        tx.commit();
        session.close();

        System.out.println("Products Inserted Successfully!");

        // ---------------- RETRIEVE ----------------
        Session session2 = HibernateUtil.getSessionFactory().openSession();
        Product product = session2.get(Product.class, 1L);

        if (product != null) {
            System.out.println("Retrieved Product:");
            System.out.println("Name: " + product.getName());
        }

        session2.close();

        // ---------------- UPDATE ----------------
        Session session3 = HibernateUtil.getSessionFactory().openSession();
        Transaction tx3 = session3.beginTransaction();

        Product updateProduct = session3.get(Product.class, 1L);
        if (updateProduct != null) {
            updateProduct.setPrice(70000);
            updateProduct.setQuantity(10);
        }

        tx3.commit();
        session3.close();

        System.out.println("Product Updated Successfully!");

        // ---------------- DELETE ----------------
        Session session4 = HibernateUtil.getSessionFactory().openSession();
        Transaction tx4 = session4.beginTransaction();

        Product deleteProduct = session4.get(Product.class, 2L);
        if (deleteProduct != null) {
            session4.remove(deleteProduct);
        }

        tx4.commit();
        session4.close();

        System.out.println("Product Deleted Successfully!");
    }
}
