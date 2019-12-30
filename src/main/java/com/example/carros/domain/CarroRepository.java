package com.example.carros.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CarroRepository extends JpaRepository<Carro, Long> {
    /**
     * Por convenção, tendo um "tipo" e começando por
     * findBy, o spring já consegue criar o filtro
     * sozinho
     */
    List<Carro> findByTipo(String tipo);
}
