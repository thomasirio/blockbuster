package com.github.thomasirio;

import com.github.thomasirio.model.Profilo;
import com.github.thomasirio.exception.FieldValidationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by pcthomas on 30/10/2014.
 */

public class ProfiloResource extends Controller {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Profilo profilo = Profilo.validate(Profilo.class, request.getParameter("id"));
            if(profilo == null)
                throw new FieldValidationException();

            PrintWriter writer = response.getWriter();
            writer.println(profilo.toJson());
        } catch (FieldValidationException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String json = this.readBody(request.getReader());
            Profilo nuovoProfilo = Profilo.fromJson(Profilo.class, json);
            System.out.println(nuovoProfilo.toJson());
            nuovoProfilo.save();

            PrintWriter writer = response.getWriter();
            writer.println(nuovoProfilo.toJson());
        } catch(FieldValidationException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String json = this.readBody(request.getReader());
            Profilo nuovoProfilo = Profilo.fromJson(Profilo.class, json);
            nuovoProfilo.update();
            PrintWriter writer = response.getWriter();
            writer.println(nuovoProfilo.toJson());
        } catch(FieldValidationException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Profilo profilo = Profilo.validate(Profilo.class, request.getParameter("id"));
            profilo.delete();
        } catch (FieldValidationException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

}