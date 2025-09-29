package com.example.restaurante.dto;

import java.time.LocalDateTime;

public record ReservaFuturaDTO(
        String nome,
        String telefone,
        short id_reserva,
        short qnt_pessoas,
        LocalDateTime data_hora_chegada
) {}
