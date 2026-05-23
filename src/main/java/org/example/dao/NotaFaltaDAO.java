package org.example.dao;

import org.example.model.NotaFalta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotaFaltaDAO {

    /** Verifica se já existe registro para este RGM + disciplina + semestre. */
    public boolean existeRegistro(String rgm, String disciplina, String semestre) throws SQLException {
        String sql = "SELECT 1 FROM tb_notas_faltas WHERE fk_rgm=? AND disciplina=? AND semestre=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, rgm);
            stmt.setString(2, disciplina);
            stmt.setString(3, semestre);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    /** Insere um novo registro de nota/falta. */
    public void inserir(NotaFalta nf) throws SQLException {
        String sql = "INSERT INTO tb_notas_faltas (fk_rgm, disciplina, semestre, nota, faltas) VALUES (?,?,?,?,?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            preencherStatement(stmt, nf, true);
            stmt.executeUpdate();
        }
    }

    /** Atualiza nota e faltas de um registro existente. */
    public void atualizar(NotaFalta nf) throws SQLException {
        String sql = "UPDATE tb_notas_faltas SET nota=?, faltas=? WHERE fk_rgm=? AND disciplina=? AND semestre=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, nf.getNota());
            stmt.setInt(2,    nf.getFaltas());
            stmt.setString(3, nf.getRgm());
            stmt.setString(4, nf.getDisciplina());
            stmt.setString(5, nf.getSemestre());
            stmt.executeUpdate();
        }
    }

    /** Busca um registro específico; retorna null se não encontrado. */
    public NotaFalta buscar(String rgm, String disciplina, String semestre) throws SQLException {
        String sql = "SELECT * FROM tb_notas_faltas WHERE fk_rgm=? AND disciplina=? AND semestre=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, rgm);
            stmt.setString(2, disciplina);
            stmt.setString(3, semestre);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? mapearNotaFalta(rs) : null;
            }
        }
    }

    /** Retorna todos os registros de um aluno (usado pelo Boletim). */
    public List<NotaFalta> listarPorRgm(String rgm) throws SQLException {
        String sql = "SELECT * FROM tb_notas_faltas WHERE fk_rgm=? ORDER BY semestre, disciplina";
        List<NotaFalta> lista = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, rgm);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearNotaFalta(rs));
                }
            }
        }
        return lista;
    }

    /** Exclui um registro. */
    public void excluir(String rgm, String disciplina, String semestre) throws SQLException {
        String sql = "DELETE FROM tb_notas_faltas WHERE fk_rgm=? AND disciplina=? AND semestre=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, rgm);
            stmt.setString(2, disciplina);
            stmt.setString(3, semestre);
            stmt.executeUpdate();
        }
    }

    // ── helpers privados ────────────────────────────────────────────────────────

    private NotaFalta mapearNotaFalta(ResultSet rs) throws SQLException {
        NotaFalta nf = new NotaFalta();
        nf.setRgm(rs.getString("fk_rgm"));
        nf.setDisciplina(rs.getString("disciplina"));
        nf.setSemestre(rs.getString("semestre"));
        nf.setNota(rs.getDouble("nota"));
        nf.setFaltas(rs.getInt("faltas"));
        return nf;
    }

    private void preencherStatement(PreparedStatement stmt, NotaFalta nf, boolean incluirChave) throws SQLException {
        stmt.setString(1, nf.getRgm());
        stmt.setString(2, nf.getDisciplina());
        stmt.setString(3, nf.getSemestre());
        stmt.setDouble(4, nf.getNota());
        stmt.setInt(5,    nf.getFaltas());
    }
}
