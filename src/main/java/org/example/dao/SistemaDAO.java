package org.example.dao;

import org.example.util.DB;
import org.example.model.Proprietario;
import org.example.model.Transferencia;
import org.example.model.Veiculo;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SistemaDAO {

    public void executarTransferenciaCompleta(String placaAntiga, String placaNova, Proprietario novoProp) throws SQLException, Exception {
        String sqlUpdateVeiculo = "UPDATE veiculos SET placa = ?, cpf_proprietario = ? WHERE placa = ?";
        String sqlInsertTransferencia = "INSERT INTO transferencias (placa_veiculo, cpf_novo_proprietario, data_transferencia) VALUES (?, ?, ?)";

        Connection conn = null;
        try {
            conn = DB.getConnection();
            conn.setAutoCommit(false); // Inicia a transação

            try (PreparedStatement stmtUpdate = conn.prepareStatement(sqlUpdateVeiculo)) {
                stmtUpdate.setString(1, placaNova);
                stmtUpdate.setString(2, novoProp.getCpf());
                stmtUpdate.setString(3, placaAntiga);
                int linhasAfetadas = stmtUpdate.executeUpdate();
                if (linhasAfetadas == 0) {
                    throw new Exception("Falha na atualização: Veículo com placa '" + placaAntiga + "' não foi encontrado.");
                }
            }

            try (PreparedStatement stmtInsert = conn.prepareStatement(sqlInsertTransferencia)) {
                stmtInsert.setString(1, placaNova);
                stmtInsert.setString(2, novoProp.getCpf());
                stmtInsert.setDate(3, Date.valueOf(LocalDate.now()));
                stmtInsert.executeUpdate();
            }

            conn.commit(); // Confirma a transação se tudo correu bem

        } catch (Exception e) {
            if (conn != null) conn.rollback(); // Desfaz tudo em caso de erro
            throw e; // Lança o erro para a GUI
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

 
    public void cadastrarProprietario(Proprietario proprietario) throws SQLException {
        String sql = "INSERT INTO proprietarios (cpf, nome) VALUES (?, ?)";
        try (Connection conn = DB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, proprietario.getCpf()); stmt.setString(2, proprietario.getNome());
            stmt.executeUpdate();
        }
    }
    public Proprietario buscarProprietarioPorCpf(String cpf) throws SQLException {
        String sql = "SELECT * FROM proprietarios WHERE cpf = ?";
        try (Connection conn = DB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            try (ResultSet rs = stmt.executeQuery()) { if (rs.next()) return new Proprietario(rs.getString("nome"), rs.getString("cpf")); }
        }
        return null;
    }
    public void cadastrarVeiculo(Veiculo veiculo) throws SQLException {
        String sql = "INSERT INTO veiculos (placa, marca, modelo, ano, cor, cpf_proprietario) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, veiculo.getPlaca()); stmt.setString(2, veiculo.getMarca()); stmt.setString(3, veiculo.getModelo());
            stmt.setInt(4, veiculo.getAno()); stmt.setString(5, veiculo.getCor()); stmt.setString(6, veiculo.getProprietario().getCpf());
            stmt.executeUpdate();
        }
    }
    public Veiculo buscarVeiculoPorPlaca(String placa) throws SQLException {
        String sql = "SELECT v.*, p.nome as proprietario_nome FROM veiculos v JOIN proprietarios p ON v.cpf_proprietario = p.cpf WHERE v.placa = ?";
        try (Connection conn = DB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, placa);
            try (ResultSet rs = stmt.executeQuery()) { if (rs.next()) return extrairVeiculoDoResultSet(rs); }
        }
        return null;
    }
    public void removerVeiculo(String placa) throws SQLException {
        String sql = "DELETE FROM veiculos WHERE placa = ?";
        try (Connection conn = DB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, placa); stmt.executeUpdate();
        }
    }
    public boolean veiculoTemTransferencias(String placa) throws SQLException {
        String sql = "SELECT COUNT(*) FROM transferencias WHERE placa_veiculo = ?";
        try (Connection conn = DB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, placa);
            try (ResultSet rs = stmt.executeQuery()) { if (rs.next()) return rs.getInt(1) > 0; }
        }
        return false;
    }
    public List<Transferencia> buscarHistoricoDeTransferencias(String placa) throws SQLException {
        List<Transferencia> historico = new ArrayList<>();
        String sql = "SELECT * FROM transferencias WHERE placa_veiculo = ? ORDER BY data_transferencia DESC";
        try (Connection conn = DB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, placa);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    historico.add(new Transferencia(rs.getString("placa_veiculo"), rs.getString("cpf_novo_proprietario"), rs.getDate("data_transferencia").toLocalDate()));
                }
            }
        }
        return historico;
    }
    public List<Veiculo> listarVeiculosPorProprietario(String cpf) throws SQLException {
        List<Veiculo> veiculos = new ArrayList<>();
        String sql = "SELECT v.*, p.nome as proprietario_nome FROM veiculos v JOIN proprietarios p ON v.cpf_proprietario = p.cpf WHERE v.cpf_proprietario = ?";
        try (Connection conn = DB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            try (ResultSet rs = stmt.executeQuery()) { while (rs.next()) veiculos.add(extrairVeiculoDoResultSet(rs)); }
        }
        return veiculos;
    }
    public List<Transferencia> listarTransferidosPorPeriodo(LocalDate inicio, LocalDate fim) throws SQLException {
        List<Transferencia> transferencias = new ArrayList<>();
        String sql = "SELECT * FROM transferencias WHERE data_transferencia BETWEEN ? AND ? ORDER BY data_transferencia";
        try (Connection conn = DB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(inicio)); stmt.setDate(2, Date.valueOf(fim));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) transferencias.add(new Transferencia(rs.getString("placa_veiculo"), rs.getString("cpf_novo_proprietario"), rs.getDate("data_transferencia").toLocalDate()));
            }
        }
        return transferencias;
    }
    public Map<String, Integer> contarVeiculosPorMarca() throws SQLException {
        Map<String, Integer> contagem = new LinkedHashMap<>();
        String sql = "SELECT marca, COUNT(*) as quantidade FROM veiculos GROUP BY marca ORDER BY marca";
        try (Connection conn = DB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) contagem.put(rs.getString("marca"), rs.getInt("quantidade"));
        }
        return contagem;
    }
    public List<Veiculo> listarVeiculosComPlacaAntiga() throws SQLException {
        List<Veiculo> veiculos = new ArrayList<>();
        String sql = "SELECT v.*, p.nome as proprietario_nome FROM veiculos v JOIN proprietarios p ON v.cpf_proprietario = p.cpf WHERE v.placa REGEXP '^[A-Z]{3}-[0-9]{4}$'";
        try (Connection conn = DB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) veiculos.add(extrairVeiculoDoResultSet(rs));
        }
        return veiculos;
    }
    private Veiculo extrairVeiculoDoResultSet(ResultSet rs) throws SQLException {
        Proprietario proprietario = new Proprietario(rs.getString("proprietario_nome"), rs.getString("cpf_proprietario"));
        return new Veiculo(rs.getString("placa"), rs.getString("marca"), rs.getString("modelo"), rs.getInt("ano"), rs.getString("cor"), proprietario);
    }
}
