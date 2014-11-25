package com.github.thomasirio;

import com.github.thomasirio.exception.FieldValidationException;
import com.github.thomasirio.model.Film;
import com.github.thomasirio.model.ModelList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by pcthomas on 31/10/2014.
 */
public class FilmsResource extends Controller {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ModelList films;
        Map parameters = request.getParameterMap();
        try {
            if (parameters.size() == 0)
                films = Film.all(Film.class);
            else {
                ArrayList<String> keys = new ArrayList<String>();
                ArrayList<Object> values = new ArrayList<Object>();
                Enumeration<String> keysEnum = request.getParameterNames();

                while (keysEnum.hasMoreElements()) {
                    String key = keysEnum.nextElement();
                    keys.add(key);
                    values.add(this.getValue(Film.class, key, request));
                }

                films = Film.where(Film.class, keys, values, null, null);
            }

            PrintWriter writer = response.getWriter();
            writer.println(films.toJson());
        } catch (FieldValidationException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

}
