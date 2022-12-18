package code;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

@WebServlet("/students")
public class StudentAdd extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Connection conn = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3311/university", "root", "root");

            Statement s = conn.createStatement();
            List<Student> students = new LinkedList<Student>();

            request.setAttribute("students", students);
            request.getRequestDispatcher("index.jsp").forward(request, response);
            //response.sendRedirect("index.jsp");
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.getMessage();
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter pw = null;

        try {
            pw = response.getWriter();
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace(pw);
            pw.print(ex.getMessage());
        }

        Connection conn = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3311/university", "root", "root");

            if (request.getParameter("name") != null && request.getParameter("surname") != null) {
                PreparedStatement ps = (PreparedStatement)conn.prepareStatement("INSERT INTO student (name, surname, age, email, `group`, faculty) VALUES (?, ?, ?, ?, ?, ?);");

                ps.setString(1, request.getParameter("name"));
                ps.setString(2, request.getParameter("surname"));
                ps.setInt(3, Integer.parseInt(request.getParameter("age")));
                ps.setString(4, request.getParameter("email"));
                ps.setString(5, request.getParameter("group"));
                ps.setString(6, request.getParameter("faculty"));
                ps.executeUpdate();
            }

            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM student;");
            List<Student> students = new LinkedList<Student>();

            while (rs.next())
                students.add(new Student(rs.getString(2), rs.getString(3), ((Integer)rs.getInt(4)).toString(), rs.getString(5), rs.getString(6), rs.getString(7)));

            request.setAttribute("students", students);
            request.getRequestDispatcher("index.jsp").forward(request, response);
            //response.sendRedirect("index.jsp");
        } catch (SQLException ex) {
            pw.print(ex.getMessage());
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.getMessage();
                }
            }
        }
    }

}
