package com.github.thomasirio.model;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by pcthomas on 30/10/2014.
 */

@Entity
public class Film extends JsonObject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long idFilm;
    @Column(nullable = false)
    private String titolo;
    @Column(nullable = false)
    private Integer quantita;
    @Column(nullable = false)
    private String genere;
    @Column(nullable = false)
    private Float prezzo;

    @OneToOne
    @JoinColumn(nullable = false)
    private Regista regista;          //relazione di 1 a molti mappato in java
    @ManyToMany(mappedBy = "films", fetch = FetchType.EAGER)
    private transient Set<Utente> utentes;
    @ManyToMany(mappedBy = "films", fetch = FetchType.EAGER)
    private transient Set<Attore> attores;

    // Constructors
    /** default constructor */
    public Film(){}

    /** minimal constructor */
    public Film(Long idFilm){
        this.idFilm=idFilm;
    }

    /** full constructor */
    public Film(Long idFilm,String titolo,Integer quantita,String genere, Float prezzo,
                Regista regista,Set<Utente> utentes,Set<Attore> attores){
        this.idFilm=idFilm;
        this.titolo=titolo;
        this.quantita=quantita;
        this.genere=genere;
        this.prezzo=prezzo;
        this.regista=regista;
        this.utentes=utentes;
        this.attores=attores;
    }

    // Metodi
    public Long getIdFilm() {
        return idFilm;
    }

    public void setIdFilm(Long idFilm) {
        this.idFilm = idFilm;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public Integer getQuantita() {
        return quantita;
    }

    public void setQuantita(Integer quantita) {
        this.quantita = quantita;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public Float getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(Float prezzo) {
        this.prezzo = prezzo;
    }

    public Regista getRegista() {
        return regista;
    }

    public void setRegista(Regista regista) {
        this.regista = regista;
    }

    public Set<Utente> getUtentes() {
        return utentes;
    }

    public void setUtentes(Set<Utente> utentes) {
        this.utentes = utentes;
    }

    public Set<Attore> getAttores() {
        return attores;
    }

    public void setAttores(Set<Attore> attores) {
        this.attores = attores;
    }
}
