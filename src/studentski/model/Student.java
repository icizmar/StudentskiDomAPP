/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentski.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Ivan
 */
@Entity
@Table
public class Student extends Osoba implements Serializable {
    
    private String brojUgovora;
    private String fakultet;
    
    @ManyToOne
    private Soba soba;
    
    @OneToMany(mappedBy = "student")
    private List<Racun> racuni = new ArrayList<>();
    
    public Student(){
        super();
    }
    
    public Student(String ime, String prezime){
        super(ime,prezime);
    }

    public List<Racun> getRacuni() {
        return racuni;
    }

    public void setRacuni(List<Racun> racuni) {
        this.racuni = racuni;
    }

    public String getBrojUgovora() {
        return brojUgovora;
    }

    public void setBrojUgovora(String brojUgovora) {
        this.brojUgovora = brojUgovora;
    }

    public Soba getSoba() {
        return soba;
    }

    public void setSoba(Soba soba) {
        this.soba = soba;
    }
    
    public String getFakultet() {
        return fakultet;
    }

    public void setFakultet(String fakultet) {
        this.fakultet = fakultet;
    }

    @Override
    public String toString() {
        return getIme() + " " + getPrezime();
    }
    
}
