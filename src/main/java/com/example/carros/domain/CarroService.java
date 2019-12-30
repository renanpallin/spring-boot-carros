package com.example.carros.domain;

import com.example.carros.domain.dto.CarroDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarroService {

    @Autowired
    private CarroRepository rep;

    public List<CarroDTO> getCarros() {
        return rep.findAll().stream().map(CarroDTO::create).collect(Collectors.toList());

    }

    public List<Carro> getCarrosFake() {
       List<Carro> carros = new ArrayList<>();
       carros.add(new Carro(1L, "Fusca", "classico"));
       carros.add(new Carro(2L, "Fusca 2", "classico"));
       return carros;
    }

    public Optional<Carro> getById(Long id) {
        return rep.findById(id);
    }

    public List<CarroDTO> findByTipo(String tipo) {
        return rep.findByTipo(tipo).stream().map(CarroDTO::create).collect(Collectors.toList());
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