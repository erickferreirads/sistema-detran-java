# 🚗 Sistema de Gestão de Veículos (Estilo DETRAN)
<div align="center"> <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" /> <img src="https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white" /> <img src="https://img.shields.io/badge/Swing-6DB33F?style=for-the-badge&logo=java&logoColor=white" /> </div>
📌 Visão Geral
Aplicação desktop em Java com interface gráfica (Swing) para simular operações de um DETRAN, incluindo:
✔ Cadastro de proprietários e veículos
✔ Transferência de propriedade com conversão automática de placas (padrão Mercosul)
✔ Relatórios personalizados
✔ Persistência em banco de dados MySQL

##🛠️ Tecnologias
Componente	Tecnologia
Linguagem	Java (JDK 11+)
Interface	Java Swing
Banco de Dados	MySQL 8.0+
Driver	MySQL Connector/J
##🎯 Funcionalidades
###Cadastros
✅ Proprietários (com validação de CPF único)

✅ Veículos (placas geradas automaticamente no padrão Mercosul)

###Operações
🔄 Transferência de veículos com histórico

🗑️ Baixa de veículos (restrita a veículos sem transferências)

Relatórios
📊 Veículos por proprietário (via CPF)

📅 Transferências por período

🚙 Contagem de veículos por marca

🔄 Lista de veículos com placas no formato antigo

##🚀 Configuração
1. Banco de Dados
Execute o script SQL para criar a estrutura:

sql
CREATE DATABASE IF NOT EXISTS sistema_detran;  
USE detran_computacional;  

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

2. Dados de Teste
Proprietários Cadastrados
sql
INSERT INTO proprietarios (cpf, nome) VALUES
('11122233344', 'Homer Simpson'),
('22233344455', 'Mickey Mouse'),
('33344455566', 'Pernalonga Rabbit'),
('44455566677', 'Fred Flintstone'),
('55566677788', 'Bob Esponja Calça Quadrada'),
('66677788899', 'Pato Donald');

Veículos Cadastrados
sql
-- VEÍCULOS PLACA FORMATO ANTIGO
INSERT INTO veiculos (placa, marca, modelo, ano, cor, cpf_proprietario) VALUES
('ABC1234', 'Plymouth', 'Sedan (The Homer)', 1986, 'Rosa', '11122233344'), -- Carro Homer
('DEF5678', 'Hot-Dog-Móvel', 'Veículo Especial', 2019, 'Vermelho', '22233344455'), -- Carro Mickey
('GHI9012', 'ACME', 'Foguete', 2018, 'Marrom', '33344455566'); -- Veículo Pernalonga

-- VEÍCULOS PLACA MERCOSUL
INSERT INTO veiculos (placa, marca, modelo, ano, cor, cpf_proprietario) VALUES
('JKL3M45', 'Flintmobile', 'Troncomóvel', 1960, 'Madeira', '44455566677'), -- Carro Fred
('MNO6P78', 'Barco-Móvel', 'Anfíbio', 2022, 'Amarelo', '55566677788'), -- Carro Bob Esponja
('QRS9T01', 'Bela-313', 'Roadster', 1934, 'Vermelho/Azul', '66677788899'), -- Carro Pato Donald
('UVW2X34', 'Canyonero', 'SUV', 2020, 'Branco', '11122233344'); -- Outro carro Homer
Histórico de Transferências
sql
-- O foguete 'GHI-9012' foi transferido do Pernalonga para o Fred Flintstone.
INSERT INTO transferencias (placa_veiculo, cpf_novo_proprietario, data_transferencia) VALUES
('GHI9012', '44455566677', '2022-08-15');

-- O mesmo foguete foi transferido novamente, do Fred para o Bob Esponja.
INSERT INTO transferencias (placa_veiculo, cpf_novo_proprietario, data_transferencia) VALUES
('GHI9012', '55566677788', '2023-05-20');

-- O carro do Pato Donald foi transferido para o Mickey Mouse.
INSERT INTO transferencias (placa_veiculo, cpf_novo_proprietario, data_transferencia) VALUES
('QRS9T01', '22233344455', '2024-01-10');

3. Conexão
Edite as credenciais em src/org/example/util/DB.java:

java
private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/sistema_detran";  
private static final String USER = "seu_usuario";  
private static final String PASSWORD = "sua_senha";  

👥 Autores
Erick Vinícius Ferreira da Silva / RA:12925114010

Nome do Integrante 2

Nome do Integrante 3

Nome do Integrante 4


