package org.example;

import org.example.dao.SistemaDAO;
import org.example.model.Proprietario;
import org.example.model.Transferencia;
import org.example.model.Veiculo;
import org.example.service.PlacaService;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Main extends JFrame {

    private final SistemaDAO sistemaDAO = new SistemaDAO();
    private final PlacaService placaService = new PlacaService();

    public Main() {
        setTitle("Sistema de Gestão DETRAN - Menu Principal");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painelMenu = new JPanel(new GridLayout(4, 2, 15, 15));
        painelMenu.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton btnCadProp = new JButton("1. Cadastrar Proprietário");
        JButton btnCadVeiculo = new JButton("2. Cadastrar Veículo");
        JButton btnTransferir = new JButton("3. Realizar Transferência");
        JButton btnConsultar = new JButton("4. Consultar Veículo");
        JButton btnBaixa = new JButton("5. Dar Baixa de Veículo");
        JButton btnRelatorios = new JButton("6. Gerar Relatórios");
        JButton btnSair = new JButton("Sair");

        painelMenu.add(btnCadProp);
        painelMenu.add(btnCadVeiculo);
        painelMenu.add(btnTransferir);
        painelMenu.add(btnConsultar);
        painelMenu.add(btnBaixa);
        painelMenu.add(btnRelatorios);
        painelMenu.add(new JLabel());
        painelMenu.add(btnSair);

        btnCadProp.addActionListener(e -> acaoCadastrarProprietario());
        btnCadVeiculo.addActionListener(e -> acaoCadastrarVeiculo());
        btnTransferir.addActionListener(e -> acaoTransferirVeiculo());
        btnConsultar.addActionListener(e -> acaoConsultarVeiculo());
        btnBaixa.addActionListener(e -> acaoDarBaixaVeiculo());
        btnRelatorios.addActionListener(e -> acaoGerarRelatorios());
        btnSair.addActionListener(e -> System.exit(0));

        add(painelMenu);
    }

    private void acaoCadastrarProprietario() {
        JPanel painel = new JPanel(new GridLayout(0, 2, 5, 5));
        JTextField txtNome = new JTextField();
        JTextField txtCpf = new JTextField();
        painel.add(new JLabel("Nome Completo:")); painel.add(txtNome);
        painel.add(new JLabel("CPF (11 dígitos):")); painel.add(txtCpf);

        int resultado = JOptionPane.showConfirmDialog(this, painel, "Cadastrar Novo Proprietário", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (resultado != JOptionPane.OK_OPTION) return;

        try {
            String nome = txtNome.getText();
            String cpf = txtCpf.getText();
            if (nome.trim().isEmpty() || cpf.trim().isEmpty() || cpf.length() != 11) throw new Exception("Nome e CPF (11 dígitos) são obrigatórios.");
            if (sistemaDAO.buscarProprietarioPorCpf(cpf) != null) throw new Exception("Já existe um proprietário com este CPF.");

            sistemaDAO.cadastrarProprietario(new Proprietario(nome, cpf));
            JOptionPane.showMessageDialog(this, "Proprietário cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro de Cadastro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void acaoCadastrarVeiculo() {
        JPanel painel = new JPanel(new GridLayout(0, 2, 5, 5));
        JTextField txtCpf = new JTextField();
        JTextField txtMarca = new JTextField();
        JTextField txtModelo = new JTextField();
        JTextField txtAno = new JTextField();
        JTextField txtCor = new JTextField();
        painel.add(new JLabel("CPF do Proprietário:")); painel.add(txtCpf);
        painel.add(new JLabel("Marca:")); painel.add(txtMarca);
        painel.add(new JLabel("Modelo:")); painel.add(txtModelo);
        painel.add(new JLabel("Ano:")); painel.add(txtAno);
        painel.add(new JLabel("Cor:")); painel.add(txtCor);

        int resultado = JOptionPane.showConfirmDialog(this, painel, "Cadastrar Novo Veículo", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (resultado != JOptionPane.OK_OPTION) return;

        try {
            Proprietario prop = sistemaDAO.buscarProprietarioPorCpf(txtCpf.getText());
            if (prop == null) throw new Exception("CPF do proprietário não encontrado.");

            String placa = placaService.gerarPlacaMercosul();
            Veiculo veiculo = new Veiculo(placa, txtMarca.getText(), txtModelo.getText(), Integer.parseInt(txtAno.getText()), txtCor.getText(), prop);
            sistemaDAO.cadastrarVeiculo(veiculo);
            JOptionPane.showMessageDialog(this, "Veículo cadastrado com sucesso! Placa gerada: " + placa, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro de Cadastro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void acaoTransferirVeiculo() {
        JPanel painel = new JPanel(new GridLayout(0, 2, 5, 5));
        JTextField txtPlaca = new JTextField();
        JTextField txtCpfNovoProp = new JTextField();
        painel.add(new JLabel("Placa do Veículo:")); painel.add(txtPlaca);
        painel.add(new JLabel("CPF do Novo Proprietário:")); painel.add(txtCpfNovoProp);

        int resultado = JOptionPane.showConfirmDialog(this, painel, "Realizar Transferência", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (resultado != JOptionPane.OK_OPTION) return;

        try {
            String placaOriginalInput = txtPlaca.getText();
            String cpfNovoProp = txtCpfNovoProp.getText();

            Veiculo veiculo = sistemaDAO.buscarVeiculoPorPlaca(placaOriginalInput);
            if (veiculo == null) throw new Exception("Veículo com a placa '" + placaOriginalInput + "' não encontrado.");

            Proprietario novoProp = sistemaDAO.buscarProprietarioPorCpf(cpfNovoProp);
            if (novoProp == null) throw new Exception("CPF do novo proprietário não encontrado.");
            if (veiculo.getProprietario().getCpf().equals(cpfNovoProp)) throw new Exception("O veículo já pertence a este proprietário.");

            String placaAntiga = veiculo.getPlaca();
            String placaNova = placaService.converterParaMercosul(placaAntiga);

            sistemaDAO.executarTransferencia(placaAntiga, placaNova, novoProp);

            String mensagem = "Transferência realizada com sucesso!";
            if (!placaAntiga.equals(placaNova)) {
                mensagem += "\nA placa foi convertida para o padrão Mercosul: " + placaNova;
            }
            JOptionPane.showMessageDialog(this, mensagem, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro na Transferência: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void acaoConsultarVeiculo() {
        String placa = JOptionPane.showInputDialog(this, "Digite a placa do veículo:", "Consultar Veículo e Histórico", JOptionPane.PLAIN_MESSAGE);
        if (placa == null || placa.trim().isEmpty()) return;

        try {
            Veiculo veiculo = sistemaDAO.buscarVeiculoPorPlaca(placa);
            StringBuilder resultado = new StringBuilder();
            if (veiculo != null) {
                resultado.append("--- DADOS DO VEÍCULO ---\n").append(veiculo).append("\n\n");
                List<Transferencia> historico = sistemaDAO.buscarHistoricoDeTransferencias(veiculo.getPlaca());
                resultado.append("--- HISTÓRICO DE TRANSFERÊNCIAS ---\n");
                if (historico.isEmpty()) resultado.append("Este veículo não tem transferências registadas.");
                else historico.forEach(t -> resultado.append(t).append("\n"));
            } else {
                resultado.append("Nenhum veículo encontrado com a placa '").append(placa.toUpperCase()).append("'.");
            }
            mostrarResultadoEmDialogo("Resultado da Consulta", resultado.toString());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro de Consulta", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void acaoDarBaixaVeiculo() {
        String placa = JOptionPane.showInputDialog(this, "Digite a placa do veículo para dar baixa:", "Dar Baixa de Veículo", JOptionPane.WARNING_MESSAGE);
        if (placa == null || placa.trim().isEmpty()) return;

        try {
            Veiculo veiculo = sistemaDAO.buscarVeiculoPorPlaca(placa);
            if (veiculo == null) throw new Exception("Veículo não encontrado.");
            if (sistemaDAO.veiculoTemTransferencias(veiculo.getPlaca())) throw new Exception("Não é possível dar baixa. O veículo possui um histórico de transferências.");

            int confirmacao = JOptionPane.showConfirmDialog(this, "Tem a certeza que deseja remover permanentemente o veículo de placa " + veiculo.getPlaca() + "?", "Confirmar Baixa", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (confirmacao == JOptionPane.YES_OPTION) {
                sistemaDAO.removerVeiculo(veiculo.getPlaca());
                JOptionPane.showMessageDialog(this, "Veículo removido com sucesso!", "Baixa Realizada", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro na Baixa", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void acaoGerarRelatorios() {
        String[] opcoes = { "Selecione um relatório...", "1. Veículos por Proprietário", "2. Transferências por Período", "3. Contagem de Veículos por Marca", "4. Veículos com Placa Antiga" };
        JComboBox<String> comboBox = new JComboBox<>(opcoes);
        int resultado = JOptionPane.showConfirmDialog(this, comboBox, "Gerar Relatórios", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (resultado != JOptionPane.OK_OPTION || comboBox.getSelectedIndex() == 0) return;

        try {
            switch (comboBox.getSelectedIndex()) {
                case 1: gerarRelatorioVeiculosPorProprietario(); break;
                case 2: gerarRelatorioTransferenciasPorPeriodo(); break;
                case 3: gerarRelatorioContagemPorMarca(); break;
                case 4: gerarRelatorioPlacasAntigas(); break;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao gerar relatório: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void gerarRelatorioVeiculosPorProprietario() throws Exception {
        String cpf = JOptionPane.showInputDialog(this, "Digite o CPF do proprietário:", "Relatório por Proprietário", JOptionPane.PLAIN_MESSAGE);
        if (cpf != null && !cpf.trim().isEmpty()) {
            List<Veiculo> veiculos = sistemaDAO.listarVeiculosPorProprietario(cpf);
            StringBuilder relatorio = new StringBuilder("--- VEÍCULOS DO PROPRIETÁRIO CPF: ").append(cpf).append(" ---\n\n");
            if (veiculos.isEmpty()) relatorio.append("Não foram encontrados veículos para este proprietário.");
            else veiculos.forEach(v -> relatorio.append(v).append("\n"));
            mostrarResultadoEmDialogo("Relatório de Veículos por Proprietário", relatorio.toString());
        }
    }

    private void gerarRelatorioTransferenciasPorPeriodo() throws Exception {
        JPanel painel = new JPanel(new GridLayout(0, 2, 5, 5));
        JTextField txtInicio = new JTextField("AAAA-MM-DD");
        JTextField txtFim = new JTextField("AAAA-MM-DD");
        painel.add(new JLabel("Data de Início:")); painel.add(txtInicio);
        painel.add(new JLabel("Data de Fim:")); painel.add(txtFim);
        int resultado = JOptionPane.showConfirmDialog(this, painel, "Definir Período", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (resultado == JOptionPane.OK_OPTION) {
            LocalDate inicio = LocalDate.parse(txtInicio.getText());
            LocalDate fim = LocalDate.parse(txtFim.getText());
            List<Transferencia> transferencias = sistemaDAO.listarTransferidosPorPeriodo(inicio, fim);
            StringBuilder relatorio = new StringBuilder("--- TRANSFERÊNCIAS ENTRE ").append(inicio).append(" E ").append(fim).append(" ---\n\n");
            if (transferencias.isEmpty()) relatorio.append("Não houve transferências neste período.");
            else transferencias.forEach(t -> relatorio.append(t).append("\n"));
            mostrarResultadoEmDialogo("Relatório de Transferências por Período", relatorio.toString());
        }
    }

    private void gerarRelatorioContagemPorMarca() throws SQLException {
        Map<String, Integer> contagem = sistemaDAO.contarVeiculosPorMarca();
        StringBuilder relatorio = new StringBuilder("--- QUANTIDADE DE VEÍCULOS POR MARCA ---\n\n");
        if (contagem.isEmpty()) relatorio.append("Não há veículos registados.");
        else contagem.forEach((marca, qtd) -> relatorio.append(String.format("%-20s: %d veículo(s)\n", marca, qtd)));
        mostrarResultadoEmDialogo("Relatório de Veículos por Marca", relatorio.toString());
    }

    private void gerarRelatorioPlacasAntigas() throws SQLException {
        List<Veiculo> veiculos = sistemaDAO.listarVeiculosComPlacaAntiga();
        StringBuilder relatorio = new StringBuilder("--- RELATÓRIO DE VEÍCULOS COM PLACA ANTIGA ---\n\n");
        if (veiculos.isEmpty()) relatorio.append("Não foram encontrados veículos com o modelo de placa antigo.");
        else veiculos.forEach(v -> relatorio.append(v).append("\n"));
        mostrarResultadoEmDialogo("Relatório de Placas Antigas", relatorio.toString());
    }

    private void mostrarResultadoEmDialogo(String titulo, String texto) {
        JTextArea areaTexto = new JTextArea(texto);
        areaTexto.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaTexto);
        scrollPane.setPreferredSize(new Dimension(500, 300));
        JOptionPane.showMessageDialog(this, scrollPane, titulo, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main gui = new Main();
            gui.setVisible(true);
        });
    }
}
