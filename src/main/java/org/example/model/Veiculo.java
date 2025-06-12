package org.example.model;

public class Veiculo {
    private String placa; // Deve ser única
    private String marca;
    private String modelo;
    private int ano;
    private String cor;
    private Proprietario proprietario;

    public Veiculo(String placa, String marca, String modelo, int ano, String cor, Proprietario proprietario) {
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.cor = cor;
        this.proprietario = proprietario;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public Proprietario getProprietario() {
        return proprietario;
    }

    public void setProprietario(Proprietario proprietario) {
        this.proprietario = proprietario;
    }

    @Override
    public String toString() {
        return "Placa:" + placa  + "\n" + "Marca: " + marca  + "\n" + "Modelo: " + modelo + "\n" + "Ano: " + ano  + "\n" + "Cor: " + cor + "\n" + "Proprietário: " + proprietario.getNome();
    }
}