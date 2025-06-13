-- ## Script para Carga de Dados

USE sistema_detran;

-- Limpa os dados existentes para evitar duplicados ao re-executar o script
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
-- VEÍCULOS COM PLACA NO FORMATO ANTIGO (com hífen, para testar a conversão)
INSERT INTO veiculos (placa, marca, modelo, ano, cor, cpf_proprietario) VALUES
('ABC1234', 'Plymouth', 'Sedan (The Homer)', 1986, 'Rosa', '11122233344'),
('DEF5678', 'Hot-Dog-Móvel', 'Veículo Especial', 2019, 'Vermelho', '22233344455'),
('GHI9012', 'ACME', 'Foguete', 2018, 'Marrom', '33344455566');

-- VEÍCULOS JÁ COM PLACA NO FORMATO MERCOSUL
INSERT INTO veiculos (placa, marca, modelo, ano, cor, cpf_proprietario) VALUES
('JKL3M45', 'Flintmobile', 'Troncomóvel', 1960, 'Madeira', '44455566677'),
('MNO6P78', 'Barco-Móvel', 'Anfíbio', 2022, 'Amarelo', '55566677788'),
('QRS9T01', 'Bela-313', 'Roadster', 1934, 'Vermelho/Azul', '66677788899'),
('UVW2X34', 'Canyonero', 'SUV', 2020, 'Branco', '11122233344');

-- 3. Inserção de Histórico de Transferências
INSERT INTO transferencias (placa_veiculo, cpf_novo_proprietario, data_transferencia) VALUES
('GHI9012', '44455566677', '2022-08-15'),
('GHI9012', '55566677788', '2023-05-20'),
('QRS9T01', '22233344455', '2024-01-10');
