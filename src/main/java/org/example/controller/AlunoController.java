package org.example.controller;

import org.example.dao.AlunoDAO;
import org.example.model.Aluno;

import java.sql.SQLException;

public class AlunoController {

    private final AlunoDAO alunoDAO = new AlunoDAO();

    public String salvarAluno(Aluno aluno) {
        try {
            if (alunoDAO.existeRgm(aluno.getRgm())) {
                return "AVISO: Já existe um aluno cadastrado com este RGM. Use o botão ALTERAR.";
            }
            alunoDAO.inserir(aluno);
            return "SUCESSO: Aluno cadastrado com sucesso!";
        } catch (SQLException e) {
            e.printStackTrace();
            return "ERRO: " + e.getMessage();
        }
    }

    public String alterarAluno(Aluno aluno) {
        try {
            if (!alunoDAO.existeRgm(aluno.getRgm())) {
                return "AVISO: Aluno não encontrado. Não é possível alterar um registro inexistente.";
            }
            alunoDAO.atualizar(aluno);
            return "SUCESSO: Dados do aluno atualizados com sucesso!";
        } catch (SQLException e) {
            e.printStackTrace();
            return "ERRO: " + e.getMessage();
        }
    }

    public String excluirAluno(String rgm) {
        try {
            if (!alunoDAO.existeRgm(rgm)) {
                return "AVISO: Aluno não encontrado para exclusão.";
            }
            alunoDAO.excluir(rgm);
            return "SUCESSO: Aluno excluído com sucesso!";
        } catch (SQLException e) {
            e.printStackTrace();
            return "ERRO: " + e.getMessage();
        }
    }

    public Aluno buscarPorRgm(String rgm) throws SQLException {
        return alunoDAO.buscarPorRgm(rgm);
    }
}
