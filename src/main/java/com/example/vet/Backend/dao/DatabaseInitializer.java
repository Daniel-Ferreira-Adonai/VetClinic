package com.example.vet.Backend.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    private static final String ROOT_URL = "jdbc:mysql://localhost:3306/?user=root&password=";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/clinicaVeterinaria";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
        createDatabase();
        createTables();
        insertTestUsers();
    }

    public static void createDatabase() {
        try (Connection conn = DriverManager.getConnection(ROOT_URL);
             Statement stmt = conn.createStatement()) {

            stmt.execute("CREATE DATABASE IF NOT EXISTS clinicaVeterinaria");
            System.out.println("Database alredy exist or was created with success");

        } catch (SQLException e) {
            System.err.println("Error creating Database" + e.getMessage());
        }
    }

    public static void createTables() {
        String[] sqlStatements = {
                """
            CREATE TABLE IF NOT EXISTS tutor (
                id_tutor INT AUTO_INCREMENT PRIMARY KEY,
                nome VARCHAR(100) NOT NULL,
                telefone VARCHAR(20),
                email VARCHAR(100) UNIQUE NOT NULL
            );
            """,
                """
            CREATE TABLE IF NOT EXISTS animal (
                id_animal INT AUTO_INCREMENT PRIMARY KEY,
                nome VARCHAR(100) NOT NULL,
                especie VARCHAR(50),
                raca VARCHAR(50),
                id_tutor INT,
                FOREIGN KEY (id_tutor) REFERENCES tutor(id_tutor)
            );
            """,
                """
            CREATE TABLE IF NOT EXISTS consulta (
                id_consulta INT AUTO_INCREMENT PRIMARY KEY,
                id_animal INT,
                queixa_do_tutor TEXT,
                data_hora DATETIME NOT NULL,
                veterinario VARCHAR(100),
                preco DECIMAL(10,2),
                diagnostico TEXT,
                FOREIGN KEY (id_animal) REFERENCES animal(id_animal)
            );
            """,
                """
            CREATE TABLE IF NOT EXISTS servico (
                id_servico INT AUTO_INCREMENT PRIMARY KEY,
                nome VARCHAR(100) NOT NULL,
                descricao TEXT,
                preco DECIMAL(10,2),
                imagem_url TEXT,
                tipo ENUM('BANHO', 'TOSA', 'VACINA', 'CONSULTA', 'EXAME', 'OUTRO') DEFAULT 'OUTRO'
            );
            """,
                """
            CREATE TABLE IF NOT EXISTS produto (
                id_produto INT PRIMARY KEY AUTO_INCREMENT,
                nome VARCHAR(100),
                descricao TEXT,
                preco DECIMAL(10,2),
                quantidade_estoque INT,
                imagem_url TEXT
            );
            """,
                """
            CREATE TABLE IF NOT EXISTS usuarios (
                id INT AUTO_INCREMENT PRIMARY KEY,
                nome VARCHAR(100),
                email VARCHAR(100) UNIQUE,
                senha VARCHAR(255),
                tipo_usuario ENUM('TUTOR', 'VET', 'ADMIN') NOT NULL
            );
            """,
                """
            CREATE TABLE IF NOT EXISTS pedido (
                id INT AUTO_INCREMENT PRIMARY KEY,
                email_cliente VARCHAR(255),
                data DATETIME,
                total DOUBLE
            );
            """,
                """
            CREATE TABLE IF NOT EXISTS item_pedido (
                id INT AUTO_INCREMENT PRIMARY KEY,
                id_pedido INT,
                id_item INT,
                nome VARCHAR(255),
                tipo VARCHAR(50),
                preco DOUBLE,
                quantidade INT,
                FOREIGN KEY (id_pedido) REFERENCES pedido(id)
            );
            """
        };

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            for (String sql : sqlStatements) {
                stmt.execute(sql);
            }

            System.out.println("Tables created with success");

        } catch (SQLException e) {
            System.err.println("Error creating tables " + e.getMessage());
        }
    }

    public static void insertTestUsers() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            // Inserir tutor (usuario e tutor)
            stmt.executeUpdate("""
                INSERT IGNORE INTO usuarios (nome, email, senha, tipo_usuario)
                VALUES ('Tutor Teste', 'tutor@teste.com', 'tutor123', 'TUTOR');
            """);

            stmt.executeUpdate("""
                INSERT IGNORE INTO tutor (nome, telefone, email)
                VALUES ('Tutor Teste', '88999999999', 'tutor@teste.com');
            """);

            // Inserir vet
            stmt.executeUpdate("""
                INSERT IGNORE INTO usuarios (nome, email, senha, tipo_usuario)
                VALUES ('Vet Teste', 'vet@teste.com', 'vet123', 'VET');
            """);

            // Inserir admin
            stmt.executeUpdate("""
                INSERT IGNORE INTO usuarios (nome, email, senha, tipo_usuario)
                VALUES ('Admin Teste', 'admin@teste.com', 'admin123', 'ADMIN');
            """);

            System.out.println("Users added with success");

        } catch (SQLException e) {
            System.err.println("Error on inserting users " + e.getMessage());
        }
    }
}
