package org.example.model;

import java.time.LocalDate;

public class Transferencia {
    private int  id;
    private final String placaVeiculo;
    private final String cpfNovoProprietario;
    private final LocalDate dataTransferencia; //

    public Transferencia(String placaVeiculo, String cpfNovoProprietario, LocalDate dataTransferencia) {
        this.placaVeiculo = placaVeiculo;
        this.cpfNovoProprietario = cpfNovoProprietario;
        this.dataTransferencia = dataTransferencia;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getPlacaVeiculo() { return placaVeiculo; }
    public String getCpfNovoProprietario() { return cpfNovoProprietario; }
    public LocalDate getDataTransferencia() { return dataTransferencia; }

    @Override
    public String toString() {
        return "Placa: " + placaVeiculo  + "\n" + "CPF Novo Proprietário: " + cpfNovoProprietario  + "\n" + "Data: " + dataTransferencia ;
    }
}