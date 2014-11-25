package com.github.thomasirio.model;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by pcThomas on 30/10/2014.
 */

@Entity
public class Attore extends JsonObject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long idAttore;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = true)
    private String cognome;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Film> films;

    // Constructors
    /** default constructor */
    public Attore(){}

    /** minimal constructor */
    public Attore(Long idAttore){
        this.idAttore=idAttore;
    }

    /** full constructor */
    public Attore(Long idAttore,String nome,String cognome, Set<Film> films){
        this.idAttore=idAttore;
        this.nome=nome;
        this.cognome=cognome;
        this.films=films;
    }

    //Metodi
    public Long getIdAttore() {
        return idAttore;
    }

    public void setIdAttore(Long idAttore) {
        this.idAttore = idAttore;
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

    public Set<Film> getFilms() {
        return films;
    }

    public void setFilms(Set<Film> films) {
        this.films = films;
    }

}