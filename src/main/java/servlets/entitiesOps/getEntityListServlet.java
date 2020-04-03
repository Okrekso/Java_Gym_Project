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
public class getEntityListServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String entity = (String)request.getParameter("entity");
        GymDB db = new GymDB();
        List<DBEntity> findDBEntities = db.getDBentities().stream()
                .filter(dbEntity -> dbEntity.getTableID().toLowerCase().equals(entity.toLowerCase())).collect(Collectors.toList());
        DBEntity dbEntity = findDBEntities.get(0);
        List<IDBEntity> dbEntities = null;
        try {
            dbEntities = db.getFromEntityTable(dbEntity);
        } catch (SQLException e) {
            request.setAttribute("error", "unable to get");
        }
        request.setAttribute("entities", dbEntities);
        request.getRequestDispatcher("/getEntityList.jsp").forward(request, response);
    }
}
