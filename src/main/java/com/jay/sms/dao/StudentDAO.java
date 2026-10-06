package com.jay.sms.dao;

import com.jay.sms.model.Student;
import com.jay.sms.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO = Data Access Object. This class is the ONLY place that writes SQL.
 * It hides "how data is stored" from the rest of the app. The servlet just
 * calls dao.getAll() / dao.save(...) and never touches SQL itself.
 *
 * This is pure JDBC — the interview keyword. The core JDBC pattern is:
 *   1. Get a Connection
 *   2. Create a PreparedStatement with SQL (use ? placeholders — safe from SQL injection)
 *   3. Set the ? values, then executeQuery() (SELECT) or executeUpdate() (INSERT/UPDATE/DELETE)
 *   4. Read the ResultSet (for SELECT)
 *   5. Close everything  (try-with-resources does this automatically)
 */
public class StudentDAO {

    /** READ all — SELECT */
    public List<Student> getAll() {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT id, name, email, course FROM students ORDER BY id";

        // try-with-resources: conn, ps, rs are auto-closed at the end of the block
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {   // move row by row through the result
                list.add(new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("course")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load students", e);
        }
        return list;
    }

    /** READ one by id — used to pre-fill the edit form */
    public Student getById(int id) {
        String sql = "SELECT id, name, email, course FROM students WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);                 // fill the first ?
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Student(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("course"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load student " + id, e);
        }
        return null;
    }

    /** CREATE — INSERT */
    public void insert(Student s) {
        String sql = "INSERT INTO students (name, email, course) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, s.getName());
            ps.setString(2, s.getEmail());
            ps.setString(3, s.getCourse());
            ps.executeUpdate();               // executeUpdate for changes
        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert student", e);
        }
    }

    /** UPDATE */
    public void update(Student s) {
        String sql = "UPDATE students SET name = ?, email = ?, course = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, s.getName());
            ps.setString(2, s.getEmail());
            ps.setString(3, s.getCourse());
            ps.setInt(4, s.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update student", e);
        }
    }

    /** DELETE */
    public void delete(int id) {
        String sql = "DELETE FROM students WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete student " + id, e);
        }
    }
}
