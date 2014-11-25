package com.github.thomasirio.model;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by pcthomas on 30/10/2014.
 */

@Entity
public class Regista extends JsonObject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long idRegista;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String cognome;
    @Column(nullable = false)
    private String nazionalita;

    @OneToMany(mappedBy = "regista", fetch = FetchType.EAGER)
    private  transient Set<Film> films;

    // Constructors
    /** default constructor */
    public Regista(){}

    /** minimal constructor */
    public Regista(Long idRegista){
        this.idRegista=idRegista;
    }

    /** full constructor */
    public Regista(Long idRegista, String nome,String cognome,String nazionalita, Set<Film> films){
        this.idRegista=idRegista;
        this.nome=nome;
        this.cognome=cognome;
        this.nazionalita=nazionalita;
        this.films=films;
    }

    //Metodi
    public Long getIdRegista() {
        return idRegista;
    }

    public void setIdRegista(Long idRegista) {
        this.idRegista = idRegista;
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

    public String getNazionalita() {
        return nazionalita;
    }

    public void setNazionalita(String nazionalita) {
        this.nazionalita = nazionalita;
    }

    public Set<Film> getFilms() {
        return films;
    }

    public void setFilms(Set<Film> films) {
        this.films = films;
    }

}