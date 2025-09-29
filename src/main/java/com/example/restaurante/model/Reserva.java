package com.example.restaurante.model;

import lombok.*;

import java.time.LocalDateTime;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Reserva {

    private short id_reserva;
    private String cliente_cpf;
    private short qnt_pessoas;
    private LocalDateTime data_hora_chegada;

    public Reserva(String cliente_cpf, short qnt_pessoas, LocalDateTime data_hora_chegada) {
        this.cliente_cpf = cliente_cpf;
        this.qnt_pessoas = qnt_pessoas;
        this.data_hora_chegada = data_hora_chegada;
    }

}
