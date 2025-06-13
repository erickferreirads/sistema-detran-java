package org.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/sistema_detran";
    private static final String USER = "root";
    private static final String PASSWORD = "@lgor1tmoDificil";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver JDBC do MySQL não encontrado. Adicione o Connector/J ao seu projeto.", e);
        }
    }
}


/*
Em caso de erro para gerar fazer tranferencia de placa antiga e gerar relatorio de placas antigas

ALTER TABLE transferencias
DROP FOREIGN KEY transferencias_ibfk_1;

ALTER TABLE transferencias
ADD CONSTRAINT transferencias_ibfk_1
FOREIGN KEY (placa_veiculo) REFERENCES veiculos(placa)
ON DELETE CASCADE ON UPDATE CASCADE;

*/