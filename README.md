# 🚗 Sistema de Gestão de Veículos (Estilo DETRAN)
## 📌 Visão Geral
Aplicação desktop em Java com interface gráfica (Swing) para simular operações de um DETRAN, incluindo:

✔ Cadastro de proprietários e veículos

✔ Transferência de propriedade com conversão automática de placas (padrão Mercosul)

✔ Relatórios personalizados

✔ Persistência em banco de dados MySQL

## 🛠️ Tecnologias
<table>
  <thead>
    <tr>
      <th>Componente</th>
      <th>Tecnologia</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>Linguagem </td>
      <td>Java (JDK 11+)</td>
    </tr>
    <tr>
      <td>Interface</td>
      <td>Java Swing</td>
    </tr>
      <tr>
      <td>Banco de Dados</td>
      <td>MySQL 8.0+</td>
    </tr>
      <tr>
      <td>Driver</td>
      <td>MySQL Connector/J</td>
    </tr>
  </tbody>
</table>

## 🎯 Funcionalidades
Cadastros


✅ Proprietários (com validação de CPF único)

✅ Veículos (placas geradas automaticamente no padrão Mercosul)

Operações


🔄 Transferência de veículos com histórico

🗑️ Baixa de veículos (restrita a veículos sem transferências)

Relatórios


📊 Veículos por proprietário (via CPF)

📅 Transferências por período

🚙 Contagem de veículos por marca

🔄 Lista de veículos com placas no formato antigo

## Conexão
   
Edite as credenciais em src/org/example/util/DB.java:

```
private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/sistema_detran";  
private static final String USER = "seu_usuario";  
private static final String PASSWORD = "sua_senha";
```


## 👥 Autores

Erick Vinícius Ferreira da Silva / RA: 12925114010

Fabrício Jardim Zietlow / RA: 12925114421

Pedro Henrique Silva de Oliveira / RA: 12925115152

Adriano Junior de Oliveira / RA: 12925114205

