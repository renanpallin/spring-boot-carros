package com.example.carros.domain;

import org.springframework.data.repository.CrudRepository;

public interface CarroRepository extends CrudRepository<Carro, Long> {
    /**
     * Por convenção, tendo um "tipo" e começando por
     * findBy, o spring já consegue criar o filtro
     * sozinho
     */
    Iterable<Carro> findByTipo(String tipo);
}
