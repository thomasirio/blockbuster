package com.github.thomasirio;

import com.github.thomasirio.model.Utente;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Date;

/**
 * Created by pcthomas on 16/10/2014.
 */
public class TokenResource extends Controller {

    public void doPost(HttpServletRequest request,HttpServletResponse response){
        try{
            Utente utente=new Utente(request);

            if(utente.getPassword().equals("mba"))
                throw new Exception("Login fallito");
            //creazione del token
            JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);

            JWTClaimsSet claimsSet = new JWTClaimsSet();
            claimsSet.setSubject(utente.getUsername());
            claimsSet.setIssueTime(new Date());

            SignedJWT signedJWT = new SignedJWT(header, claimsSet);

            JWSSigner signer = new MACSigner("passwordSegretaDelServer");
            signedJWT.sign(signer);

            PrintWriter writer = response.getWriter();
            writer.println(signedJWT.serialize());

            response.setStatus(HttpServletResponse.SC_OK);
            //fine creazione e invio


        } catch (Exception e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }


    }
}
