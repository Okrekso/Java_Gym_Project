package servlets.entitiesOps;

import database.GymDB;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "geEntityListServlet")
public class geEntityListServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String entity = (String)request.getAttribute("entity");
        request
                .setAttribute("entities", new GymDB().)
                .getRequestDispatcher("/getEntityList.jsp").forward(request, response);
    }
}
