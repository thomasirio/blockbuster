package com.github.thomasirio;

import com.github.thomasirio.exception.FieldValidationException;
import com.github.thomasirio.model.Film;
import com.github.thomasirio.model.Utente;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by pcthomas on 04/11/2014.
 */
public class NoleggioResource extends Controller {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Utente utente = Utente.validate(Utente.class, request.getParameter("idutente"));
            if(utente == null)
                throw new FieldValidationException();

            Film film = Film.validate(Film.class, request.getParameter("idfilm"));
            if(film == null)
                throw new FieldValidationException();

            utente.getFilms().add(film);
            utente.update();

            PrintWriter writer = response.getWriter();
            writer.println(utente.toJson());
        } catch(FieldValidationException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

}
