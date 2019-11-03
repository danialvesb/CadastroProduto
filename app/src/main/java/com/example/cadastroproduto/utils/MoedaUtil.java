package com.example.cadastroproduto.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class MoedaUtil {
    private static DecimalFormatSymbols symbols = new DecimalFormatSymbols();

    public MoedaUtil() {
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');
    }

    public static String moeda2Decimais(Double valor) {
        DecimalFormat decimalFormat = new DecimalFormat(" #,##0.00", symbols);
        String sValor = decimalFormat.format(valor);

        return sValor;
    }

    public static String moeda3Decimais(Double valor) {
        DecimalFormat decimalFormat3dec = new DecimalFormat(" #,##0.000", symbols);
        String sValor = decimalFormat3dec.format(valor);

        return sValor;
    }

    public static String moedaSemDecimais(Double valor) {
        DecimalFormat decimalFormat3dec = new DecimalFormat(" #,##0", symbols);
        String sValor = decimalFormat3dec.format(valor);

        return sValor;
    }

    public static boolean valorSemCasasDecimais(Double valor) {
        Double fracao = valor % 1.0f;

        return (fracao == 0.0f);
    }
}
