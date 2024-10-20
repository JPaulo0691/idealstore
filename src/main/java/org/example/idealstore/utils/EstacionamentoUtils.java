package org.example.idealstore.utils;

import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
public class EstacionamentoUtils {

    public static String gerarRecibo(){

        LocalDateTime date = LocalDateTime.now(); //2024-10-20T19:43:28.645218
        String recibo = date.toString().substring(0,19);

        return recibo
                .replace("-", "")
                .replace(":", "")
                .replace("T", "-"); //20241020-194328
    }
}
