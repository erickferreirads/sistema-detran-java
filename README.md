🚗 Sistema de Gestão de Veículos (Estilo DETRAN)
<div align="center"> <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" /> <img src="https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white" /> <img src="https://img.shields.io/badge/Swing-6DB33F?style=for-the-badge&logo=java&logoColor=white" /> </div>

Tecnologias Utilizadas
Linguagem: Java (JDK 11 ou superior)

Interface Gráfica: Java Swing

Base de Dados: MySQL 8.0 ou superior

Driver de Conexão: MySQL Connector/J

Funcionalidades Implementadas
A aplicação apresenta um menu principal simples e intuitivo, a partir do qual todas as funcionalidades podem ser acedidas através de janelas de diálogo:

Cadastrar Proprietário: Adiciona novos proprietários ao sistema através do nome e CPF, com validação para impedir CPFs duplicados.

Cadastrar Veículo: Efetua o registo de novos veículos. A placa é gerada automaticamente no padrão Mercosul.

Realizar Transferência: Altera o proprietário de um veículo. Se o veículo possuir uma placa no padrão antigo (ex: ABC-1234), esta é automaticamente convertida para o padrão Mercosul e a operação é guardada no histórico. A operação é gerida como uma transação para garantir a integridade dos dados.

Consultar Veículo: Permite pesquisar um veículo pela sua placa e exibe não só os seus dados, mas também todo o seu histórico de transferências.

Dar Baixa de Veículo: Permite remover um veículo do sistema. Esta ação só é permitida se o veículo não possuir nenhum registo de transferência, de acordo com a regra de negócio.

Gerar Relatórios: Um menu dropdown permite ao utilizador escolher e gerar vários relatórios:

Listar todos os veículos de um determinado proprietário (através do CPF).

Listar todas as transferências realizadas num determinado período de tempo.

Exibir uma contagem de quantos veículos existem de cada marca.

Gerar uma lista de todos os veículos que ainda possuem a placa no formato antigo.

Como Configurar e Executar
Siga estes passos para configurar o ambiente e executar a aplicação:

1. Configuração da Base de Dados
Execute o seguinte script SQL no seu cliente MySQL (como o MySQL Workbench) para criar a base de dados e as tabelas necessárias para o projeto.

-- Cria a base de dados (se ainda não existir)
CREATE DATABASE IF NOT EXISTS detran_computacional;

-- Seleciona a base de dados que será utilizada nos comandos seguintes
USE detran_computacional;

-- Tabela para armazenar os dados dos proprietários.
-- O CPF é a chave primária, pois é um identificador único.
CREATE TABLE proprietarios (
    cpf VARCHAR(11) PRIMARY KEY,
    nome VARCHAR(255) NOT NULL
);

-- Tabela para armazenar os dados dos veículos.
-- A placa é a chave primária.
-- A coluna 'cpf_proprietario' é uma chave estrangeira que cria uma relação
-- com a tabela 'proprietarios'.
CREATE TABLE veiculos (
    placa VARCHAR(10) PRIMARY KEY,
    marca VARCHAR(100) NOT NULL,
    modelo VARCHAR(100) NOT NULL,
    ano INT NOT NULL,
    cor VARCHAR(50) NOT NULL,
    cpf_proprietario VARCHAR(11),
    FOREIGN KEY (cpf_proprietario) REFERENCES proprietarios(cpf)
);

-- Tabela para guardar o histórico de todas as transferências realizadas.
-- O 'id' é auto-incrementado para garantir que cada registo de transferência seja único.
CREATE TABLE transferencias (
    id INT AUTO_INCREMENT PRIMARY KEY,
    placa_veiculo VARCHAR(10) NOT NULL,
    cpf_novo_proprietario VARCHAR(11) NOT NULL,
    data_transferencia DATE NOT NULL
);

2. Configuração da Conexão
Abra o ficheiro src/org/example/util/DB.java no seu projeto e altere as credenciais de conexão para corresponderem à sua configuração local do MySQL:

// src/org/example/util/DB.java

// ...
private static final String DATABASE_URL = "jdbc:mysql://sistema_detran:3306/detran_computacional?useSSL=false&allowPublicKeyRetrieval=true";
private static final String USER = "seu_utilizador"; // Ex: root
private static final String PASSWORD = "sua_senha"; // A sua senha do MySQL
// ...

3. Execução do Projeto
Certifique-se de que o Driver JDBC do MySQL (Connector/J) foi adicionado como uma dependência no seu projeto na sua IDE.

Compile o projeto.

Execute a classe principal org.example.main.SistemaGUI.java.

O menu principal da aplicação irá aparecer, pronto para ser utilizado.

4. Carga de Dados para Teste (Opcional)
Depois de criar as tabelas, pode executar o script abaixo para popular a base de dados com dados de teste, incluindo proprietários (personagens de desenhos animados) e veículos com placas antigas e novas.

-- Seleciona a base de dados correta
USE detran_computacional;

-- Limpa os dados existentes para evitar duplicados
DELETE FROM transferencias;
DELETE FROM veiculos;
DELETE FROM proprietarios;

-- 1. Inserção de Proprietários
INSERT INTO proprietarios (cpf, nome) VALUES
('11122233344', 'Homer Simpson'),
('22233344455', 'Mickey Mouse'),
('33344455566', 'Pernalonga Rabbit'),
('44455566677', 'Fred Flintstone'),
('55566677788', 'Bob Esponja Calça Quadrada'),
('66677788899', 'Pato Donald');

-- 2. Inserção de Veículos
-- VEÍCULOS COM PLACA NO FORMATO ANTIGO (com hífen)
INSERT INTO veiculos (placa, marca, modelo, ano, cor, cpf_proprietario) VALUES
('ABC-1234', 'Plymouth', 'Sedan (The Homer)', 1986, 'Rosa', '11122233344'),
('DEF-5678', 'Hot-Dog-Móvel', 'Veículo Especial', 2019, 'Vermelho', '22233344455'),
('GHI-9012', 'ACME', 'Foguete', 2018, 'Marrom', '33344455566');

-- VEÍCULOS JÁ COM PLACA NO FORMATO MERCOSUL
INSERT INTO veiculos (placa, marca, modelo, ano, cor, cpf_proprietario) VALUES
('JKL3M45', 'Flintmobile', 'Troncomóvel', 1960, 'Madeira', '44455566677'),
('MNO6P78', 'Barco-Móvel', 'Anfíbio', 2022, 'Amarelo', '55566677788'),
('QRS9T01', 'Bela-313', 'Roadster', 1934, 'Vermelho/Azul', '66677788899'),
('UVW2X34', 'Canyonero', 'SUV', 2020, 'Branco', '11122233344');

-- 3. Inserção de Histórico de Transferências
INSERT INTO transferencias (placa_veiculo, cpf_novo_proprietario, data_transferencia) VALUES
('GHI-9012', '44455566677', '2022-08-15'),
('GHI-9012', '55566677788', '2023-05-20'),
('QRS9T01', '22233344455', '2024-01-10');

Estrutura do Projeto
O código está organizado em pacotes para seguir as boas práticas de programação e facilitar a manutenção:

org.example.main: Contém a classe SistemaGUI, responsável por toda a interface gráfica e interação com o utilizador.

org.example.model: Contém as classes que representam as entidades do sistema (Veiculo, Proprietario, Transferencia). Elas servem como "moldes" para os dados.

org.example.dao: Contém a classe SistemaDAO, que centraliza toda a comunicação com a base de dados (operações de INSERT, UPDATE, SELECT, DELETE).

org.example.service: Contém a classe PlacaService, que isola a lógica de negócio específica para gerar e converter placas de veículos.

org.example.util: Contém a classe DB, responsável por gerir a conexão com a base de dados.

Autores
[Nome do Integrante 1]

[Nome do Integrante 2]

[Nome do Integrante 3]

[Nome do Integrante 4]
