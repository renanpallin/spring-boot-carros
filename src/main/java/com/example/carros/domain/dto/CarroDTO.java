package com.example.carros.domain.dto;

import com.example.carros.domain.Carro;
import lombok.Data;

@Data
public class CarroDTO {
    private Long id;
    private String nome;
    private String tipo;

    public CarroDTO(Carro c) {
        this.setId(c.getId());
        this.setNome(c.getNome());
        this.setTipo(c.getTipo());
    }
}
