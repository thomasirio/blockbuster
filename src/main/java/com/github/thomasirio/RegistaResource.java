package com.github.thomasirio;

import com.github.thomasirio.model.Regista;
import com.github.thomasirio.exception.FieldValidationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by pcthomas on 31/10/2014.
 */

public class RegistaResource extends Controller {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Regista regista = Regista.validate(Regista.class, request.getParameter("id"));
            if(regista == null)
                throw new FieldValidationException();

            PrintWriter writer = response.getWriter();
            writer.println(regista.toJson());
        } catch (FieldValidationException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String json = this.readBody(request.getReader());
            Regista nuovoRegista = Regista.fromJson(Regista.class, json);
            System.out.println(nuovoRegista.toJson());
            nuovoRegista.save();

            PrintWriter writer = response.getWriter();
            writer.println(nuovoRegista.toJson());
        } catch(FieldValidationException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String json = this.readBody(request.getReader());
            Regista nuovoRegista = Regista.fromJson(Regista.class, json);
            nuovoRegista.update();
            PrintWriter writer = response.getWriter();
            writer.println(nuovoRegista.toJson());
        } catch(FieldValidationException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Regista regista = Regista.validate(Regista.class, request.getParameter("id"));
            regista.delete();
        } catch (FieldValidationException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

}
