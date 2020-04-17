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
import java.lang.reflect.Parameter;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet(name = "AddEntitySubmitServlet")
public class AddEntitySubmitServlet extends HttpServlet {
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

            if(db.addToTable(factory.create(mappedParams)))
                request.setAttribute("successCode", "success");
            else
                request.setAttribute("successCode", "error");
            //            logging add
            log.info(String.format("adding new entity to table %s",
                    factory.create().getTableID(),
                    request.getAttribute("successCode")));
            log.info("params:");
            log.info(mappedParams);
        } catch (Exception ex) {
            log.error(ex);
            request.setAttribute("successCode", "error");
        }
        finally {
            request.getRequestDispatcher("/AddEntity.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
