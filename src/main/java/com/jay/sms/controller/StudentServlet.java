package com.jay.sms.controller;

import com.jay.sms.dao.StudentDAO;
import com.jay.sms.model.Student;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * The Controller (the "C" in MVC) for the JSP pages.
 *
 * @WebServlet maps this servlet to the URL /students — so a browser request to
 * http://localhost:8080/students comes HERE. No web.xml entry needed for this.
 *
 * Flow for every request:
 *   Browser --> this Servlet --> StudentDAO (JDBC/MySQL) --> put data in request
 *           --> forward to a JSP --> JSP renders HTML --> back to browser
 *
 * We use one "action" parameter to decide what to do (list / new / edit / delete).
 * GET  = show a page.   POST = change data, then redirect back to the list.
 */
@WebServlet("/students")
public class StudentServlet extends HttpServlet {

    private final StudentDAO dao = new StudentDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "new":     // show the empty "add" form
                forward(req, resp, "/WEB-INF/views/form.jsp");
                break;
            case "edit":    // show the form pre-filled with one student
                int editId = Integer.parseInt(req.getParameter("id"));
                req.setAttribute("student", dao.getById(editId));
                forward(req, resp, "/WEB-INF/views/form.jsp");
                break;
            case "delete":
                dao.delete(Integer.parseInt(req.getParameter("id")));
                resp.sendRedirect("students");   // redirect -> avoids re-delete on refresh
                break;
            default:        // "list"
                List<Student> students = dao.getAll();
                req.setAttribute("students", students);   // hand data to the JSP
                forward(req, resp, "/WEB-INF/views/list.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        // Read the form fields the user submitted
        String idParam = req.getParameter("id");
        Student s = new Student();
        s.setName(req.getParameter("name"));
        s.setEmail(req.getParameter("email"));
        s.setCourse(req.getParameter("course"));

        if (idParam == null || idParam.isEmpty()) {
            dao.insert(s);                       // no id -> new student
        } else {
            s.setId(Integer.parseInt(idParam));  // has id -> update existing
            dao.update(s);
        }
        resp.sendRedirect("students");           // go back to the list
    }

    private void forward(HttpServletRequest req, HttpServletResponse resp, String jsp)
            throws ServletException, IOException {
        req.getRequestDispatcher(jsp).forward(req, resp);
    }
}
