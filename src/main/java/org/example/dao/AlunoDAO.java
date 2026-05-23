package org.example.dao;

import org.example.model.Aluno;

import java.sql.*;

public class AlunoDAO {

    /** Verifica se um RGM existe na base. */
    public boolean existeRgm(String rgm) throws SQLException {
        String sql = "SELECT 1 FROM tb_alunos WHERE pk_rgm = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, rgm);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    /** Retorna o Aluno pelo RGM, ou null se não encontrado. */
    public Aluno buscarPorRgm(String rgm) throws SQLException {
        String sql = "SELECT pk_rgm, nome, fk_curso, email, celular, endereco, " +
                     "data_nasc, cpf, municipio, uf, fk_campus, fk_periodo " +
                     "FROM tb_alunos WHERE pk_rgm = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, rgm);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearAluno(rs);
                }
                return null;
            }
        }
    }

    /** Insere um novo aluno. */
    public void inserir(Aluno a) throws SQLException {
        String sql = "INSERT INTO tb_alunos " +
                     "(pk_rgm, nome, fk_curso, email, celular, endereco, data_nasc, cpf, municipio, uf, fk_campus, fk_periodo) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            preencherStatement(stmt, a);
            stmt.setString(12, a.getPeriodo());
            stmt.executeUpdate();
        }
    }

    /** Atualiza os dados de um aluno existente. */
    public void atualizar(Aluno a) throws SQLException {
        String sql = "UPDATE tb_alunos SET nome=?, fk_curso=?, email=?, celular=?, endereco=?, " +
                     "data_nasc=?, cpf=?, municipio=?, uf=?, fk_campus=?, fk_periodo=? WHERE pk_rgm=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1,  a.getNome());
            stmt.setString(2,  a.getCurso());
            stmt.setString(3,  a.getEmail());
            stmt.setString(4,  a.getTelefone());
            stmt.setString(5,  a.getEndereco());
            stmt.setString(6,  a.getDataNascimento());
            stmt.setString(7,  a.getCpf());
            stmt.setString(8,  a.getMunicipio());
            stmt.setString(9,  a.getUf());
            stmt.setString(10, a.getCampus());
            stmt.setString(11, a.getPeriodo());
            stmt.setString(12, a.getRgm());
            stmt.executeUpdate();
        }
    }

    /** Exclui um aluno pelo RGM (o CASCADE no banco remove notas/faltas vinculadas). */
    public void excluir(String rgm) throws SQLException {
        String sql = "DELETE FROM tb_alunos WHERE pk_rgm = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, rgm);
            stmt.executeUpdate();
        }
    }

    // ── helpers privados ────────────────────────────────────────────────────────

    private Aluno mapearAluno(ResultSet rs) throws SQLException {
        Aluno a = new Aluno();
        a.setRgm(rs.getString("pk_rgm"));
        a.setNome(rs.getString("nome"));
        a.setCurso(rs.getString("fk_curso"));
        a.setEmail(rs.getString("email"));
        a.setTelefone(rs.getString("celular"));
        a.setEndereco(rs.getString("endereco"));
        a.setDataNascimento(rs.getString("data_nasc"));
        a.setCpf(rs.getString("cpf"));
        a.setMunicipio(rs.getString("municipio"));
        a.setUf(rs.getString("uf"));
        a.setCampus(rs.getString("fk_campus"));
        a.setPeriodo(rs.getString("fk_periodo"));
        return a;
    }

    /** Preenche os parâmetros comuns para INSERT (posições 1-11, RGM na 1). */
    private void preencherStatement(PreparedStatement stmt, Aluno a) throws SQLException {
        stmt.setString(1,  a.getRgm());
        stmt.setString(2,  a.getNome());
        stmt.setString(3,  a.getCurso());
        stmt.setString(4,  a.getEmail());
        stmt.setString(5,  a.getTelefone());
        stmt.setString(6,  a.getEndereco());
        stmt.setString(7,  a.getDataNascimento());
        stmt.setString(8,  a.getCpf());
        stmt.setString(9,  a.getMunicipio());
        stmt.setString(10, a.getUf());
        stmt.setString(11, a.getCampus());
    }
}
