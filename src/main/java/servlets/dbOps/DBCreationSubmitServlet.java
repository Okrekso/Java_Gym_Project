package servlets.dbOps;

import database.GymDB;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "DBCreationServlet")
public class DBCreationSubmitServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(!new GymDB().isDBcreated()) {
            new GymDB().build();
            request.setAttribute("message", "DB creation in progress...");
        } else {
            request.setAttribute("message", "DB already exist.");
        }
        request.getRequestDispatcher("/DBCreation.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        request.setAttribute("message", "incorrect request URL!");
        request.getRequestDispatcher("/DBCreation.jsp").forward(request, response);
    }
}
