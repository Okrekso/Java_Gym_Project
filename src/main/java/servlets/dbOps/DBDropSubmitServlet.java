package servlets.dbOps;

import database.GymDB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import servlets.entitiesOps.GetEntityListServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DBDropSubmitServlet")
public class DBDropSubmitServlet extends HttpServlet {
    static final Logger log = LogManager.getLogger(GetEntityListServlet.class);
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(new GymDB().dropCurrentDB()) {
            request.setAttribute("drop success", true);
            log.info("database dropped successfully!");
        } else log.error("database drop failure!");

        request.getRequestDispatcher("/DBDrop.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/DBDrop.jsp").forward(request, response);
    }
}
