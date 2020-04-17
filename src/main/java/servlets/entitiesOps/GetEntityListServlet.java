package servlets.entitiesOps;

import database.*;
import logic.gym.Info;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet(name = "geEntityListServlet")
public class GetEntityListServlet extends HttpServlet {
    static Logger log = LogManager.getLogger(GetEntityListServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tableID = (String)request.getParameter("entity");
        GymDB db = new GymDB();
        log.debug("start getting entities from " + tableID);

        IDBEntityFactory entityFactory = db.getFactoryByTableName(tableID);
        List<DBEntity> dbEntities = null;
        try {
            dbEntities = db.getFromEntityTable(entityFactory);
            log.debug("found " + dbEntities.size() + " entities");

        } catch (NullPointerException | SQLException | ParseException e) {
            request.setAttribute("error", "unable to get");
            log.error("error occupied while getting entities from DB: " + e.getMessage());
        }
        if(dbEntities!=null && request.getParameter("selected")!=null)
            request.setAttribute("selected", dbEntities.stream()
                    .filter(idbEntity -> idbEntity.getEntityIDValue() == Integer.parseInt(request.getParameter("selected")))
                    .collect(Collectors.toList()).get(0)
            );
        try {
            IDBContainRelative dbEntityContainRelative = (IDBContainRelative) entityFactory.create();
            request.setAttribute("containRelativeValue", dbEntityContainRelative);
        } catch (ClassCastException ex) {
            request.setAttribute("containRelativeValue", null);
        }
        request.setAttribute("entityFactory", entityFactory);
        request.setAttribute("entities", dbEntities);
        request.getRequestDispatcher("/GetEntityList.jsp").forward(request, response);
    }
}
