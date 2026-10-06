package com.jay.sms.controller;

import com.google.gson.Gson;
import com.jay.sms.dao.StudentDAO;
import com.jay.sms.model.Student;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * A REST-style JSON endpoint at /api/students — this is what the REACT app calls.
 *
 * Same DAO, but instead of forwarding to a JSP we write JSON to the response.
 * This shows the "full stack" split: the SAME Java+JDBC backend can serve BOTH
 * server-rendered JSP pages AND a modern JavaScript frontend (React).
 *
 * GET    /api/students        -> list all as JSON
 * POST   /api/students        -> create (form fields name/email/course)
 * DELETE /api/students?id=5    -> delete one
 */
@WebServlet("/api/students")
public class StudentApiServlet extends HttpServlet {

    private final StudentDAO dao = new StudentDAO();
    private final Gson gson = new Gson();

    // CORS: lets the React dev server (port 5173) call this backend (port 8080)
    private void cors(HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) {
        cors(resp);                       // browsers send a preflight OPTIONS first
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        cors(resp);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        List<Student> students = dao.getAll();
        resp.getWriter().write(gson.toJson(students));   // Java List -> JSON array
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        cors(resp);
        Student s = new Student();
        s.setName(req.getParameter("name"));
        s.setEmail(req.getParameter("email"));
        s.setCourse(req.getParameter("course"));
        dao.insert(s);
        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        cors(resp);
        dao.delete(Integer.parseInt(req.getParameter("id")));
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
