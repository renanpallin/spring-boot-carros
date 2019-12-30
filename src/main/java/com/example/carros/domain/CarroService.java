package com.example.carros.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarroService {

    @Autowired
    private CarroRepository rep;

    public Iterable<Carro> getCarros() {
        return rep.findAll();
    }

    public List<Carro> getCarrosFake() {
       List<Carro> carros = new ArrayList<>();
       carros.add(new Carro(1L, "Fusca"));
       carros.add(new Carro(2L, "Fusca 2"));
       return carros;
    }

    public Optional<Carro> getById(Long id) {
        return rep.findById(id);
    }

    public Iterable<Carro> findByTipo(String tipo) {
        return rep.findByTipo(tipo);
    }

    public Carro insert(Carro carro) {
        return rep.save(carro);
    }

    public Carro update(Carro carro, Long id) {
        return getById(id).map(dbCarro -> {
            dbCarro.setNome(carro.getNome());
            dbCarro.setTipo(carro.getTipo());
            return rep.save(dbCarro);
        }).orElseThrow(() -> new RuntimeException("Não foi possível atualizar o registro"));
    }

    public void delete(Long id) {
        getById(id).ifPresent(carro -> {
            rep.deleteById(id);
        });
    }
}