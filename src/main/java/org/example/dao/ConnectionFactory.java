package org.example.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Centraliza a criação de conexões com o banco de dados.
 * Antes, cada DAO duplicava o método getConnection() — agora todos usam esta classe.
 * Para trocar a senha ou URL, edite apenas aqui.
 */
public class ConnectionFactory {

    private static final String URL      = "jdbc:mysql://localhost:3306/academico_java?useSSL=false&serverTimezone=America/Sao_Paulo&allowPublicKeyRetrieval=true";
    private static final String USERNAME = "root";
    private static final String PASSWORD = ""; // ajuste conforme sua instalação

    private ConnectionFactory() {} // utilitária — não instanciar

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
