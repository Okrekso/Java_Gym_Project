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
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "DeleteEntitySubmitServlet")
public class DeleteEntitySubmitServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer entityID = Integer.parseInt(request.getParameter("entityID"));
        String tableID = (String)request.getParameter("entity");
        GymDB db = new GymDB();

        DBEntity entity = db.getEmptyEntity(tableID);
        List<IDBEntity> foundDBEntity = null;
        try {
            foundDBEntity = db.getFromEntityTable(entity).stream()
                    .filter(idbEntity -> idbEntity.getEntityIDValue() == entityID)
                    .collect(Collectors.toList());

        } catch (SQLException e) {
            request.setAttribute("success", false);
            request.getRequestDispatcher("/deleteEntitySubmit.jsp").forward(request, response);
            return;

        }

        if(foundDBEntity!=null && foundDBEntity.size()>0 && foundDBEntity.get(0).delete())
            request.setAttribute("success", true);
        else
            request.setAttribute("success", false);

        request.getRequestDispatcher("/deleteEntitySubmit.jsp").forward(request, response);
    }
}
