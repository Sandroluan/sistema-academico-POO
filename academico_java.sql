DROP DATABASE IF EXISTS academico_java;
CREATE DATABASE academico_java;
USE academico_java;

CREATE TABLE tb_curso (
    pk_curso ENUM('Análise e Desenvolvimento de Sistemas', 'Gestão de TI', 'Ciência da Computação', 'Sistemas de Informação', 'Engenharia de Software'),
    pk_campus ENUM('Tatuapé'),
    pk_periodo ENUM('Matutino', 'Vespertino', 'Noturno'),
    PRIMARY KEY (pk_curso, pk_campus, pk_periodo)
);

CREATE TABLE tb_alunos(
	pk_rgm char(8) NOT NULL,
    cpf char(11) NOT NULL UNIQUE,
    nome varchar(100) NOT NULL,
    data_nasc date NOT NULL,
    endereco varchar(255) NOT NULL,
    municipio varchar(100) NOT NULL,
    uf char(2) NOT NULL,
    celular varchar(15),
    email varchar(100),
    fk_curso ENUM('Análise e Desenvolvimento de Sistemas', 'Gestão de TI', 'Ciência da Computação', 'Sistemas de Informação', 'Engenharia de Software'),
    fk_campus ENUM('Tatuapé'),
    fk_periodo ENUM('Matutino', 'Vespertino', 'Noturno'),
    PRIMARY KEY (pk_rgm),
    FOREIGN KEY (fk_curso, fk_campus, fk_periodo) REFERENCES tb_curso (pk_curso, pk_campus, pk_periodo)
);

CREATE TABLE tb_notas_faltas(
    fk_rgm CHAR(8) NOT NULL,
    disciplina VARCHAR(100) NOT NULL,
    semestre VARCHAR(10) NOT NULL,
    nota DECIMAL(4,2) NOT NULL,
    faltas INT NOT NULL,
    PRIMARY KEY (fk_rgm, disciplina, semestre),
    FOREIGN KEY (fk_rgm) REFERENCES tb_alunos (pk_rgm) ON DELETE CASCADE ON UPDATE CASCADE
);