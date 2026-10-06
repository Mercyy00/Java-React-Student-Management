<%-- 'taglib' imports JSTL so we can use <c:forEach>, <c:if> etc. --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Students</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; color: #222; }
        h1 { margin-bottom: 4px; }
        table { border-collapse: collapse; width: 100%; margin-top: 16px; }
        th, td { border: 1px solid #ccc; padding: 10px 12px; text-align: left; }
        th { background: #2c3e50; color: #fff; }
        tr:nth-child(even) { background: #f6f8fa; }
        .btn { padding: 6px 12px; text-decoration: none; border-radius: 4px; color: #fff; font-size: 14px; }
        .add { background: #27ae60; }
        .edit { background: #2980b9; }
        .del { background: #c0392b; }
    </style>
</head>
<body>
    <h1>Student Management System</h1>
    <p>Java + JDBC + Servlet + JSP (classic full-stack MVC)</p>

    <a class="btn add" href="students?action=new">+ Add Student</a>

    <table>
        <tr>
            <th>ID</th><th>Name</th><th>Email</th><th>Course</th><th>Actions</th>
        </tr>
        <%-- Loop over the "students" list the servlet put in the request.
             ${s.name} calls s.getName() automatically. --%>
        <c:forEach var="s" items="${students}">
            <tr>
                <td>${s.id}</td>
                <td>${s.name}</td>
                <td>${s.email}</td>
                <td>${s.course}</td>
                <td>
                    <a class="btn edit" href="students?action=edit&id=${s.id}">Edit</a>
                    <a class="btn del" href="students?action=delete&id=${s.id}"
                       onclick="return confirm('Delete this student?')">Delete</a>
                </td>
            </tr>
        </c:forEach>

        <c:if test="${empty students}">
            <tr><td colspan="5">No students yet. Click "Add Student".</td></tr>
        </c:if>
    </table>
</body>
</html>
