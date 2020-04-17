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
import java.io.PrintWriter;

@WebServlet(name = "DBCreationServlet")
public class DBCreationSubmitServlet extends HttpServlet {
    static final Logger log = LogManager.getLogger(DBCreationSubmitServlet.class);
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(!new GymDB().isDBcreated()) {
            new GymDB().build();
            log.info("DB Creation process started");
            request.setAttribute("message", "DB creation in progress...");
        } else {
            request.setAttribute("message", "DB already exist.");
            log.info("DB already exist.");
        }
        request.getRequestDispatcher("/DBCreation.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        String error = "incorrect request URL!";
        request.setAttribute("message", error);
        log.error(error);
        request.getRequestDispatcher("/DBCreation.jsp").forward(request, response);
    }
}
