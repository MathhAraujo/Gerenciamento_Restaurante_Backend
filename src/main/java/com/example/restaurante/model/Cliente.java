package com.example.restaurante.model;


import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Cliente {

    private String cpf;
    private String telefone;
    private String nome;
}