package servlets.entitiesOps;

import database.DBEntity;
import database.GymDB;
import database.IDBEntityFactory;

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GymDB db = new GymDB();

        String entityName = request.getParameter("entity");

        DBEntity emptyEntity = db.getEmptyEntity(entityName);
        Class entityClass = emptyEntity.getClass();

        Enumeration<String> params = request.getParameterNames();
        Constructor constructor = Arrays.asList(entityClass.getConstructors()).get(0);
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

            db.addToTable(factory.create(mappedParams));
            request.setAttribute("successCode", "success");
        } catch (NullPointerException | ParseException ex) {
            if(ex.getClass() == NullPointerException.class)
                System.out.println("error! No such entity Factory");
            if(ex.getClass() == ParseException.class)
                System.out.println("invalid date format!");
            request.setAttribute("successCode", "error");
        }
        finally {
            request.getRequestDispatcher("/AddEntity.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
