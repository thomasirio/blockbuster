package com.github.thomasirio.model;

import javax.persistence.*;

/**
 * Created by pcthomas on 30/10/2014.
 */

@Entity
public class Profilo extends JsonObject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long idProfilo;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String cognome;
    @Column(nullable = false)
    private String indirizzo;
    @Column(nullable = false)
    private String cartaCredito;

    @OneToOne
    @JoinColumn(nullable = true)
    private Utente utente;

    // Constructors
    /** default constructor */
    public Profilo(){}

    /** minimal constructor */
    public Profilo(Long idProfilo){
        this.idProfilo=idProfilo;
    }

    /** full constructor */
    public Profilo(Long idProfilo, String nome,String cognome,String indirizzo, String cartaCredito,Utente utente){
        this.idProfilo=idProfilo;
        this.nome=nome;
        this.cognome=cognome;
        this.indirizzo=indirizzo;
        this.cartaCredito=cartaCredito;
        this.utente=utente;
    }


    //Metodi
    public Long getIdProfilo() {
        return idProfilo;
    }

    public void setIdProfilo(Long idProfilo) {
        this.idProfilo = idProfilo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getCartaCredito() {
        return cartaCredito;
    }

    public void setCartaCredito(String cartaCredito) {
        this.cartaCredito = cartaCredito;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

}