package org.example.idealstore.utils;

import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class EstacionamentoUtils {

    private static final double PRIMEIROS_15_MINUTES = 5.00;
    private static final double PRIMEIROS_60_MINUTES = 9.25;
    private static final double ADICIONAL_15_MINUTES = 1.75;
    private static final double DESCONTO_PERCENTUAL = 0.30;

    private EstacionamentoUtils() {}

    public static BigDecimal calcularCusto(LocalDateTime entrada, LocalDateTime saida) {
        long minutes = entrada.until(saida, ChronoUnit.MINUTES);
        double total = 0.0;

        if (minutes <= 15) {

            // complete com a lógica para calcular o custo até 15 minutos de uso
            total = PRIMEIROS_15_MINUTES;

        } else if (minutes <= 60) {

            // complete com a lógica para calcular o custo até os primeiros 60 minutos de uso
            total = PRIMEIROS_60_MINUTES;

        } else {

            // complete com a lógica para calcular o custo acima de 60 minutos de uso
            long extraTime  = minutes -  60;
            long faixaDe15Minutos  = (extraTime + 14) / 15;
            total = PRIMEIROS_60_MINUTES + (ADICIONAL_15_MINUTES * faixaDe15Minutos);

        }

        return new BigDecimal(total).setScale(2, RoundingMode.HALF_EVEN);
    }

    public static BigDecimal calcularDesconto(BigDecimal custo, long numeroDeVezes) {

        // Complete o código com a sua lógica
        BigDecimal desconto = BigDecimal.ZERO;

        if(numeroDeVezes % 10 == 1){
            desconto = custo.multiply(BigDecimal.valueOf(DESCONTO_PERCENTUAL));
        }

        return desconto.setScale(2, RoundingMode.HALF_EVEN);
    }

    public static String gerarRecibo(){

        LocalDateTime date = LocalDateTime.now(); //2024-10-20T19:43:28.645218
        String recibo = date.toString().substring(0,19);

        return recibo
                .replace("-", "")
                .replace(":", "")
                .replace("T", "-"); //20241020-194328
    }
}
