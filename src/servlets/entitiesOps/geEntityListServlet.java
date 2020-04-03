package servlets.entitiesOps;

import database.DBEntity;
import database.GymDB;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "geEntityListServlet")
public class geEntityListServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String entity = (String)request.getParameter("entity");
//        request.setAttribute("entities", new GymDB().getFromTable(entity.getTableID()));
        request.setAttribute("entities", new GymDB().);
        request.getRequestDispatcher("/getEntityList.jsp").forward(request, response);
    }
}
