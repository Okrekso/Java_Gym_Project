package servlets.entitiesOps;

import database.DBEntity;
import database.GymDB;
import database.IDBEntityFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet(name = "EditEntitySubmitServlet")
public class EditEntitySubmitServlet extends HttpServlet {
    static final Logger log = LogManager.getLogger(GetEntityListServlet.class);
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GymDB db = new GymDB();
        String entityName = request.getParameter("entity");
        Enumeration<String> params = request.getParameterNames();
        List<String> paramsString = new ArrayList<>();

        while (params.hasMoreElements()) {
            String param = params.nextElement();
            paramsString.add(param);
        }

        Map<String, String> mappedParams = paramsString.stream()
                .filter(param -> !param.toLowerCase().equals("entity"))
                .collect(Collectors.toMap(param->param, param->request.getParameter(param)));

        try {
            IDBEntityFactory factory = db.getDBEntityFactories().stream()
                    .filter(idbEntityFactory -> idbEntityFactory
                            .create().getTableID().toLowerCase().equals(entityName.toLowerCase()))
                    .collect(Collectors.toList()).get(0);
            mappedParams.put(factory.create().getEntityID().getTitle(), request.getParameter("id"));

            if(factory.create(mappedParams).update())
                request.setAttribute("successCode", "e-success");
            else
                request.setAttribute("successCode", "error");
//            logging edit
            log.info(String.format("edit of id:%s of table %s: %s", request.getParameter("id"),
                    factory.create().getTableID(),
                    request.getAttribute("successCode")));

        } catch (NullPointerException | ParseException ex) {
            if(ex.getClass() == NullPointerException.class)
                log.error("error! No such entity Factory");
            if(ex.getClass() == ParseException.class)
                log.error("invalid date format!");
            request.setAttribute("successCode", "error");
        }
        finally {
            request.getRequestDispatcher("/AddEntity.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
