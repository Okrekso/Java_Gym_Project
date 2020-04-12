package servlets.entitiesOps;

import database.DBEntity;
import database.GymDB;
import database.IDBEntity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

@WebServlet(name = "EditEntityServlet")
public class EditEntityServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GymDB db = new GymDB();
        String tableID = request.getParameter("entity").toLowerCase();
        try {
            IDBEntity entity = db.getFromEntityTableById(db.getFactoryByTableName(tableID),
                    Integer.parseInt(request.getParameter("id"))).get(0);
            request.setAttribute("editVariables", entity.getVariables());
            request.setAttribute("mode", "edit");
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
        }
        request.getRequestDispatcher("/AddEntity.jsp").forward(request, response);
    }
}
