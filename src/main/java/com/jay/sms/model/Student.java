package com.jay.sms.model;

/**
 * A "model" (also called POJO / entity) — a plain Java class that mirrors
 * one row of the students table. Every field maps to one column.
 * This is the "M" in MVC. It carries data between the DAO, servlet and JSP.
 */
public class Student {
    private int id;
    private String name;
    private String email;
    private String course;

    public Student() {}

    public Student(int id, String name, String email, String course) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.course = course;
    }

    // Getters/setters — JSP and Gson use these to read/write the fields.
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }
}
