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
        String placaUpper = placaAntiga.toUpperCase();
        if (!placaUpper.matches("[A-Z]{3}-\\d{4}")) return placaAntiga;

        String tresPrimeirasLetras = placaUpper.substring(0, 3);
        char primeiroNumero = placaUpper.charAt(4);
        char segundoNumeroChar = placaUpper.charAt(5);
        String ultimosDoisNumeros = placaUpper.substring(6);
        int segundoNumeroInt = Character.getNumericValue(segundoNumeroChar);
        char letraConvertida = (char) ('A' + segundoNumeroInt);

        return tresPrimeirasLetras + primeiroNumero + letraConvertida + ultimosDoisNumeros;
    }
}