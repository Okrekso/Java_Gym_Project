package servlets.dbOps;

import database.GymDB;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DBDropSubmitServlet")
public class DBDropSubmitServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        new GymDB().dropCurrentDB();
        request.setAttribute("drop success", true);
        request.getRequestDispatcher("/DBDrop.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/DBDrop.jsp").forward(request, response);
    }
}
