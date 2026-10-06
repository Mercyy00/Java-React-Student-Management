<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>${empty student ? 'Add' : 'Edit'} Student</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; color: #222; }
        form { max-width: 400px; }
        label { display: block; margin-top: 14px; font-weight: bold; }
        input { width: 100%; padding: 8px; margin-top: 4px; box-sizing: border-box; }
        .actions { margin-top: 20px; }
        button { background: #27ae60; color: #fff; border: none; padding: 10px 18px; border-radius: 4px; cursor: pointer; }
        a { margin-left: 10px; }
    </style>
</head>
<body>
    <%-- One form does BOTH add and edit. If a "student" exists we pre-fill it
         and send a hidden id, so the servlet knows to UPDATE instead of INSERT. --%>
    <h1>${empty student ? 'Add' : 'Edit'} Student</h1>

    <form action="students" method="post">
        <input type="hidden" name="id" value="${student.id}"/>

        <label>Name</label>
        <input type="text" name="name" value="${student.name}" required/>

        <label>Email</label>
        <input type="email" name="email" value="${student.email}" required/>

        <label>Course</label>
        <input type="text" name="course" value="${student.course}" required/>

        <div class="actions">
            <button type="submit">Save</button>
            <a href="students">Cancel</a>
        </div>
    </form>
</body>
</html>
