package com.github.thomasirio;

import com.github.thomasirio.exception.FieldValidationException;
import com.github.thomasirio.model.Regista;
import com.github.thomasirio.model.ModelList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Map;

/**
 * Created by pcthomas on 04/11/2014.
 */
public class RegistiResource extends Controller  {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ModelList registi;
        Map parameters = request.getParameterMap();
        try {
            if (parameters.size() == 0)
                registi = Regista.all(Regista.class);
            else {
                ArrayList<String> keys = new ArrayList<String>();
                ArrayList<Object> values = new ArrayList<Object>();
                Enumeration<String> keysEnum = request.getParameterNames();

                while (keysEnum.hasMoreElements()) {
                    String key = keysEnum.nextElement();
                    keys.add(key);
                    values.add(this.getValue(Regista.class, key, request));
                }

                registi = Regista.where(Regista.class, keys, values, null, null);
            }

            PrintWriter writer = response.getWriter();
            writer.println(registi.toJson());
        } catch (FieldValidationException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
