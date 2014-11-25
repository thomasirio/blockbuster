package com.github.thomasirio;

import com.github.thomasirio.model.Attore;
import com.github.thomasirio.exception.FieldValidationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by pcthomas on 31/10/2014.
 */
public class AttoreResource extends Controller {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Attore attore = Attore.validate(Attore.class, request.getParameter("id"));
            if(attore == null)
                throw new FieldValidationException();

            PrintWriter writer = response.getWriter();
            writer.println(attore.toJson());
        } catch (FieldValidationException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String json = this.readBody(request.getReader());
            Attore nuovoAttore = Attore.fromJson(Attore.class, json);
            System.out.println(nuovoAttore.toJson());
            nuovoAttore.save();

            PrintWriter writer = response.getWriter();
            writer.println(nuovoAttore.toJson());
        } catch(FieldValidationException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String json = this.readBody(request.getReader());
            Attore nuovoAttore = Attore.fromJson(Attore.class, json);
            nuovoAttore.update();
            PrintWriter writer = response.getWriter();
            writer.println(nuovoAttore.toJson());
        } catch(FieldValidationException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Attore attore = Attore.validate(Attore.class, request.getParameter("id"));
            attore.delete();
        } catch (FieldValidationException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

}
