package org.example.service;

import java.util.Random;

public class PlacaService {
    public String gerarPlacaMercosul() {
        Random rand = new Random();
        String letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        return String.format("%c%c%c%d%c%02d",
                letras.charAt(rand.nextInt(26)), letras.charAt(rand.nextInt(26)), letras.charAt(rand.nextInt(26)),
                rand.nextInt(10), letras.charAt(rand.nextInt(26)), rand.nextInt(100));
    }

    public String converterParaMercosul(String placaAntiga) {
        if (placaAntiga == null) return null;

        // Remove hífens ou espaços e converte para maiúsculas
        String placaUpper = placaAntiga.replaceAll("[^a-zA-Z0-9]", "").toUpperCase();

        // Valida o formato antigo (AAA9999)
        if (!placaUpper.matches("[A-Z]{3}\\d{4}")) {
            return placaAntiga; // Retorna original se não for uma placa antiga válida
        }

        String tresPrimeirasLetras = placaUpper.substring(0, 3);
        char primeiroNumero = placaUpper.charAt(3);
        char segundoNumeroChar = placaUpper.charAt(4);
        String ultimosDoisNumeros = placaUpper.substring(5, 7); // Pega os dígitos 6 e 7

        int segundoNumeroInt = Character.getNumericValue(segundoNumeroChar);
        if (segundoNumeroInt < 0 || segundoNumeroInt > 9) {
            return placaAntiga; // Dígito inválido
        }

        char letraConvertida = (char) ('A' + segundoNumeroInt);
        return tresPrimeirasLetras + primeiroNumero + letraConvertida + ultimosDoisNumeros;
    }
}