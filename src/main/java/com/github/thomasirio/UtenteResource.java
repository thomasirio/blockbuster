package com.github.thomasirio;

import com.github.thomasirio.model.Utente;
import com.github.thomasirio.exception.FieldValidationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by pcthomas on 30/10/2014.
 */
public class UtenteResource extends Controller  {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Utente utente = Utente.validate(Utente.class, request.getParameter("id"));
            if(utente == null)
                throw new FieldValidationException();

            PrintWriter writer = response.getWriter();
            writer.println(utente.toJson());
        } catch (FieldValidationException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String json = this.readBody(request.getReader());
            Utente nuovoUtente = Utente.fromJson(Utente.class, json);
            System.out.println(nuovoUtente.toJson());
            nuovoUtente.save();

            PrintWriter writer = response.getWriter();
            writer.println(nuovoUtente.toJson());
        } catch(FieldValidationException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String json = this.readBody(request.getReader());
            Utente nuovoUtente = Utente.fromJson(Utente.class, json);
            nuovoUtente.update();
            PrintWriter writer = response.getWriter();
            writer.println(nuovoUtente.toJson());
        } catch(FieldValidationException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Utente utente = Utente.validate(Utente.class, request.getParameter("id"));
            utente.delete();
        } catch (FieldValidationException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

}
