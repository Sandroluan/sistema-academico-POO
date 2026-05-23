package org.example.model;

public class NotaFalta {

    private String rgm;
    private String disciplina;
    private String semestre;
    private double nota;
    private int    faltas;

    public NotaFalta() {}

    public String getRgm()                   { return rgm; }
    public void   setRgm(String rgm)         { this.rgm = rgm; }

    public String getDisciplina()            { return disciplina; }
    public void   setDisciplina(String d)    { this.disciplina = d; }

    public String getSemestre()              { return semestre; }
    public void   setSemestre(String s)      { this.semestre = s; }

    public double getNota()                  { return nota; }
    public void   setNota(double nota)       { this.nota = nota; }

    public int  getFaltas()                  { return faltas; }
    public void setFaltas(int faltas)        { this.faltas = faltas; }
}
