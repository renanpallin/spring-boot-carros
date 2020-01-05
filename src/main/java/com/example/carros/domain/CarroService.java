package com.example.carros.domain;

import com.example.carros.domain.dto.CarroDTO;
import com.carros.api.exception.ObjectNotFoundException;
import org.modelmapper.internal.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarroService {

    @Autowired
    private CarroRepository rep;
    private Object Optional;

    public List<CarroDTO> getCarros() {
        return rep.findAll().stream().map(CarroDTO::create).collect(Collectors.toList());

    }

//    public List<Carro> getCarrosFake() {
//       List<Carro> carros = new ArrayList<>();
//       carros.add(new Carro(1L, "Fusca", "classico"));
//       carros.add(new Carro(2L, "Fusca 2", "classico"));
//       return carros;
//    }

    public CarroDTO getById(Long id) {
        Optional<Carro> carro = rep.findById(id);
        return carro.map(CarroDTO::create).orElseThrow(() -> new ObjectNotFoundException("Carro não encontrado"));
    }

    public List<CarroDTO> findByTipo(String tipo) {
        return rep.findByTipo(tipo).stream().map(CarroDTO::create).collect(Collectors.toList());
    }

    public Carro insert(Carro carro) {
        Assert.isNull(carro.getId(), "Não foi possível inserir o registro");
        return rep.save(carro);
    }

    public Optional<CarroDTO> update(Carro carro, Long id) {
        Assert.notNull(id,"Não foi possível atualizar o registro");

        return rep.findById(id).map(dbCarro -> {
            dbCarro.setNome(carro.getNome());
            dbCarro.setTipo(carro.getTipo());
            return CarroDTO.create(rep.save(dbCarro));
        });
    }

    public void delete(Long id) {
        rep.deleteById(id);
    }
}