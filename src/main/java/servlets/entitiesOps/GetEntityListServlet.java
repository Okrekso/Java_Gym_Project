package servlets.entitiesOps;

import database.DBEntity;
import database.GymDB;
import database.IDBEntity;
import logic.gym.Info;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "geEntityListServlet")
public class GetEntityListServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tableID = (String)request.getParameter("entity");
        GymDB db = new GymDB();

        DBEntity dbEntity = db.getEmptyEntity(tableID);
        List<IDBEntity> dbEntities = null;
        try {
            dbEntities = db.getFromEntityTable(dbEntity);
        } catch (SQLException e) {
            request.setAttribute("error", "unable to get");
        }
        if(dbEntities!=null && request.getParameter("selected")!=null)
            request.setAttribute("selected", dbEntities.stream()
                    .filter(idbEntity -> idbEntity.getEntityIDValue() == Integer.parseInt(request.getParameter("selected")))
                    .collect(Collectors.toList()).get(0)
            );
        request.setAttribute("entities", dbEntities);
        request.setAttribute("templateEntity", dbEntity);
        request.getRequestDispatcher("/GetEntityList.jsp").forward(request, response);
    }
}
