package servlets.entitiesOps;

import database.DBEntity;
import database.GymDB;
import database.IDBEntity;
import database.IDBEntityFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

@WebServlet(name = "DeleteEntitySubmitServlet")
public class DeleteEntitySubmitServlet extends HttpServlet {
    static final Logger log = LogManager.getLogger(GetEntityListServlet.class);
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer entityID = Integer.parseInt(request.getParameter("entityID"));
        String tableID = request.getParameter("entity");
        GymDB db = new GymDB();

        IDBEntityFactory entity = db.getFactoryByTableName(tableID);
        List<IDBEntity> foundDBEntity;
        try {
            foundDBEntity = db.getFromEntityTable(entity).stream()
                    .filter(idbEntity -> idbEntity.getEntityIDValue() == entityID)
                    .collect(Collectors.toList());

        } catch (SQLException | ParseException ex) {
            request.setAttribute("success", false);
            log.error(ex);
            request.getRequestDispatcher("/DeleteEntitySubmit.jsp").forward(request, response);
            return;

        }

        if(foundDBEntity!=null && foundDBEntity.size()>0 && foundDBEntity.get(0).delete()) {
            request.setAttribute("success", true);
            log.info(String.format("deleted id:%s successfully!", request.getParameter("entityID")));
        } else {
            request.setAttribute("success", false);
            log.error(String.format("deleted id:%s failure!", request.getParameter("entityID")));
        }
        request.getRequestDispatcher("/DeleteEntitySubmit.jsp").forward(request, response);
    }
}
