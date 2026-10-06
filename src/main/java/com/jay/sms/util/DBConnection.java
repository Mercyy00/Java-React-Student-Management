package com.jay.sms.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The one place that knows HOW to open a JDBC connection to MySQL.
 * Every DAO calls DBConnection.getConnection() instead of repeating this.
 *
 * The 4 things JDBC always needs:
 *   1. The driver on the classpath (mysql-connector-j, added in pom.xml)
 *   2. A JDBC URL:  jdbc:mysql://host:port/databaseName
 *   3. A username
 *   4. A password
 */
public class DBConnection {

    // studentdb must match the database created in db/schema.sql
    private static final String URL =
        "jdbc:mysql://localhost:3306/studentdb?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    // XAMPP/MariaDB default: root has an EMPTY password. If you later set one, put it here.
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        try {
            // Loads the MySQL driver class. Not strictly required on modern JDBC,
            // but it's the classic line every tutorial shows — good to know.
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found. Is the dependency in pom.xml?", e);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
