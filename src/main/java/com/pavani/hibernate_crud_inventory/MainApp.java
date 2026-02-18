package com.pavani.hibernate_crud_inventory;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class MainApp {

    public static void main(String[] args) {

        // ================= INSERT ADDITIONAL PRODUCTS =================
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.persist(new Product("Keyboard", "Electronics", 1500, 20));
        session.persist(new Product("Monitor", "Electronics", 12000, 8));
        session.persist(new Product("Headphones", "Electronics", 2500, 15));
        session.persist(new Product("Chair", "Furniture", 5000, 12));
        session.persist(new Product("Table", "Furniture", 8000, 5));
        session.persist(new Product("MousePad", "Accessories", 500, 40));
        session.persist(new Product("Speaker", "Electronics", 4000, 10));

        tx.commit();
        session.close();

        System.out.println("Additional Products Inserted!");

        // ================= SORT BY PRICE ASC =================
        Session session2 = HibernateUtil.getSessionFactory().openSession();

        System.out.println("\nPrice Ascending:");
        session2.createQuery("FROM Product ORDER BY price ASC", Product.class)
                .getResultList()
                .forEach(p -> System.out.println(p.getName() + " - " + p.getPrice()));

        // ================= SORT BY PRICE DESC =================
        System.out.println("\nPrice Descending:");
        session2.createQuery("FROM Product ORDER BY price DESC", Product.class)
                .getResultList()
                .forEach(p -> System.out.println(p.getName() + " - " + p.getPrice()));

        // ================= SORT BY QUANTITY DESC =================
        System.out.println("\nQuantity Highest First:");
        session2.createQuery("FROM Product ORDER BY quantity DESC", Product.class)
                .getResultList()
                .forEach(p -> System.out.println(p.getName() + " - " + p.getQuantity()));

        session2.close();

        // ================= PAGINATION =================
        Session session3 = HibernateUtil.getSessionFactory().openSession();

        System.out.println("\nFirst 3 Products:");
        session3.createQuery("FROM Product", Product.class)
                .setFirstResult(0)
                .setMaxResults(3)
                .getResultList()
                .forEach(p -> System.out.println(p.getName()));

        System.out.println("\nNext 3 Products:");
        session3.createQuery("FROM Product", Product.class)
                .setFirstResult(3)
                .setMaxResults(3)
                .getResultList()
                .forEach(p -> System.out.println(p.getName()));

        session3.close();

        // ================= AGGREGATE FUNCTIONS =================
        Session session4 = HibernateUtil.getSessionFactory().openSession();

        Long total = session4.createQuery(
                "SELECT COUNT(p) FROM Product p", Long.class)
                .getSingleResult();
        System.out.println("\nTotal Products: " + total);

        Long available = session4.createQuery(
                "SELECT COUNT(p) FROM Product p WHERE p.quantity > 0", Long.class)
                .getSingleResult();
        System.out.println("Products in Stock: " + available);

        System.out.println("\nProducts Grouped by Description:");
        List<Object[]> grouped = session4.createQuery(
                "SELECT p.description, COUNT(p) FROM Product p GROUP BY p.description",
                Object[].class).getResultList();

        for (Object[] obj : grouped) {
            System.out.println(obj[0] + " : " + obj[1]);
        }

        Object[] minMax = session4.createQuery(
                "SELECT MIN(p.price), MAX(p.price) FROM Product p",
                Object[].class).getSingleResult();

        System.out.println("\nMinimum Price: " + minMax[0]);
        System.out.println("Maximum Price: " + minMax[1]);

        session4.close();

        // ================= WHERE PRICE RANGE =================
        Session session5 = HibernateUtil.getSessionFactory().openSession();

        System.out.println("\nProducts between 2000 and 10000:");
        session5.createQuery(
                "FROM Product p WHERE p.price BETWEEN :min AND :max",
                Product.class)
                .setParameter("min", 2000.0)
                .setParameter("max", 10000.0)
                .getResultList()
                .forEach(p -> System.out.println(p.getName() + " - " + p.getPrice()));

        session5.close();

        // ================= LIKE QUERIES =================
        Session session6 = HibernateUtil.getSessionFactory().openSession();

        System.out.println("\nNames Starting with 'M':");
        session6.createQuery(
                "FROM Product p WHERE p.name LIKE 'M%'", Product.class)
                .getResultList()
                .forEach(p -> System.out.println(p.getName()));

        System.out.println("\nNames Ending with 'r':");
        session6.createQuery(
                "FROM Product p WHERE p.name LIKE '%r'", Product.class)
                .getResultList()
                .forEach(p -> System.out.println(p.getName()));

        System.out.println("\nNames Containing 'top':");
        session6.createQuery(
                "FROM Product p WHERE p.name LIKE '%top%'", Product.class)
                .getResultList()
                .forEach(p -> System.out.println(p.getName()));

        System.out.println("\nNames with Exact 5 Characters:");
        session6.createQuery(
                "FROM Product p WHERE LENGTH(p.name) = 5", Product.class)
                .getResultList()
                .forEach(p -> System.out.println(p.getName()));

        session6.close();

        HibernateUtil.getSessionFactory().close();
    }
}
