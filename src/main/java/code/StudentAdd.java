package code;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@WebServlet("/students")
public class StudentAdd extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<Student> students = (List<Student>)session.getAttribute("students");

        if(students == null) {
            students = new LinkedList<>();
            session.setAttribute("students", students);
        }
        response.sendRedirect("index.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<Student> students = (List<Student>)session.getAttribute("students");

        if(students == null) {
            students = new LinkedList<>();
            session.setAttribute("students", students);
        }

        if(!request.getParameter("name").isBlank() || !request.getParameter("surname").isBlank()) {
            students.add(new Student(request.getParameter("name"),
                    request.getParameter("surname"),
                    request.getParameter("age"),
                    request.getParameter("email"),
                    request.getParameter("group"),
                    request.getParameter("faculty")));
        }

        response.sendRedirect("index.jsp");
    }

}
