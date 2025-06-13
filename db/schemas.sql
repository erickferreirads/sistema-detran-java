-- ## Script para Criação da Base de Dados

CREATE DATABASE IF NOT EXISTS sistema_detran;

USE sistema_detran;

CREATE TABLE proprietarios (
    cpf VARCHAR(11) PRIMARY KEY,
    nome VARCHAR(255) NOT NULL
);

CREATE TABLE veiculos (
    placa VARCHAR(10) PRIMARY KEY,
    marca VARCHAR(100) NOT NULL,
    modelo VARCHAR(100) NOT NULL,
    ano INT NOT NULL,
    cor VARCHAR(50) NOT NULL,
    cpf_proprietario VARCHAR(11),
    FOREIGN KEY (cpf_proprietario) REFERENCES proprietarios(cpf)
);

CREATE TABLE transferencias (
    id INT AUTO_INCREMENT PRIMARY KEY,
    placa_veiculo VARCHAR(10) NOT NULL,
    cpf_novo_proprietario VARCHAR(11) NOT NULL,
    data_transferencia DATE NOT NULL
);