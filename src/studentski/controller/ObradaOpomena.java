/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentski.controller;

import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import studentski.model.Opomena;
import studentski.model.Student;
import studentski.model.StudentskiDom;
import studentski.pomocno.HibernateUtil;

/**
 *
 * @author Ivan
 */
public class ObradaOpomena {
    
    private Obrada<Opomena> obrada;
    
    public ObradaOpomena(){
        obrada = new Obrada<>();
    }

    public List<Opomena> getNeplaceneOpomene(StudentskiDom studentskiDom, Date pocetakTrenutnogMjeseca, Date krajTrenutnogMjeseca) {
        Session s = HibernateUtil.getSession();
        s.clear();
        return s.createQuery(
                "SELECT op " +
                        "from Opomena as op " +
                        "inner join Racun as r on r.sifra = op.racun " +
                        "inner join Student as st on st.sifra = r.student " +
                        "inner join Soba as so on so.sifra = st.soba " +
                        "inner join Paviljon as pa on pa.sifra = so.paviljon " +
                        "inner join StudentskiDom as sd on sd.sifra = pa.studentskiDom " +
                        "where sd = :uvjet " +
                        "and op.obrisano = false " +
                        "and op.placenoNakonOpomene=false " +
                        "and op.izdavanjeOpomene between :startDate and :endDate")
                .setString("uvjet", String.valueOf(studentskiDom.getSifra()))
                .setParameter("startDate", pocetakTrenutnogMjeseca)
                .setParameter("endDate", krajTrenutnogMjeseca)
                .list();
    }

    public Opomena getOpomena(Student student, Date pocetakTrenutnogMjeseca, Date krajTrenutnogMjeseca) {
        Session s = HibernateUtil.getSession();
        s.clear();
        List<Opomena> listaOpomena = s.createQuery(
                " FROM Opomena a WHERE a.obrisano=false AND a.placenoNakonOpomene=false "
                        + " AND a.izdavanjeOpomene BETWEEN :stDate AND :edDate ")
                .setParameter("stDate", pocetakTrenutnogMjeseca)
                .setParameter("edDate", krajTrenutnogMjeseca)
                .list();
        Opomena opomena = new Opomena();
        for (Opomena o : listaOpomena) {
            if(student.getSifra() != o.getRacun().getStudent().getSifra()){
                continue;
            }
            opomena = o;
        }
        return opomena;
    }

    public boolean provjeraOp(Student student, Date pocetakTrenutnogMjeseca, Date krajTrenutnogMjeseca) {
        Session s = HibernateUtil.getSession();
        s.clear();
        List<Opomena> listaOpomena = s.createQuery(
                " FROM Opomena a WHERE a.obrisano=false "
                        + " AND a.izdavanjeOpomene BETWEEN :stDate AND :edDate ")
                .setParameter("stDate", pocetakTrenutnogMjeseca)
                .setParameter("edDate", krajTrenutnogMjeseca)
                .list();
        for (Opomena opomena : listaOpomena) {
            if(opomena.getRacun().getStudent().getSifra() == student.getSifra()){
                return false;
            }
        }
        return true;
    }

    public Opomena pronadjiOpomenu(Student student, Date pocetakTrenutnogMjeseca, Date krajTrenutnogMjeseca) {
        Opomena o = new Opomena();
        List<Opomena> opomena = HibernateUtil.getSession().createQuery(
                "FROM Opomena a WHERE a.obrisano=false AND a.placenoNakonOpomene=false "
                        + " AND a.izdavanjeOpomene BETWEEN :stDate AND :edDate ")
                .setParameter("stDate", pocetakTrenutnogMjeseca)
                .setParameter("edDate", krajTrenutnogMjeseca)
                .list();
        for (Opomena op : opomena) {
            if(op.getRacun().getStudent().getSifra()!=student.getSifra()){
                continue;
            }
            o = op;
        }
        return o;
    }
    
    
}
