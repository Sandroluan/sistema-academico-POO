package org.example.controller;

import org.example.dao.NotaFaltaDAO;
import org.example.model.NotaFalta;

import java.sql.SQLException;
import java.util.List;

public class NotaFaltaController {

    private final NotaFaltaDAO dao = new NotaFaltaDAO();

    public String salvarNotaFalta(NotaFalta nf) {
        try {
            if (dao.existeRegistro(nf.getRgm(), nf.getDisciplina(), nf.getSemestre())) {
                return "AVISO: Já existe uma nota cadastrada para este aluno nesta disciplina e semestre. Use o botão ALTERAR.";
            }
            dao.inserir(nf);
            return "SUCESSO: Nota e faltas salvas com sucesso!";
        } catch (SQLException e) {
            e.printStackTrace();
            return "ERRO: " + e.getMessage();
        }
    }

    public String alterarNotaFalta(NotaFalta nf) {
        try {
            if (!dao.existeRegistro(nf.getRgm(), nf.getDisciplina(), nf.getSemestre())) {
                return "AVISO: Registro não encontrado. Não é possível alterar um lançamento inexistente.";
            }
            dao.atualizar(nf);
            return "SUCESSO: Nota e faltas atualizadas com sucesso!";
        } catch (SQLException e) {
            e.printStackTrace();
            return "ERRO: " + e.getMessage();
        }
    }

    public String excluirNotaFalta(String rgm, String disciplina, String semestre) {
        try {
            if (!dao.existeRegistro(rgm, disciplina, semestre)) {
                return "AVISO: Registro não encontrado para exclusão.";
            }
            dao.excluir(rgm, disciplina, semestre);
            return "SUCESSO: Registro excluído com sucesso!";
        } catch (SQLException e) {
            e.printStackTrace();
            return "ERRO: " + e.getMessage();
        }
    }

    public NotaFalta buscarNotaFalta(String rgm, String disciplina, String semestre) throws SQLException {
        return dao.buscar(rgm, disciplina, semestre);
    }

    public List<NotaFalta> listarPorRgm(String rgm) throws SQLException {
        return dao.listarPorRgm(rgm);
    }
}
