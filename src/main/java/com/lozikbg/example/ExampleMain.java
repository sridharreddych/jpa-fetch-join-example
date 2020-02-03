package com.lozikbg.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class ExampleMain {
    private static EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("example-unit");

    public static void main(String[] args) {
        try {
            persistEmployees();
            executeQuery();
        } finally {
            entityManagerFactory.close();
        }
    }

    public static void persistEmployees() {
        Task task1 = new Task("Coding", "Denise");
        Task task2 = new Task("Refactoring", "Rose");
        Task task3 = new Task("Designing", "Denise");
        Task task4 = new Task("Documentation", "Mike");

        Employee employee1 = Employee.create("Diana", task1, task3);
        Employee employee2 = Employee.create("Mike", task2, task4);
        Employee employee3 = Employee.create("Tim", task3, task4);
        Employee employee4 = Employee.create("Jack");

        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(employee1);
        em.persist(employee2);
        em.persist(employee3);
        em.persist(employee4);
        em.getTransaction().commit();
        em.close();
        System.out.println("-- Employee persisted --");
        System.out.println(employee1);
        System.out.println(employee2);
        System.out.println(employee3);
        System.out.println(employee4);
    }

    private static void executeQuery() {
        System.out.println("-- executing query --");
        EntityManager em = entityManagerFactory.createEntityManager();
        Query query = em.createQuery("SELECT DISTINCT e FROM Employee e INNER JOIN e.tasks t");
        List<Employee> resultList = query.getResultList();
        for (Employee employee : resultList) {
            System.out.println(employee.getName() + " - " + employee.getTasks());
        }
        em.close();
    }
}