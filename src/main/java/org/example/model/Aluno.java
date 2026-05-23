package org.example.model;

public class Aluno {

    private String rgm;
    private String nome;
    private String curso;
    private String email;
    private String telefone;
    private String endereco;
    private String dataNascimento;
    private String cpf;
    private String municipio;
    private String uf;
    private String campus;
    private String periodo;

    public Aluno() {}

    public String getRgm()                   { return rgm; }
    public void   setRgm(String rgm)         { this.rgm = rgm; }

    public String getNome()                  { return nome; }
    public void   setNome(String nome)       { this.nome = nome; }

    public String getCurso()                 { return curso; }
    public void   setCurso(String curso)     { this.curso = curso; }

    public String getEmail()                 { return email; }
    public void   setEmail(String email)     { this.email = email; }

    public String getTelefone()              { return telefone; }
    public void   setTelefone(String t)      { this.telefone = t; }

    public String getEndereco()              { return endereco; }
    public void   setEndereco(String e)      { this.endereco = e; }

    public String getDataNascimento()        { return dataNascimento; }
    public void   setDataNascimento(String d){ this.dataNascimento = d; }

    public String getCpf()                   { return cpf; }
    public void   setCpf(String cpf)         { this.cpf = cpf; }

    public String getMunicipio()             { return municipio; }
    public void   setMunicipio(String m)     { this.municipio = m; }

    public String getUf()                    { return uf; }
    public void   setUf(String uf)           { this.uf = uf; }

    public String getCampus()                { return campus; }
    public void   setCampus(String campus)   { this.campus = campus; }

    public String getPeriodo()               { return periodo; }
    public void   setPeriodo(String periodo) { this.periodo = periodo; }
}
